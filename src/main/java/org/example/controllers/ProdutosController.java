package org.example.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.entities.Bebida;
import org.example.services.BebidaService;
import org.example.util.AbridorJanela;
import org.hibernate.resource.beans.container.spi.AbstractCdiBeanContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
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
}