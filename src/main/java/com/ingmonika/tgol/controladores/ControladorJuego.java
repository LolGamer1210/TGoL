package com.ingmonika.tgol.controladores;

import com.ingmonika.Console;
import com.ingmonika.tgol.Main;
import com.ingmonika.tgol.clases.Jugador;
import com.ingmonika.tgol.implementaciones.Controlador;
import com.ingmonika.tgol.utils.URLHelper;
import com.ingmonika.tgol.clases.Settings;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.Queue;

public class ControladorJuego implements Controlador {

    @FXML
    public Label consolaJuego;

    @FXML
    private Button atras;

    @FXML
    private Text version;

    @FXML
    private HBox hboxArriba;

    @FXML
    private VBox vboxAbajo;

    @FXML
    private VBox vboxIzquierda;

    @FXML
    private VBox vboxDerecha;

    @FXML
    private Label tituloJugador1;

    @FXML
    private Label tituloJugador2;

    @FXML
    private GridPane grid;

    @FXML
    private TextArea ultimoMovimientoJugador1;

    @FXML
    private TextArea ultimoMovimientoJugador2;

    private final Queue<String> movimientosJugador1 = new LinkedList<>();
    private final Queue<String> movimientosJugador2 = new LinkedList<>();
    private static final int MAX_MOVIMIENTOS = 10;

    private final Settings gameSettings = Main.getGameSettings();
    private final Button[][] buttons = new Button[gameSettings.selectedSize][gameSettings.selectedSize];
    private final Jugador[] jugadores = Main.getJugadores();
    private Jugador jugadorActual = jugadores[1];

    @FXML
    private void initialize() {
        //Botón para regresar al menú principal.
        atras.setOnAction(event -> Main.loadScene("Menu.fxml"));

        //Versión
        version.setOnMouseClicked(event -> URLHelper.openURL("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));

        //Crear grid según el tamaño seleccionado en el menú
        int gridSize = gameSettings.selectedSize;
        Console.log("Selected grid size: " + gridSize);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(0);
        grid.setVgap(0);
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Button button = new Button("");
                button.setPrefSize(600.0 / gridSize, 600.0 / gridSize);
                final int x = i, y = j;
                button.setOnAction(e -> Jugada(x, y));
                buttons[i][j] = button;
                grid.add(button, j, i);
            }
        }

        //Escribir el nombre de los jugadores.
        String titulo1, titulo2;

        //Revisando si jugador es CPU.
        if(jugadores[0].esCPU()) {
            titulo1 = "(CPU) " + jugadores[0].getNombre();
        } else {titulo1 = jugadores[0].getNombre();}

        if(jugadores[1].esCPU()) {
            titulo2 = "(CPU) " + jugadores[1].getNombre();
        } else {titulo2 = jugadores[1].getNombre();}

        tituloJugador1.setText(titulo1);
        tituloJugador2.setText(titulo2);
    }

    ///Manejo de jugada.
    private void Jugada(int fila, int columna){
        jugadorActual = cambioJugador();
        consolaJuegoLog("Turno de ",cambioJugador().getNombre(), " (", cambioJugador().getLetra(), ")");
        Console.log("Click en celda ", String.valueOf(fila),",", String.valueOf(columna));
        buttons[fila][columna].setText(jugadorActual.getLetra());
        añadirMovimiento(jugadorActual.getNombre(), " colocó " + jugadorActual.getLetra() + " en (" + fila + "," + columna + ")\n");
    };

    ///Consola en al VBox de abajo en el juego
    ///@param mensaje un String o array de String el cual se parseará a la consola dentro del juego.
    private void consolaJuegoLog(String ...mensaje) {
        consolaJuego.setText(String.join(" ",mensaje));
        Console.log("Logged to Game Console: ", String.join(" ",mensaje));
    }

    ///Cambio de jugador.
    private Jugador cambioJugador(){
        if(jugadorActual.getNumero() == 0) {
            return jugadores[1];
        } else return jugadores[0];
    }

    ///Añade los ultimos movimientos de los jugadores en los cuadros de texto.
    public void añadirMovimiento(String jugador, String movimiento) {
        if (jugador.equals(jugadores[0].getNombre())) {
            if (movimientosJugador1.size() >= MAX_MOVIMIENTOS) {
                movimientosJugador1.poll(); // Eliminar el movimiento más antiguo
            }
            movimientosJugador1.add(movimiento);
            actualizarUltimoMovimiento(ultimoMovimientoJugador1, movimientosJugador1);
        } else if (jugador.equals(jugadores[1].getNombre())) {
            if (movimientosJugador2.size() >= MAX_MOVIMIENTOS) {
                movimientosJugador2.poll(); // Eliminar el movimiento más antiguo
            }
            movimientosJugador2.add(movimiento);
            actualizarUltimoMovimiento(ultimoMovimientoJugador2, movimientosJugador2);
        } else { consolaJuegoLog("Movimiento desconocido...", jugador, movimiento);};
    }

    private void actualizarUltimoMovimiento(TextArea textArea, Queue<String> movimientos) {
        textArea.setText(String.join("\n", movimientos));
    }

    ///Imprime el texto del botón.
    private void printButtonName(Button button) {
        Console.log(Console.LogType.DEBUG, button.getText());
    }

}


