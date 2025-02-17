package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.example.util.SpringFXMLLoader;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class TelaInicialController implements Initializable {

    @FXML
    private BorderPane conteudoPane;

    private final SpringFXMLLoader fxmlLoader;

    public TelaInicialController(SpringFXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    private void carregarTela(String fxml) {
        try {
            String resourcePath = "/views/" + fxml;
            System.out.println("Carregando: " + resourcePath);

            Parent tela = fxmlLoader.load(resourcePath).getRoot(); // Pega a raiz do FXML carregado
            conteudoPane.setCenter(tela);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela: " + fxml);
        }
    }

    @FXML
    public void onCadastroProdButtonClick(ActionEvent actionEvent) {
        carregarTela("produtos-view.fxml");
    }

    @FXML
    public void onVendasButtonClick(ActionEvent actionEvent) {
        carregarTela("vendas-view.fxml");
    }

    @FXML
    public void onClientesButtonClick(ActionEvent actionEvent) {
        carregarTela("clientes-view.fxml");
    }

    @FXML
    public void onRelatoriosButtonClick(ActionEvent actionEvent) {
        carregarTela("relatorios-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarTela("main-view.fxml");
    }

    public void onInicioButtonClick(ActionEvent actionEvent) {
        carregarTela("main-view.fxml");
    }
}
