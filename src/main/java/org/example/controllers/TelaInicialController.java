package org.example.controllers;

import org.example.telas.TelaInicial;
import javafx.fxml.FXML;
import org.springframework.stereotype.Controller;

@Controller
public class TelaInicialController {

    private TelaInicial telaInicial;

    public TelaInicialController(TelaInicial telaInicial) {
        this.telaInicial = telaInicial;
    }

    @FXML
    protected void onAlunosButtonClick() {
        telaInicial.abrir();
    }

}