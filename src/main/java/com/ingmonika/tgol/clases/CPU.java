package com.ingmonika.tgol.clases;

import com.ingmonika.tgol.controladores.ControladorJuego;
import javafx.scene.control.Button;

import java.util.List;
import java.util.Random;

public class CPU extends Jugador {
    private final ControladorJuego controlador;
    private static Button[][] botones;
    private Random random;

    public CPU(Jugador jugador) {
        super(jugador.getNombre(), jugador.getLetra(), jugador.esCPU(), jugador.getNumero());
        controlador = jugador.getControlador();
        botones = controlador.getBotones();
        random = new Random();
    }

    @Override
    public void realizarMovimiento(int x, int y) {
        if(controlador.gameSettings.isCPUDificil()){
            realizarMovimientoDificil();
        } else {
            realizarMovimientoFacil();
        }
    }

    public void realizarMovimientoFacil() {
        //Cuando el movimiento es de CPU se ignoran las coordenadas.
        //Obtener la lista de botones vacíos del controlador
        List<int[]> botonesVacios = controlador.botonesVacios();

        //Si hay botones vacíos, seleccionar uno al azar y realizar el movimiento
        if (!botonesVacios.isEmpty()) {
            int[] coordenadas = botonesVacios.get(random.nextInt(botonesVacios.size()));
            int fila = coordenadas[0];
            int columna = coordenadas[1];

            //Realizar el movimiento en las coordenadas seleccionadas
            controlador.Jugada(fila, columna);
        }
    }

    public void realizarMovimientoDificil() {
        // Cuando el movimiento es de CPU se ignoran las coordenadas.
        // Obtener la lista de botones vacíos del controlador
        List<int[]> botonesVacios = controlador.botonesVacios();

        // Si hay botones vacíos, seleccionar el mejor movimiento
        if (!botonesVacios.isEmpty()) {
            int[] mejorMovimiento = obtenerMejorMovimiento(botonesVacios);
            if (mejorMovimiento != null) {
                int fila = mejorMovimiento[0];
                int columna = mejorMovimiento[1];

                // Realizar el movimiento en las coordenadas seleccionadas
                controlador.Jugada(fila, columna);
            }
        }
    }

    /// Método para obtener el mejor movimiento posible
    private int[] obtenerMejorMovimiento(List<int[]> botonesVacios) {
        int[] mejorMovimiento = null;
        int mejorPuntuacion = Integer.MIN_VALUE;

        for (int[] coordenadas : botonesVacios) {
            int fila = coordenadas[0];
            int columna = coordenadas[1];
            int puntuacion = evaluarMovimiento(fila, columna);

            if (puntuacion > mejorPuntuacion) {
                mejorPuntuacion = puntuacion;
                mejorMovimiento = coordenadas;
            }
        }

        return mejorMovimiento;
    }

    /// Método para evaluar un movimiento y determinar su puntuación
    private int evaluarMovimiento(int fila, int columna) {
        // Lógica para evaluar el movimiento
        int puntuacion = 0;

        // Simular movimiento de CPU y evaluar líneas horizontales, verticales y diagonales
        controlador.getBotones()[fila][columna].setText(String.valueOf(this.getLetra()));

        // Evaluar horizontal
        puntuacion += checkLOL(fila, columna,this.getLetra());

        // Restaurar el estado del botón
        controlador.getBotones()[fila][columna].setText("");

        return puntuacion;
    }

    /// Revisa si la letra en X,Y forma parte de un LOL sin alterar los botones.
    public static int checkLOL(int fila, int columna, String letra) {
        return (horizontalLOL(fila, columna, letra)
                + verticalLOL(fila, columna, letra)
                + diagonalLOL1(fila, columna, letra)
                + diagonalLOL2(fila, columna, letra));
    }

    /// Chequear si la letra forma un LOL horizontal sin alterar los botones.
    private static int horizontalLOL(int fila, int columna, String letra) {
        int lolCount = 0;
        if (columna >= 2) {
            if ((letraBoton(fila, columna - 2) + letraBoton(fila, columna - 1) + letra).equals("LOL")) {
                lolCount++;
            }
        }
        if (columna >= 1 && columna <= botones[0].length - 2) {
            if ((letraBoton(fila, columna - 1) + letra + letraBoton(fila, columna + 1)).equals("LOL")) {
                lolCount++;
            }
        }
        if (columna <= botones[0].length - 3) {
            if ((letra + letraBoton(fila, columna + 1) + letraBoton(fila, columna + 2)).equals("LOL")) {
                lolCount++;
            }
        }
        return lolCount;
    }

    /// Chequear si la letra forma un LOL vertical sin alterar los botones.
    private static int verticalLOL(int fila, int columna, String letra) {
        int lolCount = 0;
        if (fila >= 2) {
            if ((letraBoton(fila - 2, columna) + letraBoton(fila - 1, columna) + letra).equals("LOL")) {
                lolCount++;
            }
        }
        if (fila >= 1 && fila <= botones.length - 2) {
            if ((letraBoton(fila - 1, columna) + letra + letraBoton(fila + 1, columna)).equals("LOL")) {
                lolCount++;
            }
        }
        if (fila <= botones.length - 3) {
            if ((letra + letraBoton(fila + 1, columna) + letraBoton(fila + 2, columna)).equals("LOL")) {
                lolCount++;
            }
        }
        return lolCount;
    }

    /// Chequear si la letra forma un LOL diagonal de Izquierda a Derecha sin alterar los botones.
    private static int diagonalLOL1(int fila, int columna, String letra) {
        int lolCount = 0;
        if (fila >= 2 && columna >= 2) {
            if ((letraBoton(fila - 2, columna - 2) + letraBoton(fila - 1, columna - 1) + letra).equals("LOL")) {
                lolCount++;
            }
        }
        if (fila >= 1 && columna >= 1 && fila <= botones.length - 2 && columna <= botones[0].length - 2) {
            if ((letraBoton(fila - 1, columna - 1) + letra + letraBoton(fila + 1, columna + 1)).equals("LOL")) {
                lolCount++;
            }
        }
        if (fila <= botones.length - 3 && columna <= botones[0].length - 3) {
            if ((letra + letraBoton(fila + 1, columna + 1) + letraBoton(fila + 2, columna + 2)).equals("LOL")) {
                lolCount++;
            }
        }
        return lolCount;
    }

    /// Chequear si la letra forma un LOL diagonal de Derecha a Izquierda sin alterar los botones.
    private static int diagonalLOL2(int fila, int columna, String letra) {
        int lolCount = 0;
        if (fila >= 2 && columna <= botones[0].length - 3) {
            if ((letraBoton(fila - 2, columna + 2) + letraBoton(fila - 1, columna + 1) + letra).equals("LOL")) {
                lolCount++;
            }
        }
        if (fila >= 1 && columna >= 1 && fila <= botones.length - 2 && columna <= botones[0].length - 2) {
            if ((letraBoton(fila - 1, columna + 1) + letra + letraBoton(fila + 1, columna - 1)).equals("LOL")) {
                lolCount++;
            }
        }
        if (fila <= botones.length - 3 && columna >= 2) {
            if ((letra + letraBoton(fila + 1, columna - 1) + letraBoton(fila + 2, columna - 2)).equals("LOL")) {
                lolCount++;
            }
        }
        return lolCount;
    }

    /// Obtiene la letra en un botón especificado.
    private static String letraBoton(int fila, int columna) {
        return botones[fila][columna].getText();
    }
}
