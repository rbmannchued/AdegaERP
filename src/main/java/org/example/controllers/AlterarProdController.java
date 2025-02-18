package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.entities.Produto;
import org.example.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class AlterarProdController implements Initializable {

    @FXML
    private TextField tf_CodigoBarra, tf_Desc, tf_Quant, tf_Preco;

    @Autowired
    private ProdutoService produtoService;
    private Produto produtoSelecionada; // Produto selecionado para edição
    private Stage stage; // Referência ao stage da janela de edição

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setProdutoSelecionada(Produto produto) {
        this.produtoSelecionada = produto;
        carregarDados();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private void carregarDados() {
        if (produtoSelecionada != null) {
            tf_Desc.setText(produtoSelecionada.getDescricao());
            tf_Quant.setText(String.valueOf(produtoSelecionada.getQuantidade()));
            tf_Preco.setText(String.valueOf(produtoSelecionada.getPreco()));
            tf_CodigoBarra.setText(produtoSelecionada.getCodigo_barras());

        }
    }
    @FXML
    public void onSalvarButtonClick(ActionEvent actionEvent) {
        if (produtoSelecionada != null) {
            // Atualiza os dados do produto
            produtoSelecionada.setDescricao(tf_Desc.getText());
            produtoSelecionada.setQuantidade(Float.parseFloat(tf_Quant.getText()));
            produtoSelecionada.setPreco(Float.parseFloat(tf_Preco.getText()));
            produtoSelecionada.setCodigo_barras(tf_CodigoBarra.getText());


            // Salva as alterações no banco de dados
            produtoService.salvar(produtoSelecionada);

            // Fecha a janela de edição
            stage.close();
        }
    }

    public void onCancelarButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) tf_CodigoBarra.getScene().getWindow();
        stage.close();
    }
}
