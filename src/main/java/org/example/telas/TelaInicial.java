package org.example.telas;


import org.example.util.AbridorJanela;
import javafx.fxml.FXMLLoader;
import org.springframework.stereotype.Component;

@Component
public class TelaInicial {

    AbridorJanela abridorJanela;

    public TelaInicial(AbridorJanela abridorJanela) {
        this.abridorJanela = abridorJanela;
    }

    public void abrir(){
        abridorJanela.abrirNovaJanela("/views/tela-inicial-view.fxml", "Tela Inicial", 800, 600);
    }

    public FXMLLoader getXMLLoader(){
        return abridorJanela.getFxmlLoader();
    }

}