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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    private Label puntuacionJ1;

    @FXML
    private Label puntuacionJ2;

    @FXML
    private TextArea ultimoMovimientoJugador1;

    @FXML
    private TextArea ultimoMovimientoJugador2;

    @FXML
    private GridPane grid;

    private final Queue<String> movimientosJugador1 = new LinkedList<>();
    private final Queue<String> movimientosJugador2 = new LinkedList<>();
    private static final int MAX_MOVIMIENTOS = 10;

    private final Settings gameSettings = Main.getGameSettings();
    private final Button[][] botones = new Button[gameSettings.selectedSize][gameSettings.selectedSize];
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
                double tamanioBoton = 500.0 / gridSize;

                //Configurar tamaño de lo botones.
                button.setPrefSize(tamanioBoton, tamanioBoton);
                button.setMinSize(tamanioBoton, tamanioBoton);
                button.setMaxSize(tamanioBoton, tamanioBoton);

                //Tamaño de fuente dinámica.
                double tamanioFuente = tamanioBoton / 3;
                button.setStyle("-fx-font-size: " + tamanioFuente + "px;");

                final int x = i, y = j;
                button.setOnAction(e -> Jugada(x, y));
                botones[i][j] = button;
                grid.add(button, j, i);
            }
        }


        //Escribir el nombre de los jugadores.
        String titulo1, titulo2;

        //Revisando si jugador es CPU.
        if(jugadores[0].esCPU()) {
            titulo1 = "(CPU) " + jugadores[0].getNombre() + " (" + jugadores[0].getLetra() + ")";
        } else {titulo1 = jugadores[0].getNombre() + " (" + jugadores[0].getLetra() + ")";}

        if(jugadores[1].esCPU()) {
            titulo2 = "(CPU) " + jugadores[1].getNombre() + " (" + jugadores[1].getLetra() + ")";
        } else {titulo2 = jugadores[1].getNombre() + " (" + jugadores[1].getLetra() + ")";}

        jugadores[0].setTitulo(titulo1); tituloJugador1.setText(titulo1);
        jugadores[1].setTitulo(titulo2); tituloJugador2.setText(titulo2);
    }

    ///Manejo de jugada.
    private void Jugada(int fila, int columna){
        jugadorActual = cambioJugador();
        consolaJuegoLog("Turno de ", jugadorActual.getTitulo());
        Console.log("Click en celda ", String.valueOf(fila),",", String.valueOf(columna));

        //Cambiando la letra del boton y deshabilitandolo.
        botones[fila][columna].setText(jugadorActual.getLetra());
        botones[fila][columna].setDisable(true);
        Console.log("Cuenta de LOLs: ",String.valueOf(checkLOL(fila,columna)));

        //Añadiendo puntos y actualizando tablas.
        jugadorActual.incrementarPuntuacion(checkLOL(fila,columna));
        puntuacionJ1.setText("Puntuación: " + String.valueOf(jugadores[0].getPuntuacion()));
        puntuacionJ2.setText("Puntuación: " + String.valueOf(jugadores[1].getPuntuacion()));
        //Actualizando ultimos movimientos.
        aniadirMovimiento(jugadorActual.getNombre(), " colocó " + jugadorActual.getLetra() + " en (" + fila + "," + columna + ")\n");
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

    //Chequeos de LOL
    /// Revisa si la letra en X,Y forma parte de un LOL.
    private int checkLOL(int fila, int columna) {
        return (  horizontalLOL(fila, columna)
                + verticalLOL(fila, columna)
                + diagonalLOL1(fila,columna)
                + diagonalLOL2(fila,columna)
        );
    }

    ///Chequear si la letra forma un LOL hoorizontal.
    private int horizontalLOL(int fila, int columna){
        int lolCount = 0;
        if (columna >= 2) {
            if ((letraBoton(fila, columna - 2) + letraBoton(fila, columna - 1) + letraBoton(fila, columna)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila][columna - 2].setStyle("-fx-background-color: green;");
                botones[fila][columna - 1].setStyle("-fx-background-color: green;");
                botones[fila][columna].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }
        if (columna >= 1 && columna <= botones[0].length - 2) {
            if ((letraBoton(fila, columna - 1) + letraBoton(fila, columna) + letraBoton(fila, columna + 1)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila][columna - 1].setStyle("-fx-background-color: green;");
                botones[fila][columna].setStyle("-fx-background-color: green;");
                botones[fila][columna + 1].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }
        if (columna <= botones[0].length - 3) {
            if ((letraBoton(fila, columna) + letraBoton(fila, columna + 1) + letraBoton(fila, columna + 2)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila][columna].setStyle("-fx-background-color: green;");
                botones[fila][columna + 1].setStyle("-fx-background-color: green;");
                botones[fila][columna + 2].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }

        return lolCount;
    }

    ///Chequear si la letra forma un LOL vertical.
    private int verticalLOL(int fila, int columna) {
        int lolCount = 0;
        if (fila >= 2) {
            if ((letraBoton(fila - 2, columna) + letraBoton(fila - 1, columna) + letraBoton(fila, columna)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila - 2][columna].setStyle("-fx-background-color: green;");
                botones[fila - 1][columna].setStyle("-fx-background-color: green;");
                botones[fila][columna].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }
        if (fila >= 1 && fila <= botones.length - 2) {
            if ((letraBoton(fila - 1, columna) + letraBoton(fila, columna) + letraBoton(fila + 1, columna)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila - 1][columna].setStyle("-fx-background-color: green;");
                botones[fila][columna].setStyle("-fx-background-color: green;");
                botones[fila + 1][columna].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }
        if (fila <= botones.length - 3) {
            if ((letraBoton(fila, columna) + letraBoton(fila + 1, columna) + letraBoton(fila + 2, columna)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila][columna].setStyle("-fx-background-color: green;");
                botones[fila + 1][columna].setStyle("-fx-background-color: green;");
                botones[fila + 2][columna].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }

        return lolCount;
    }

    ///Chequear si la letra forma un LOL diagonal de Izquierda a Derecha.
    private int diagonalLOL1(int fila, int columna) {
        int lolCount = 0;
        if (fila >= 2 && columna >= 2) {
            if ((letraBoton(fila - 2, columna - 2) + letraBoton(fila - 1, columna - 1) + letraBoton(fila, columna)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila - 2][columna - 2].setStyle("-fx-background-color: green;");
                botones[fila - 1][columna - 1].setStyle("-fx-background-color: green;");
                botones[fila][columna].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }
        if (fila >= 1 && columna >= 1 && fila <= botones.length - 2 && columna <= botones[0].length - 2) {
            if ((letraBoton(fila - 1, columna - 1) + letraBoton(fila, columna) + letraBoton(fila + 1, columna + 1)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila - 1][columna - 1].setStyle("-fx-background-color: green;");
                botones[fila][columna].setStyle("-fx-background-color: green;");
                botones[fila + 1][columna + 1].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }
        if (fila <= botones.length - 3 && columna <= botones[0].length - 3) {
            if ((letraBoton(fila, columna) + letraBoton(fila + 1, columna + 1) + letraBoton(fila + 2, columna + 2)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila][columna].setStyle("-fx-background-color: green;");
                botones[fila + 1][columna + 1].setStyle("-fx-background-color: green;");
                botones[fila + 2][columna + 2].setStyle("-fx-background-color: green;");

                lolCount++;
            }
        }

        return lolCount;
    }

    ///Chequear si la letra forma un LOL diagonal de Derecha a Izquierda.
    private int diagonalLOL2(int fila, int columna) {
        int lolCount = 0;
        if (fila >= 2 && columna <= botones[0].length - 3) {
            if ((letraBoton(fila - 2, columna + 2) + letraBoton(fila - 1, columna + 1) + letraBoton(fila, columna)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila - 2][columna + 2].setStyle("-fx-background-color: green;");
                botones[fila - 1][columna + 1].setStyle("-fx-background-color: green;");
                botones[fila][columna].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }
        if (fila >= 1 && columna >= 1 && fila <= botones.length - 2 && columna <= botones[0].length - 2) {
            if ((letraBoton(fila - 1, columna + 1) + letraBoton(fila, columna) + letraBoton(fila + 1, columna - 1)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila - 1][columna + 1].setStyle("-fx-background-color: green;");
                botones[fila][columna].setStyle("-fx-background-color: green;");
                botones[fila + 1][columna - 1].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }
        if (fila <= botones.length - 3 && columna >= 2) {
            if ((letraBoton(fila, columna) + letraBoton(fila + 1, columna - 1) + letraBoton(fila + 2, columna - 2)).equals("LOL")) {
                //Cambia el color de los botones al formar LOL
                botones[fila][columna].setStyle("-fx-background-color: green;");
                botones[fila + 1][columna - 1].setStyle("-fx-background-color: green;");
                botones[fila + 2][columna - 2].setStyle("-fx-background-color: green;");
                lolCount++;
            }
        }

        return lolCount;
    }

    ///Retorna la letra de un botón del grid.
    private String letraBoton(int fila, int columna) {
        return botones[fila][columna].getText();
    }

    ///Retorna true si el botón está vacío,
    private boolean botonVacio(int fila, int columna) {
        return letraBoton(fila,columna).equals("");
    }

    ///Retorna una lista con todas las coordenadas de los botones que están vacíos.
    private List<int[]> botonesVacios() {
        List<int[]> coordenadasVacias = new ArrayList<>();

        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                if (botonVacio(i, j)) {
                    coordenadasVacias.add(new int[] {i, j});
                }
            }
        }

        return coordenadasVacias;
    }


    ///Añade los ultimos movimientos de los jugadores en los cuadros de texto.
    public void aniadirMovimiento(String jugador, String movimiento) {
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