package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.example.telas.TelaInicial;
import javafx.fxml.FXML;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.net.URL;

@Controller
public class TelaInicialController {

    private TelaInicial telaInicial;

    @FXML
    private BorderPane conteudoPane;

    private void carregarTela(String fxml) {
        try {
            String resourcePath = "/views/" + fxml;
            System.out.println("Carregando: " + resourcePath);

            URL fxmlUrl = getClass().getResource(resourcePath);
            System.out.println("URL encontrada: " + fxmlUrl); // Teste importante!

            if (fxmlUrl == null) {
                throw new IOException("Arquivo FXML não encontrado: " + resourcePath);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent tela = loader.load();
            conteudoPane.setCenter(tela);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela: " + fxml);
        }
    }

    public TelaInicialController(TelaInicial telaInicial) {
        this.telaInicial = telaInicial;
    }


    @FXML
    public void onCadastroProdButtonClick(ActionEvent actionEvent) {
        carregarTela("cadastro-prod-view.fxml");
    }
    @FXML
    public void onVendasButtonClick(ActionEvent actionEvent) {
        carregarTela("vendas-view.fxml");
    }

    public void onClientesButtonClick(ActionEvent actionEvent) {
        carregarTela("clientes-view.fxml");
    }

    public void onRelatoriosButtonClick(ActionEvent actionEvent) {
        carregarTela("relatorios-view.fxml");
    }
}