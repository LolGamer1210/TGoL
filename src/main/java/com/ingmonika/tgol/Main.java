package com.ingmonika.tgol;

import com.ingmonika.tgol.controladores.ControladorJuego;
import com.ingmonika.tgol.utils.URLHelper;
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

        // Pasando el HostServices al URLHelper
        URLHelper.setHostServices(getHostServices());

        loadScene("Menu.fxml", "Menú Principal del Juego");
    }

    /**
     * Carga una nueva escena al Stage.
     *
     * @param fxml El archivo FXML a cargar.
     */
    public static void loadScene(String fxml) {
        loadScene(fxml, primaryStage.getTitle());
    }

    /**
     * Carga una nueva escena al Stage y cambia el titulo de la ventana.
     *
     * @param fxml  El archivo FXML a cargar.
     * @param title El nuevo título de la ventana.
     */
    public static void loadScene(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);

            // Cambiar el Stilo con CSS
            scene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());

            // Cambiar escena en el Stage Primario
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Interacciones con el controlar de ser necesarias
            Controlador controller = loader.getController();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
