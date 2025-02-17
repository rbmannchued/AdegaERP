package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.example.entities.Bebida;
import org.example.services.BebidaService;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class AddProdController implements Initializable {
    @FXML
    private TextField tf_CodigoBarra, tf_Desc, tf_Quant, tf_Preco;

    @Autowired
    private BebidaService bebidaService;

    public void onSalvarButtonClick(ActionEvent actionEvent) {
        try {
            // Captura os valores dos TextFields
            String codigoBarras = tf_CodigoBarra.getText();
            String descricao = tf_Desc.getText();
            float quantidade = Float.parseFloat(tf_Quant.getText());
            float preco = Float.parseFloat(tf_Preco.getText());

            // Chama o metodo para inserir no banco
            Bebida novaBebida = bebidaService.inserirBebida(descricao, quantidade, preco, codigoBarras, true);

            // Limpa os campos após salvar
            tf_CodigoBarra.setText(null);
            tf_Desc.setText(null);
            tf_Quant.setText(null);
            tf_Preco.setText(null);

            System.out.println("Produto salvo com sucesso: " + novaBebida);
            // Fecha a janela
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            System.out.println("Erro: Quantidade e preço devem ser números válidos.");
        } catch (Exception e) {
            System.out.println("Erro ao salvar o produto: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
