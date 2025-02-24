package org.example.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import org.example.entities.Produto;
import org.example.entities.Produto_Vendido;
import org.example.entities.Venda;
import org.example.services.ProdutoService;
import org.example.services.Produto_VendidoService;
import org.example.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class PdvController implements Initializable {
    @FXML
    private TextField tf_Taxa;
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
    private TextField tf_Pesquisa, tf_ValorRecebido, tf_Desconto;

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
    @FXML
    private ComboBox<String> cb_FormaPag;


    @Autowired // Injeção do BebidaService
    private ProdutoService produtoService;
    @Autowired
    private VendaService  vendaService;
    @Autowired
    private Produto_VendidoService produtoVendidoService;

    private ObservableList<Produto_Vendido> produtosSelecionados = FXCollections.observableArrayList();

    float total = 0.0f;
    float trocoValor = 0.0f;
    float valorFinal = 0.0f;
    float taxa = 0.0f;
    float desconto = 0.0f;

    public PdvController(ProdutoService produtoService, VendaService vendaService, Produto_VendidoService produtoVendidoService) {
        this.produtoService = produtoService;
        this.vendaService = vendaService;
        this.produtoVendidoService = produtoVendidoService;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        total = 0.0f;
        trocoValor = 0.0f;
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
        //se o combo box mudar
        cb_FormaPag.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if("Credito".equals(newValue) || "Debito".equals(newValue)) {
               tf_Taxa.setVisible(true);
           }else{
                tf_Taxa.setVisible(false);
                tf_Taxa.setText("0.00");
           }

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
        tf_ValorRecebido.textProperty().addListener((observable, oldValue, newValue) -> {
            atualizarTroco();
        });

        tf_Taxa.setText("0.00");
        tf_Desconto.setText("0.00");
        tf_Taxa.textProperty().addListener((observable, oldValue, newValue) -> {
           atualizarTotal();
        });
        tf_Desconto.textProperty().addListener((observable, oldValue, newValue) -> {
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
            Produto_Vendido produtoVendido = new Produto_Vendido();
            produtoVendido.setId(produtoSelecionado.getId());
            produtoVendido.setPreco_vendido(produtoSelecionado.getPreco());
            produtoVendido.setQuantidade(1.0f);
            produtoVendido.setProduto(produtoSelecionado);

            // Adiciona à lista temporária
            produtosSelecionados.add(produtoVendido);
            atualizarTotal();
        }
    }
    private float getValorFinal() {

        taxa = Float.parseFloat(tf_Taxa.getText());
        desconto = Float.parseFloat(tf_Desconto.getText());

        valorFinal = (total - desconto) * (1 + (taxa / 100));
        return valorFinal;

    }
    private void atualizarTotal() {

        total = 0;
        // Percorre todos os produtos selecionados e soma (quantidade * preço)
        for (Produto_Vendido produto : produtosSelecionados) {
            total += produto.getQuantidade() * produto.getPreco_vendido();
        }

        // Atualiza a Label com o novo total formatado
        lb_Total.setText(String.format("%.2f", getValorFinal()));
        atualizarTroco();
    }
    private void atualizarTroco(){
        try{
            float valorRecebido = Float.parseFloat(tf_ValorRecebido.getText().replace(",", "."));
            trocoValor = valorRecebido - getValorFinal();
            lb_Troco.setText(String.format("%.2f", trocoValor));

        }catch (NumberFormatException e){
            lb_Troco.setText("0.00");
        }

    }


    public void onFinalizarButtonClick(ActionEvent actionEvent) {
       try {
           Venda venda = new Venda();
           venda.setData(new java.util.Date());
           venda.setTroco(trocoValor);
           venda.setForma_pagamento(cb_FormaPag.getValue());
           venda.setEsta_pago(true);
           venda.setTotal(total);
           venda.setValor_final(getValorFinal()); //total - desconto ?
           vendaService.salvar(venda);

           for (Produto_Vendido produtoVendido : produtosSelecionados) {
               produtoVendido.setVenda(venda); // Associar venda ao produto vendido
               produtoVendido.setProduto(produtoVendido.getProduto());
               produtoVendido.setQuantidade(produtoVendido.getQuantidade());
               produtoVendido.setPreco_vendido(produtoVendido.getPreco_vendido());

               produtoVendidoService.salvar(produtoVendido);


               // Atualizar estoque do produto original
               Produto produto = produtoVendido.getProduto();
               produto.setQuantidade(produto.getQuantidade() - produtoVendido.getQuantidade());
               produtoService.salvar(produto);
           }
           System.out.println("Venda finalizada com sucesso!");
           Stage stage = (Stage) tf_Desconto.getScene().getWindow();
           stage.close();
       }catch (NumberFormatException e){
           System.out.println("Erro ao finalizar venda: " + e.getMessage());
           e.printStackTrace();
       }

    }
}
