package com.ingmonika.tgol;

import com.ingmonika.tgol.controladores.ControladorJuego;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// Custom imports because I hate sout
import com.ingmonika.Console;

//Controller implementations
import com.ingmonika.tgol.implementaciones.Controlador;

import java.io.IOException;

public class Main extends Application {
    public String buildVersion = "0.1a";
    private static Stage primaryStage;
    private static Application appInstance;

    @Override
    public void start(Stage stage) {
        Console.setProjectName("TGoL");
        primaryStage = stage;
        appInstance = this;
        loadScene("Menu.fxml", "Menú Principal del Juego");
    }

    /**
     * Loads a new scene into the primary stage.
     *
     * @param fxml The FXML file to load.
     */
    public static void loadScene(String fxml) {
        loadScene(fxml, primaryStage.getTitle());
    }

    /**
     * Loads a new scene into the primary stage and changes the title.
     *
     * @param fxml  The FXML file to load.
     * @param title The title of the window.
     */
    public static void loadScene(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);

            // Set stylesheets
            scene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());

            // Set the scene on the primary stage
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Controller interaction
            Controlador controller = loader.getController();
            controller.setHostServices(appInstance.getHostServices());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
