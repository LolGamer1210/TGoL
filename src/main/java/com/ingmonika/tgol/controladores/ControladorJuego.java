package com.ingmonika.tgol.controladores;

import com.ingmonika.Console;
import com.ingmonika.tgol.Main;
import com.ingmonika.tgol.clases.CPU;
import com.ingmonika.tgol.clases.Jugador;
import com.ingmonika.tgol.utils.URLHelper;
import com.ingmonika.tgol.clases.Settings;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.*;

public class ControladorJuego {

    @FXML
    public Label consolaJuego;

    @FXML
    private Button atras;

    @FXML
    private Text version;

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
    private ImageView indicadorTurnoJugador1;

    @FXML
    private ImageView indicadorTurnoJugador2;

    @FXML
    private GridPane grid;

    private final Queue<String> movimientosJugador1 = new LinkedList<>();
    private final Queue<String> movimientosJugador2 = new LinkedList<>();
    private static final int MAX_MOVIMIENTOS = 10;

    //Importando ajustes.
    public Settings gameSettings;
    //Creando array de botones.
    private Button[][] botones;
    //Creando jugadores.
    private Jugador[] jugadores;
    private Jugador jugadorActual;

    //Movimiento final para cambio de color.
    private int[] ultimaJugada = new int[]{0,0};
    private int ultimoLOL = 0;

    @FXML
    private void initialize() {
        //Cambiando versión.
        version.setText(Main.getBuildVersion());

        //Importando ajustes y creando array de botones.
        gameSettings = Main.getGameSettings();
        botones = new Button[gameSettings.tamanioSeleccionado][gameSettings.tamanioSeleccionado];

        Console.log(String.valueOf(gameSettings.getColorJ1()));
        Console.log(String.valueOf(gameSettings.getColorJ2()));
        Console.log(String.valueOf(gameSettings.getColorLOL()));
        Console.log(String.valueOf(gameSettings.getColorUltima()));

        //Importando jugadores mas recientes.
        jugadores = Main.getJugadores();
        jugadorActual = jugadores[0];

        //Botón para regresar al menú principal.
        atras.setOnAction(_ -> Main.loadScene("Menu.fxml"));

        //Versión
        version.setOnMouseClicked(_ -> URLHelper.openURL("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));

        //Crear grid según el tamaño seleccionado en el menú
        int gridSize = gameSettings.tamanioSeleccionado;
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
                double tamanioFuente = tamanioBoton / 4;
                button.setStyle("-fx-font-size: " + tamanioFuente + "px;");

                final int x = i, y = j;
                button.setOnAction(_ -> jugadorActual.realizarMovimiento(x, y));
                botones[i][j] = button;
                grid.add(button, j, i);
            }
        }

        //Pasandole el controlador al jugador.
        jugadores[0].setControlador(this);
        jugadores[1].setControlador(this);

        //Escribir el nombre de los jugadores.
        String titulo1, titulo2;

        //Revisando si jugador es CPU. Si lo es, se inicializa el CPU.
        if(jugadores[0].esCPU()) {
            Console.log("Jugador 1 marcado como CPU");
            titulo1 = "(CPU) " + jugadores[0].getNombre() + " (" + jugadores[0].getLetra() + ")";
            jugadores[0] = new CPU(jugadores[0]);
        } else {titulo1 = jugadores[0].getNombre() + " (" + jugadores[0].getLetra() + ")";}

        if(jugadores[1].esCPU()) {
            Console.log("Jugador 2 marcado como CPU");
            titulo2 = "(CPU) " + jugadores[1].getNombre() + " (" + jugadores[1].getLetra() + ")";
            jugadores[1] = new CPU(jugadores[1]);
        } else {titulo2 = jugadores[1].getNombre() + " (" + jugadores[1].getLetra() + ")";}

        jugadores[0].setTitulo(titulo1); tituloJugador1.setText(titulo1);
        jugadores[1].setTitulo(titulo2); tituloJugador2.setText(titulo2);

        //Cambiando los colores de los titulos. Añadiendolos a la clase jugador
        tituloJugador1.setStyle("-fx-text-fill: " + gameSettings.getColorJ1()); jugadores[0].setColor(gameSettings.getColorJ1());
        tituloJugador2.setStyle("-fx-text-fill: " + gameSettings.getColorJ2()); jugadores[1].setColor(gameSettings.getColorJ2());

        //Iniciando juego
        juego();
    }

    //Flujo de juego
    private void juego(){
        //Chequeo de celdas vacías.
        // Verificar si quedan celdas vacías.
        if (botonesVacios().isEmpty()) {
            terminarJuego();
            return;
        }

        if(jugadorActual.esCPU()){
            jugadorActual.realizarMovimiento(0,0); //Coordenadas se ignoran.
        }
    }

    ///Manejo de jugada.
    public void Jugada(int fila, int columna){
        Console.log("Click en celda ", String.valueOf(fila),",", String.valueOf(columna));

        //Cambia el color de la ultima jugada al normal.
        if(ultimoLOL == 0) {
            cambiarColorBoton(botones[ultimaJugada[0]][ultimaJugada[1]], "linear-gradient(#e4e4e4, #d6d6d6)");
        }

        //Cambiando la letra del boton, el color de la letra, lo deshabilita y lo pone color amarillo.
        Button boton = botones[fila][columna];
        boton.setText(jugadorActual.getLetra());
        boton.setDisable(true);
        boton.setStyle("-fx-opacity: 1;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-border-color: gray; \n" +
                "    -fx-text-fill: " + jugadorActual.getColor() + ";\n "
        );
        cambiarColorBoton(boton, gameSettings.getColorUltima());
        ultimaJugada = new int[]{fila,columna};
        ultimoLOL = checkLOL(fila,columna);
        Console.log("Cuenta de LOLs: ",String.valueOf(ultimoLOL));

        //Añadiendo puntos y actualizando tablas.
        jugadorActual.incrementarPuntuacion(ultimoLOL);
        puntuacionJ1.setText("Puntuación: " + jugadores[0].getPuntuacion());
        puntuacionJ2.setText("Puntuación: " + jugadores[1].getPuntuacion());

        //Actualizando ultimos movimientos.
        aniadirMovimiento( jugadorActual, new int[]{fila,columna}, checkLOL(fila, columna));
        if(ultimoLOL > 0){
            consolaJuegoLog("LOL! ", jugadorActual.getTitulo(), " obtiene otro turno!");
        } else {
            jugadorActual = cambioJugador();
            consolaJuegoLog("Turno de ", jugadorActual.getTitulo());
        }
        juego();
    }

    ///Método para terminar el juego.
    private void terminarJuego() {
        // Determinar el ganador.
        String mensajeFinal;
        if (jugadores[0].getPuntuacion() > jugadores[1].getPuntuacion()) {
            mensajeFinal = "¡" + jugadores[0].getTitulo() + " ha ganado con " + jugadores[0].getPuntuacion() + " puntos!" ;
        } else if (jugadores[1].getPuntuacion() > jugadores[0].getPuntuacion()) {
            mensajeFinal = "¡" + jugadores[1].getTitulo() + " ha ganado con " + jugadores[1].getPuntuacion() + " puntos!";
        } else {
            mensajeFinal = "¡Es un empate! Ambos jugadores tienen " + jugadores[0].getPuntuacion() + " puntos.";
        }

        // Mostrar mensaje de fin en la consola del juego.
        consolaJuegoLog(mensajeFinal);
        Console.log("El juego ha terminado.");

        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Partida de LOL finalizada.");
            alert.setHeaderText("Presione aceptar para volver al Menu Principal.  \n ¡Resultado de la partida!");
            alert.setContentText(mensajeFinal);
            alert.showAndWait();

            Main.loadScene("Menu.fxml");
        });
    }

    ///Consola en al VBox de abajo en el juego
    ///@param mensaje un String o array de String el cual se parseará a la consola dentro del juego.
    private void consolaJuegoLog(String ...mensaje) {
        consolaJuego.setText(String.join(" ",mensaje));
        Console.log("Logged to Game Console: ", String.join(" ",mensaje));
    }

    ///Cambio de jugador.
    private Jugador cambioJugador(){
        if(jugadorActual.getNumero() == 0) {
            indicadorTurnoJugador1.setVisible(false);
            indicadorTurnoJugador2.setVisible(true);
            return jugadores[1];
        } else {
            indicadorTurnoJugador1.setVisible(true);
            indicadorTurnoJugador2.setVisible(false);
            return jugadores[0];
        }
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

    ///Chequear si la letra forma un LOL horizontal.
    private int horizontalLOL(int fila, int columna){
        int lolCount = 0;
        if (columna >= 2) {
            if ((letraBoton(fila, columna - 2) + letraBoton(fila, columna - 1) + letraBoton(fila, columna)).equals("LOL")) {
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila][columna - 2], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna - 1], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                lolCount++;
            }
        }
        if (columna >= 1 && columna <= botones[0].length - 2) {
            if ((letraBoton(fila, columna - 1) + letraBoton(fila, columna) + letraBoton(fila, columna + 1)).equals("LOL")) {
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila][columna - 1], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna + 1], gameSettings.getColorLOL());
                lolCount++;
            }
        }
        if (columna <= botones[0].length - 3) {
            if ((letraBoton(fila, columna) + letraBoton(fila, columna + 1) + letraBoton(fila, columna + 2)).equals("LOL")) {
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna + 1], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna + 2], gameSettings.getColorLOL());
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
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila - 2][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila - 1][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                lolCount++;
            }
        }
        if (fila >= 1 && fila <= botones.length - 2) {
            if ((letraBoton(fila - 1, columna) + letraBoton(fila, columna) + letraBoton(fila + 1, columna)).equals("LOL")) {
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila - 1][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila + 1][columna], gameSettings.getColorLOL());
                lolCount++;
            }
        }
        if (fila <= botones.length - 3) {
            if ((letraBoton(fila, columna) + letraBoton(fila + 1, columna) + letraBoton(fila + 2, columna)).equals("LOL")) {
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila + 1][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila + 2][columna], gameSettings.getColorLOL());
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
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila - 2][columna - 2], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila - 1][columna - 1], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                lolCount++;
            }
        }
        if (fila >= 1 && columna >= 1 && fila <= botones.length - 2 && columna <= botones[0].length - 2) {
            if ((letraBoton(fila - 1, columna - 1) + letraBoton(fila, columna) + letraBoton(fila + 1, columna + 1)).equals("LOL")) {
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila - 1][columna - 1], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila + 1][columna + 1], gameSettings.getColorLOL());
                lolCount++;
            }
        }
        if (fila <= botones.length - 3 && columna <= botones[0].length - 3) {
            if ((letraBoton(fila, columna) + letraBoton(fila + 1, columna + 1) + letraBoton(fila + 2, columna + 2)).equals("LOL")) {
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila + 1][columna + 1], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila + 2][columna + 2], gameSettings.getColorLOL());
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
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila - 2][columna + 2], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila - 1][columna + 1], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                lolCount++;
            }
        }
        if (fila >= 1 && columna >= 1 && fila <= botones.length - 2 && columna <= botones[0].length - 2) {
            if ((letraBoton(fila - 1, columna + 1) + letraBoton(fila, columna) + letraBoton(fila + 1, columna - 1)).equals("LOL")) {
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila - 1][columna + 1], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila + 1][columna - 1], gameSettings.getColorLOL());
                lolCount++;
            }
        }
        if (fila <= botones.length - 3 && columna >= 2) {
            if ((letraBoton(fila, columna) + letraBoton(fila + 1, columna - 1) + letraBoton(fila + 2, columna - 2)).equals("LOL")) {
                // Cambia el color de los botones al formar LOL
                cambiarColorBoton(botones[fila][columna], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila + 1][columna - 1], gameSettings.getColorLOL());
                cambiarColorBoton(botones[fila + 2][columna - 2], gameSettings.getColorLOL());
                lolCount++;
            }
        }

        return lolCount;
    }

    ///Retorna la letra de un botón del grid.
    private String letraBoton(int fila, int columna) {
        return botones[fila][columna].getText();
    }

    ///Cambia el color de un boton.
    private void cambiarColorBoton(Button boton, String color) {
        String estiloActual = boton.getStyle();
        String nuevoEstilo = estiloActual + "-fx-background-color: " + color + ";";
        boton.setStyle(nuevoEstilo);
    }

    ///Retorna true si el botón está vacío,
    private boolean botonVacio(int fila, int columna) {
        return letraBoton(fila, columna).isEmpty();
    }

    ///Retorna una lista con todas las coordenadas de los botones que están vacíos.
    public List<int[]> botonesVacios() {
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

    ///Añade los últimos movimientos de los jugadores en los cuadros de texto.
    public void aniadirMovimiento(Jugador jugador, int[] coordenadas, int cantidadLOLs) {
        String movimiento = jugador.getNombre() + " añadió " + jugador.getLetra() + " en (" + coordenadas[0] + "," + coordenadas[1] + ")";
        if (cantidadLOLs > 0) {
            movimiento += " LOL x " + cantidadLOLs;
        }

        if (jugador.equals(jugadores[0])) {
            if (movimientosJugador1.size() >= MAX_MOVIMIENTOS) {
                movimientosJugador1.poll(); // Eliminar el movimiento más antiguo
            }
            movimientosJugador1.add(movimiento);
            actualizarUltimoMovimiento(ultimoMovimientoJugador1, new LinkedList<>(movimientosJugador1));
        } else if (jugador.equals(jugadores[1])) {
            if (movimientosJugador2.size() >= MAX_MOVIMIENTOS) {
                movimientosJugador2.poll(); // Eliminar el movimiento más antiguo
            }
            movimientosJugador2.add(movimiento);
            actualizarUltimoMovimiento(ultimoMovimientoJugador2, new LinkedList<>(movimientosJugador2));
        } else {
            consolaJuegoLog("Movimiento desconocido...", jugador.getNombre(), movimiento);
        }
    }

    ///Actualiza el cuadro de texto con los movimientos en orden inverso.
    private void actualizarUltimoMovimiento(TextArea textArea, Queue<String> movimientos) {
        StringBuilder sb = new StringBuilder();
        List<String> movimientosList = new ArrayList<>(movimientos);
        Collections.reverse(movimientosList);
        for (String movimiento : movimientosList) {
            sb.append(movimiento).append("\n");
        }
        textArea.setText(sb.toString());
    }

    public Button[][] getBotones() {
        return botones;
    }
}