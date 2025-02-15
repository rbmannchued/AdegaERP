package org.example;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = "org.example") // Escaneia todos os pacotes dentro de "org.example"
public class  Main {
    public static void main(String[] args) {
       // System.out.println("Hello, World!");
        Application.launch(SpringInitializer.class, args);
    }
}