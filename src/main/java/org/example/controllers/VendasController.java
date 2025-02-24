package org.example.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.entities.Produto;
import org.example.entities.Produto_Vendido;
import org.example.entities.Venda;
import org.example.services.Produto_VendidoService;
import org.example.services.VendaService;
import org.example.util.AbridorJanela;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


@Controller
public class VendasController implements Initializable {

    @Autowired
    private AbridorJanela abridorJanela;

    @FXML
    private TableView<Venda> tView_Vendas;
    @FXML
    private TableView<Produto_Vendido> tView_ProdVendidos;
    @FXML
    private TableColumn<Venda, Integer> vendasIdCol;
    @FXML
    private TableColumn<Venda, Float> vendasValorFinalCol;
    @FXML
    private TableColumn<Venda, String> vendasFormaPagCol;
    @FXML
    private TableColumn<Venda, Date> vendasDataCol;
    @FXML
    private TableColumn<Venda, Float> vendasTotalCol;

    @FXML
    private TableColumn<Produto_Vendido, String> prodVendidosDescCol;
    @FXML
    private TableColumn<Produto_Vendido, Float> prodVendidosQuantCol;
    @FXML
    private TableColumn<Produto_Vendido, Float> prodVendidosPrecoCol;

    @Autowired
    private VendaService vendaService;
    @Autowired
    private Produto_VendidoService produto_VendidoService;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configuração das colunas tabela vendas
        vendasIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        vendasValorFinalCol.setCellValueFactory(new PropertyValueFactory<>("valor_final"));
        vendasFormaPagCol.setCellValueFactory(new PropertyValueFactory<>("forma_pagamento"));
        vendasDataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        vendasTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Configuração das colunas tabela produtos vendidos
        prodVendidosDescCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduto().getDescricao())
        );
       // prodVendidosDescCol.setCellValueFactory(new PropertyValueFactory<>("produto.descricao"));
        prodVendidosPrecoCol.setCellValueFactory(new PropertyValueFactory<>("preco_vendido"));
        prodVendidosQuantCol.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        tView_Vendas.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double totalWidth = newWidth.doubleValue();
            vendasValorFinalCol.setPrefWidth(totalWidth * 0.3); //
        });
        // Recupera os dados do banco de dados usando o serviço
        List<Venda> vendas = vendaService.buscarTodos();

        // Converte a lista para ObservableList
        ObservableList<Venda> vendasObservable = FXCollections.observableArrayList(vendas);

        // Associa os dados à TableView
        tView_Vendas.setItems(vendasObservable);
        tView_Vendas.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        // Quando uma venda é selecionada, atualiza a tabela de produtos vendidos
                        atualizarTabelaProdutosVendidos(newSelection.getId());
                    }
                }
        );


    }
    private void atualizarTabelaVenda() {
        // Busca novamente os dados do banco
        List<Venda> vendaAtualizadas = vendaService.buscarTodos();

        // Atualiza os dados na TableView
        tView_Vendas.setItems(FXCollections.observableArrayList(vendaAtualizadas));
    }
    private void atualizarTabelaProdutosVendidos(Integer id){
        List<Produto_Vendido> produtosVendidos = produto_VendidoService.findProdutosVendidosByVendaId(id);

        System.out.println("Produtos vendidos encontrados: " + produtosVendidos.size());

        ObservableList<Produto_Vendido> produtosObservable = FXCollections.observableArrayList(produtosVendidos);

        tView_ProdVendidos.getItems().clear();
        tView_ProdVendidos.setItems(produtosObservable);
        tView_ProdVendidos.refresh();

        // Teste para garantir que os dados estão realmente na tabela
        System.out.println("Itens na TableView: " + tView_ProdVendidos.getItems().size());

    }

    public void onVenderButtonClick(ActionEvent actionEvent) {
        Stage stage = abridorJanela.abrirNovaJanela("/views/pdv-view.fxml",
                "Ponto de Venda",800,600);
        if (stage != null) {
            // Aguarda o fechamento da janela e atualiza a tabela
            stage.setOnHidden(event -> atualizarTabelaVenda());
        }
    }

}
