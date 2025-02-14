package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CadastroProdutosController {

    @FXML
    public Label lbMain;

    public void onTesteButtonClick(ActionEvent actionEvent) {
        lbMain.setText("TESTE");
    }
}
