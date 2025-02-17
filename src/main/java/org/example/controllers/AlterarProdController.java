package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.entities.Bebida;
import org.example.services.BebidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class AlterarProdController implements Initializable {

    @FXML
    private TextField tf_CodigoBarra, tf_Desc, tf_Quant, tf_Preco;

    @Autowired
    private BebidaService bebidaService;
    private Bebida bebidaSelecionada; // Produto selecionado para edição
    private Stage stage; // Referência ao stage da janela de edição

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setBebidaSelecionada(Bebida bebida) {
        this.bebidaSelecionada = bebida;
        carregarDados();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private void carregarDados() {
        if (bebidaSelecionada != null) {
            tf_Desc.setText(bebidaSelecionada.getDescricao());
            tf_Quant.setText(String.valueOf(bebidaSelecionada.getQuantidade()));
            tf_Preco.setText(String.valueOf(bebidaSelecionada.getPreco()));
            tf_CodigoBarra.setText(bebidaSelecionada.getCodigo_barras());

        }
    }
    @FXML
    public void onSalvarButtonClick(ActionEvent actionEvent) {
        if (bebidaSelecionada != null) {
            // Atualiza os dados do produto
            bebidaSelecionada.setDescricao(tf_Desc.getText());
            bebidaSelecionada.setQuantidade(Float.parseFloat(tf_Quant.getText()));
            bebidaSelecionada.setPreco(Float.parseFloat(tf_Preco.getText()));
            bebidaSelecionada.setCodigo_barras(tf_CodigoBarra.getText());


            // Salva as alterações no banco de dados
            bebidaService.salvar(bebidaSelecionada);

            // Fecha a janela de edição
            stage.close();
        }
    }

    public void onCancelarButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) tf_CodigoBarra.getScene().getWindow();
        stage.close();
    }
}
