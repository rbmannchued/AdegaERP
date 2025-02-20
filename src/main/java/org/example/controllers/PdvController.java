package org.example.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import org.example.entities.Produto;
import org.example.entities.Produto_Vendido;
import org.example.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class PdvController implements Initializable {
    @FXML
    private TableView<Produto> tView_Prod;

    @FXML
    private TableColumn<Produto, Integer> prodIdCol;

    @FXML
    private TableColumn<Produto, String> prodDescCol;

    @FXML
    private TableColumn<Produto, Float> prodQuantCol;

    @FXML
    private TableColumn<Produto, Float> prodPrecoCol;

    @FXML
    private TableColumn<Produto, String> prodCbCol;

    @FXML
    private TableColumn<Produto, Boolean> prodNfCol;

    @FXML
    private TextField tf_Pesquisa;

    @FXML
    private TableView<Produto_Vendido> tView_ProdSelec;
    @FXML
    private TableColumn<Produto_Vendido, Integer> prodSelecIdCol;
    @FXML
    private TableColumn<Produto_Vendido, Float> prodSelecQuantCol;
    @FXML
    private TableColumn<Produto_Vendido, String> prodSelecDescCol;
    @FXML
    private TableColumn<Produto_Vendido, Float> prodSelectPrecoCol;

    @FXML
    private Label lb_Total, lb_Troco;


    @Autowired // Injeção do BebidaService
    private ProdutoService produtoService;

    private ObservableList<Produto_Vendido> produtosSelecionados = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        List<Produto> produtos = produtoService.buscarTodos();

        // Converte a lista para ObservableList
        ObservableList<Produto> produtosObservable = FXCollections.observableArrayList(produtos);

        // Associa os dados à TableView
        tView_Prod.setItems(produtosObservable);
        //pesquisar automaticamente ao digitar
        tf_Pesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            buscarProduto();
        });

        prodSelecIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodSelecDescCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduto().getDescricao())
        );
        prodSelecQuantCol.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        prodSelectPrecoCol.setCellValueFactory(new PropertyValueFactory<>("preco_vendido"));

        // Permitir edição das colunas quantidade e preço
        tView_ProdSelec.setEditable(true);

        prodSelecQuantCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        prodSelecQuantCol.setOnEditCommit(event -> {
            Produto_Vendido produto = event.getRowValue();
            produto.setQuantidade(event.getNewValue());
            atualizarTotal();
        });

        prodSelectPrecoCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        prodSelectPrecoCol.setOnEditCommit(event -> {
            Produto_Vendido produto = event.getRowValue();
            produto.setPreco_vendido(event.getNewValue());
            atualizarTotal();
        });
        atualizarTotal();
        configurarEventoEnter();
        lb_Total.setText("0.00");
        produtosSelecionados.clear();
        tView_ProdSelec.setItems(produtosSelecionados);
        atualizarTabela();

    }

    private void buscarProduto(){
        String descricao = tf_Pesquisa.getText().trim(); // Obtém o texto digitado

        if (!descricao.isEmpty()) {
            // Busca as bebidas com base na descrição
            List<Produto> produtosEncontradas = produtoService.buscarPorDescricao(descricao);

            // Atualiza a TableView com os resultados da busca
            tView_Prod.setItems(FXCollections.observableArrayList(produtosEncontradas));
        } else {
            // Se o campo estiver vazio, recarrega todos os dados
            atualizarTabela();
        }
    }
    private void atualizarTabela() {
        // Busca novamente os dados do banco
        List<Produto> produtosAtualizadas = produtoService.buscarTodos();

        // Atualiza os dados na TableView
        tView_Prod.setItems(FXCollections.observableArrayList(produtosAtualizadas));
    }
    @FXML
    private void configurarEventoEnter() {
        tView_Prod.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    adicionarProdutoSelecionado();
                    break;
                default:
                    break;
            }
        });
    }
    private void adicionarProdutoSelecionado() {
        Produto produtoSelecionado = tView_Prod.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            // Criar uma nova instância do Produto_Vendido
            Produto_Vendido produtoVendido = new Produto_Vendido(
                    10, // Deixa o ID como null, pois ainda não foi salvo no banco
                    1.0f, // Quantidade inicial
                    produtoSelecionado.getPreco(),
                    produtoSelecionado,
                    null // Venda será definida depois
            );

            // Adiciona à lista temporária
            produtosSelecionados.add(produtoVendido);
            atualizarTotal();
        }
    }
    private void atualizarTotal() {
        float total = 0.0f;

        // Percorre todos os produtos selecionados e soma (quantidade * preço)
        for (Produto_Vendido produto : produtosSelecionados) {
            total += produto.getQuantidade() * produto.getPreco_vendido();
        }

        // Atualiza a Label com o novo total formatado
        lb_Total.setText(String.format("%.2f", total));
    }


}
