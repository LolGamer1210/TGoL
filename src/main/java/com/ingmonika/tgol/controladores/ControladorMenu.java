package com.ingmonika.tgol.controladores;

import com.ingmonika.Console;
import com.ingmonika.tgol.Main;
import com.ingmonika.tgol.clases.Jugador;
import com.ingmonika.tgol.implementaciones.Controlador;
import com.ingmonika.tgol.clases.Settings;
import com.ingmonika.tgol.utils.SizeConverter;
import com.ingmonika.tgol.utils.URLHelper;

import java.util.Arrays;
import java.util.Collections;
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
    private TextField nombreJugador1;

    @FXML
    private TextField nombreJugador2;

    @FXML
    private CheckBox tipoJugador2;

    @FXML
    private Button botonJugar;

    @FXML
    private Button botonOpciones;

    @FXML
    private Button botonAcercaDe;

    @FXML
    private Text version;

    @FXML
    private ChoiceBox<String> gridSize;

    private ObservableList<String> sizes = FXCollections.observableArrayList("5x5", "10x10", "15x15", "20x20");
    private Settings gameSettings = Main.getGameSettings();

    @FXML
    private void initialize() {
        // Añade el dropdown de tamaños para el Grid
        gridSize.setItems(sizes);
        gridSize.setValue(SizeConverter.intToString(gameSettings.selectedSize));


        // Botón de Jugar
        botonJugar.setOnAction(event -> {

            //Debug
            printButtonName(botonJugar);
            Console.log(Console.LogType.DEBUG, nombreJugador1.getCharacters().toString());
            Console.log(Console.LogType.DEBUG, nombreJugador2.getCharacters().toString());

            //Configura los jugadores
            configurarJugadores();

            //Define los ajustes y cambia al juego.
            gameSettings.setSelectedSize(SizeConverter.stringToInt(gridSize.getValue()));
            Main.loadScene("Juego.fxml");
        });

        // Botón de opciones
        botonOpciones.setOnAction(event -> {
            printButtonName(botonOpciones);
            dialogoOpciones();
        });

        // Botón de Acerca de
        botonAcercaDe.setOnAction(event -> {
            printButtonName(botonAcercaDe);
            acercaDe(event);
        });

        // Checkbox para cambiar el jugador 2 a CPU
        tipoJugador2.selectedProperty().addListener((observable, oldValue, newValue) -> {
            nombreJugador2.setDisable(newValue);
            if (newValue) {
                Console.log(Console.LogType.DEBUG, "Player 2 set to CPU");
            } else {
                Console.log(Console.LogType.DEBUG, "Player 2 set to player");
            }
        });

        // Versión
        version.setOnMouseClicked(event -> URLHelper.openURL("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
    }

    ///Configura los jugadores con el nombre actual y la selección de sí es CPU.
    ///La letra que se les asigna es al azar.
    private void configurarJugadores(){
        //Generar letras L y O en orden aleatorio.
        Character[] letra = {'L', 'O'};
        Collections.shuffle(Arrays.asList(letra));

        //Revisa si los nombres de los jugadores están vacíos.
        String nombre1, nombre2;
        if(nombreJugador1.getCharacters().isEmpty()){
            nombre1 = "Jugador 1";
        } else {nombre1 = nombreJugador1.getCharacters().toString();};

        if(nombreJugador2.getCharacters().isEmpty()){
            nombre2 = "Jugador 2";
        } else {nombre2 = nombreJugador2.getCharacters().toString();};

        Jugador jugador1 = new Jugador(
                nombre1,
                letra[0],
                false,
                0
        );
        Jugador jugador2 = new Jugador(
                nombre2,
                letra[1],
                tipoJugador2.isSelected(),
                1
        );

        //Creando areglo de jugadores.
        Jugador[] jugadores = new Jugador[2];
        jugadores[0] = jugador1;
        jugadores[1] = jugador2;

        //Setteando en Main.
        Main.setJugadores(jugadores);
    }

    ///Imprime el nombre del botón en la consola.
    private void printButtonName(Button button) {
        Console.log(Console.LogType.DEBUG, button.getText());
    }

    ///Dialogo de opciones temporal.
    private void dialogoOpciones() {
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

    ///Alerta con la información para "Acerca de".
    private void acercaDe(Event event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The Game of LOL");
        alert.setHeaderText("Proyecto de Programación 2 - IS-210-1700");
        alert.setContentText("Creado por: \nSaid Chacón - 20222130041\nGordito 1\nPelón 2");
        alert.showAndWait();
    }
}
