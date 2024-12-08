package com.ingmonika.tgol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//Utils imports
import com.ingmonika.tgol.utils.URLHelper;

//Custom imports because I hate sout
import com.ingmonika.Console;

//Controller implementations
import com.ingmonika.tgol.implementaciones.Controlador;

//Classes imports
import com.ingmonika.tgol.clases.Settings;
import com.ingmonika.tgol.clases.Jugador;

import java.io.IOException;

public class Main extends Application {
    public String buildVersion = "0.3a";
    private static Stage primaryStage;
    private static Application appInstance;
    private static Jugador[] jugadores;

    ///Los ajustes del juego son accesibles a través de setters y getters.
    static Settings gameSettings;

    @Override
    public void start(Stage stage) {

        //Configurando el nombre del proyecto en Console y el DebugType por defecto.
        Console.setProjectName("TGoL");
        Console.setDefaultType(Console.LogType.DEBUG);

        //Creando ajustes iniciales
        gameSettings = new Settings(5);

        primaryStage = stage;
        appInstance = this;

        // Pasando el HostServices al URLHelper
        URLHelper.setHostServices(getHostServices());

        loadScene("Menu.fxml", "Menú Principal del Juego");
    }

    /// Carga una nueva escena al Stage.
    ///
    /// @param fxml El archivo FXML a cargar.
    public static void loadScene(String fxml) {
        loadScene(fxml, primaryStage.getTitle());
    }

    /// Carga una nueva escena al Stage y cambia el titulo de la ventana.
    ///
    /// @param fxml  El archivo FXML a cargar.
    /// @param title El nuevo título de la ventana.
    public static void loadScene(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);

            // Interacciones con el controlador de ser necesarias
            Controlador controller = loader.getController();

            // Cambiar el Stilo con CSS
            scene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());

            // Cambiar escena en el Stage Primario
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///Retorna los ajustes actuales.
    public static Settings getGameSettings() {
        return gameSettings;
    }

    ///Configura un nuevo valor para los ajustes.
    public static void setGameSettings(Settings gameSettings) {
        Main.gameSettings = gameSettings;
    }

    ///Retorna un arreglo de jugadores.
    public static Jugador[] getJugadores() {
        return jugadores;
    }

    ///Configura el arreglo de jugadores.
    public static void setJugadores(Jugador[] jugadores) {
        Main.jugadores = jugadores;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
