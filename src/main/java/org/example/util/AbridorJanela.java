package org.example.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.util.SpringFXMLLoader;
import org.springframework.stereotype.Component;

@Component
public class AbridorJanela {

    private final SpringFXMLLoader fxmlLoader;
    private FXMLLoader loader;

    public AbridorJanela(SpringFXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public void abrirNovaJanela(String fxmlPath, String titulo, double width, double height) {
        try {
            loader = fxmlLoader.load(fxmlPath);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(titulo);
            stage.setWidth(width);
            stage.setHeight(height);
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();
        } catch (Exception e) {
            System.out.println("Erro ao abrir janela: " + e.getMessage());
        }
    }

    public FXMLLoader getFxmlLoader() {
        return loader;
    }

}