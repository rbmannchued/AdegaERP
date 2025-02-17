package org.example;

import org.example.util.AbridorJanela;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class SpringInitializer extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(Main.class).run();
    }

    @Override
    public void start(Stage stage) throws IOException {
        AbridorJanela abridorJanela = applicationContext.getBean(AbridorJanela.class);
        abridorJanela.abrirNovaJanela("/views/tela-inicial-view.fxml", "Tela inicial", 1200, 800);
    }

    @Override
    public void stop() {
        applicationContext.close();
    }

}