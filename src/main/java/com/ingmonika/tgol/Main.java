package com.ingmonika.tgol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//Custom imports because I hate sout
import com.ingmonika.Console;
import com.ingmonika.Console.LogType;

import java.io.IOException;

public class Main extends Application {
    public String buildVersion = "0.1a";
    @Override
    public void start(Stage primaryStage) {
        Console.setProjectName("TGoL");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);

            MenuController controller = loader.getController();
            controller.setHostServices(getHostServices());

            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            primaryStage.setTitle("Menú Principal del Juego");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
