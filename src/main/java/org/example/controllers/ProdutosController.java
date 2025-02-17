package org.example.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.entities.Bebida;
import org.example.services.BebidaService;
import org.example.util.AbridorJanela;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller // Indica que esta classe é um controlador gerenciado pelo Spring
public class ProdutosController implements Initializable {

    @FXML
    private TableView<Bebida> tView_Prod;

    @FXML
    private TableColumn<Bebida, Integer> prodIdCol;

    @FXML
    private TableColumn<Bebida, String> prodDescCol;

    @FXML
    private TableColumn<Bebida, Float> prodQuantCol;

    @FXML
    private TableColumn<Bebida, Float> prodPrecoCol;

    @FXML
    private TableColumn<Bebida, String> prodCbCol;

    @FXML
    private TableColumn<Bebida, Boolean> prodNfCol;

    @FXML
    private TextField tf_Pesquisa;



    @Autowired
    private AbridorJanela abridorJanela;

    @Autowired // Injeção do BebidaService
    private BebidaService bebidaService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // Configuração das colunas
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodDescCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        prodQuantCol.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        prodPrecoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));
        prodCbCol.setCellValueFactory(new PropertyValueFactory<>("codigo_barras"));
        prodNfCol.setCellValueFactory(new PropertyValueFactory<>("nf"));

        tView_Prod.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double totalWidth = newWidth.doubleValue();
            prodDescCol.setPrefWidth(totalWidth * 0.5); //
            prodCbCol.setPrefWidth(totalWidth * 0.15); //
            prodPrecoCol.setPrefWidth(totalWidth * 0.15);
        });
        // Recupera os dados do banco de dados usando o serviço
        List<Bebida> bebidas = bebidaService.buscarTodos();

        // Converte a lista para ObservableList
        ObservableList<Bebida> bebidasObservable = FXCollections.observableArrayList(bebidas);

        // Associa os dados à TableView
        tView_Prod.setItems(bebidasObservable);
        //pesquisar automaticamente ao digitar
        tf_Pesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            onBuscarButtonClick(null);
        });

        atualizarTabela();
    }

    public void onAddProdClickButton(ActionEvent actionEvent) {
       Stage stage = abridorJanela.abrirNovaJanela("/views/add-prod-view.fxml", "Cadastro Produtos",800,600);
        if (stage != null) {
            // Aguarda o fechamento da janela e atualiza a tabela
            stage.setOnHidden(event -> atualizarTabela());
        }

    }
    private void atualizarTabela() {
        // Busca novamente os dados do banco
        List<Bebida> bebidasAtualizadas = bebidaService.buscarTodos();

        // Atualiza os dados na TableView
        tView_Prod.setItems(FXCollections.observableArrayList(bebidasAtualizadas));
    }
    @FXML
    public void onBuscarButtonClick(ActionEvent actionEvent) {

        String descricao = tf_Pesquisa.getText().trim(); // Obtém o texto digitado

        if (!descricao.isEmpty()) {
            // Busca as bebidas com base na descrição
            List<Bebida> bebidasEncontradas = bebidaService.buscarPorDescricao(descricao);

            // Atualiza a TableView com os resultados da busca
            tView_Prod.setItems(FXCollections.observableArrayList(bebidasEncontradas));
        } else {
            // Se o campo estiver vazio, recarrega todos os dados
            atualizarTabela();
        }
    }

    public void onDeletarClickButton(ActionEvent actionEvent) {
        Bebida bebidaSelecionada = tView_Prod.getSelectionModel().getSelectedItem();

        if (bebidaSelecionada != null) {
            // Confirmação antes de deletar
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Você tem certeza que deseja deletar este produto?");
            alert.setContentText("Descrição: " + bebidaSelecionada.getDescricao());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Deleta o produto
                bebidaService.deletarBebida(bebidaSelecionada.getId());
                atualizarTabela(); // Atualiza a TableView após a exclusão
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Produto Selecionado");
            alert.setHeaderText("Por favor, selecione um produto para deletar.");
            alert.showAndWait();
        }
    }

    public void onAlterarClickButton(ActionEvent actionEvent) {
        Bebida bebidaSelecionada = tView_Prod.getSelectionModel().getSelectedItem();

        if (bebidaSelecionada != null) {
            // Abre a janela de edição
            Stage stage = abridorJanela.abrirNovaJanela("/views/alterar-prod-view.fxml", "Alterar Produtos",800,600);

            // obtem o controlador da janela de edição
            AlterarProdController controller = (AlterarProdController) abridorJanela.getController();

            // Passa o produto selecionado e o stage para o controlador
            controller.setBebidaSelecionada(bebidaSelecionada);
            controller.setStage(stage);

            // Atualiza a tabela após fechar a janela de edição
            stage.setOnHidden(e -> atualizarTabela());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Produto Selecionado");
            alert.setHeaderText("Por favor, selecione um produto para editar.");
            alert.showAndWait();
        }
    }
}