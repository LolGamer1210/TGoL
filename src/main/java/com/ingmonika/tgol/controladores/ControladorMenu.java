package com.ingmonika.tgol.controladores;

import com.ingmonika.Console;
import com.ingmonika.tgol.Main;
import com.ingmonika.tgol.implementaciones.Controlador;
import com.ingmonika.tgol.utils.URLHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ControladorMenu implements Controlador {

    // Los diferentes elementos del Menú desde el FXML
    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    @FXML
    private CheckBox player2Type;

    @FXML
    private Button playButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button aboutButton;

    @FXML
    private Text version;

    @FXML
    private ChoiceBox<String> gridSize;

    private ObservableList<String> sizes = FXCollections.observableArrayList("5x5", "10x10", "15x15", "20x20");

    @FXML
    private void initialize() {
        // Añade el dropdown de tamaños para el Grid
        gridSize.setItems(sizes);

        // Botón de Jugar
        playButton.setOnAction(event -> {
            printButtonName(playButton);
            Console.log(Console.LogType.DEBUG, player1Name.getCharacters().toString());
            Console.log(Console.LogType.DEBUG, player2Name.getCharacters().toString());
            Main.loadScene("Juego.fxml");
        });

        // Botón de opciones
        optionsButton.setOnAction(event -> {
            printButtonName(optionsButton);
            showOptionsDialog();
        });

        // Botón de Acerca de
        aboutButton.setOnAction(event -> {
            printButtonName(aboutButton);
            showAboutInfo(event);
        });

        // Checkbox para cambiar el jugador 2 a CPU
        player2Type.selectedProperty().addListener((observable, oldValue, newValue) -> {
            player2Name.setDisable(newValue);
            if (newValue) {
                Console.log(Console.LogType.DEBUG, "Player 2 set to CPU");
            } else {
                Console.log(Console.LogType.DEBUG, "Player 2 set to player");
            }
        });

        // Versión
        version.setOnMouseClicked(event -> URLHelper.openURL("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
    }

    /// Imprime el nombre del botón en la consola.
    private void printButtonName(Button button) {
        Console.log(Console.LogType.DEBUG, button.getText());
    }

    /// Dialogo de opciones temporal.
    private void showOptionsDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Opciones");

        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Opción 1"),
                new CheckBox("Habilitar característica A"),
                new Label("Opción 2"),
                new CheckBox("Habilitar característica B"),
                new Label("Opción 3"),
                new CheckBox("Habilitar característica C")
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialog.showAndWait();
    }

    /// Alerta con la información para "Acerca de" (Temporal)
    private void showAboutInfo(Event event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The Game of LOL");
        alert.setHeaderText("Proyecto de Programación 2 - IS-210-1700");
        alert.setContentText("Creado por: \nMiembro 1\nMiembro 2\nMiembro 3");
        alert.showAndWait();
    }
}
