package org.example.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class AbridorJanela {

    private final SpringFXMLLoader fxmlLoader;
    private FXMLLoader loader;

    public AbridorJanela(SpringFXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public Stage abrirNovaJanela(String fxmlPath, String titulo, double width, double height) {
        try {
            loader = fxmlLoader.load(fxmlPath);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(titulo);
            stage.setWidth(width);
            stage.setHeight(height);
           // stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(true);
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();

            return stage; // Retorna a Stage para que quem chamar possa manipular
        } catch (Exception e) {
            System.out.println("Erro ao abrir janela: " + e.getMessage());
            return null; // Retorna null caso ocorra um erro
        }
    }
    public <T> T getController() {
        return loader.getController();
    }

    public FXMLLoader getFxmlLoader() {
        return loader;
    }

}