package com.ingmonika.tgol.controladores;

import com.ingmonika.Console;
import com.ingmonika.tgol.Main;
import com.ingmonika.tgol.clases.CPU;
import com.ingmonika.tgol.clases.Jugador;
import com.ingmonika.tgol.implementaciones.Controlador;
import com.ingmonika.tgol.clases.Settings;
import com.ingmonika.tgol.utils.JsonHelper;
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
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

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
    private Settings gameSettings;

    @FXML
    private void initialize() {
        //Importando ajustes.
        gameSettings = Main.getGameSettings();

        //Cambiando versión
        version.setText(Main.getBuildVersion());

        // Añade el dropdown de tamaños para el Grid
        gridSize.setItems(sizes);
        gridSize.setValue(SizeConverter.intToString(gameSettings.tamanioSeleccionado));

        //Cargando los ultimos nombres de los jugadores.
        nombreJugador1.setText(gameSettings.getNombreJ1());
        nombreJugador2.setText(gameSettings.getNombreJ2());

        // Botón de Jugar
        botonJugar.setOnAction(event -> {
            //Debug
            printButtonName(botonJugar);
            Console.log(Console.LogType.DEBUG, nombreJugador1.getCharacters().toString());
            Console.log(Console.LogType.DEBUG, nombreJugador2.getCharacters().toString());

            //Configura los jugadores
            configurarJugadores();

            //Define y guarda los ajustes y cambia al juego.
            gameSettings.setTamanioSeleccionado(SizeConverter.stringToInt(gridSize.getValue()));
            JsonHelper.guardarSettings(gameSettings);
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
        tipoJugador2.setSelected(gameSettings.isJugadorCPU());
        nombreJugador2.setDisable(gameSettings.isJugadorCPU());
        tipoJugador2.selectedProperty().addListener((observable, oldValue, newValue) -> {
            nombreJugador2.setDisable(newValue);
            if (newValue) {
                Console.log(Console.LogType.DEBUG, "Player 2 set to CPU");
            } else {
                Console.log(Console.LogType.DEBUG, "Player 2 set to player");
            }
            gameSettings.setJugadorCPU(newValue);
            JsonHelper.guardarSettings(gameSettings);
        });

        // Versión
        version.setOnMouseClicked(event -> URLHelper.openURL("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
    }

    ///Configura los jugadores con el nombre actual y la selección de sí es CPU.
    ///La letra que se les asigna es al azar.
    private void configurarJugadores(){
        //Generar letras L y O en orden aleatorio.
        String[] letra = {"L", "O"};
        Collections.shuffle(Arrays.asList(letra));

        //Revisa si los nombres de los jugadores están vacíos.
        String nombre1, nombre2;
        if(nombreJugador1.getCharacters().isEmpty()){
            nombre1 = "Jugador 1";
        } else {nombre1 = nombreJugador1.getCharacters().toString();};

        if(nombreJugador2.getCharacters().isEmpty()){
            nombre2 = "Jugador 2";
        } else {nombre2 = nombreJugador2.getCharacters().toString();};

        gameSettings.setNombreJ1(nombre1);
        gameSettings.setNombreJ2(nombre2);

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

        //Setteando en Main.
        Main.setJugadores(new Jugador[]{jugador1,jugador2});
    }

    ///Imprime el nombre del botón en la consola.
    private void printButtonName(Button button) {
        Console.log(Console.LogType.DEBUG, button.getText());
    }

    ///Dialogo de opciones temporal.
    private void dialogoOpciones() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Opciones");

        ColorPicker colorJugador1 = new ColorPicker();
        ColorPicker colorJugador2= new ColorPicker();
        ColorPicker colorLOL= new ColorPicker();
        ColorPicker colorUltima = new ColorPicker();
        CheckBox CPUDificil = new CheckBox("CPU en modo dificil");

        //Cargando los ajustes guardados.
        colorJugador1.setValue(Color.web(gameSettings.getColorJ1()));
        colorJugador2.setValue(Color.web(gameSettings.getColorJ2()));
        colorLOL.setValue(Color.web(gameSettings.getColorLOL()));
        colorUltima.setValue(Color.web(gameSettings.getColorUltima()));

        //Añadir listeners para cambiar ajustes.
        colorJugador1.setOnAction(e -> {
            String color = convertirColorHexadecimal(colorJugador1.getValue().toString());
            gameSettings.setColorJ1(color);
            Console.log("Color del jugador 1 cambiado a: " + color);
        });
        colorJugador2.setOnAction(e -> {
            String color = convertirColorHexadecimal(colorJugador2.getValue().toString());
            gameSettings.setColorJ2(color);
            Console.log("Color del jugador 2 cambiado a: " + color);
        });
        colorLOL.setOnAction(e -> {
            String color = convertirColorHexadecimal(colorLOL.getValue().toString());
            gameSettings.setColorLOL(color);
            Console.log("Color de LOL cambiado a: " + color);
        });
        colorUltima.setOnAction(e -> {
            String color = convertirColorHexadecimal(colorUltima.getValue().toString());
            gameSettings.setColorUltima(color);
            Console.log("Color del ultimo movimiento cambiado a: " + color);
        });
        CPUDificil.selectedProperty().addListener((observable, oldValue, newValue) -> {
            gameSettings.setCPUDificil(newValue);
            JsonHelper.guardarSettings(gameSettings);
            Console.log("Se cambió el CPU a dificil!");
        });

        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Seleccione color del jugador 1:"),
                colorJugador1,
                new Label("Seleccione color del jugador 2:"),
                colorJugador2,
                new Label("Seleccione color de los LOL:"),
                colorLOL,
                new Label("Seleccione color del último movimiento:"),
                colorUltima,
                CPUDificil
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialog.showAndWait();
        JsonHelper.guardarSettings(gameSettings);
    }


    ///Alerta con la información para "Acerca de".
    private void acercaDe(Event event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The Game of LOL");
        alert.setHeaderText("Proyecto de Programación 2 - IS-210-1700");
        alert.setContentText("Creado por: \nSaid Chacón - 20222130041\nGordito 1\nPelón 2");
        alert.showAndWait();
    }

    //Función auxiliar para el colorPicker.
    /// Convierte un color del formato "0xb31a1aff" a formato hexadecimal "#RRGGBB".
    private String convertirColorHexadecimal(String colorPickerString) {
        String hexColor = colorPickerString.substring(2, 8).toUpperCase();
        return "#" + hexColor;
    }

}
