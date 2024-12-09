package com.ingmonika.tgol.clases;

import com.ingmonika.tgol.controladores.ControladorJuego;

///Clase jugador, incluye el nombre, puntuación, letra de juego, y si es un CPU.
public class Jugador {
    private ControladorJuego controlador;
    private String nombre;
    private int puntuacion;
    private String letra;
    private boolean esCPU;
    private int numero;
    private String titulo;
    private String color = "#000000";

    //Constructor
    public Jugador(String nombre, String letra, boolean esCPU, int numero) {
        this.nombre = nombre;
        this.puntuacion = 0; // Inicialmente la puntuación es 0
        this.letra = letra;
        this.esCPU = esCPU;
        this.numero = numero;
        this.titulo = "";
    }

    //Getters y Setters
    public ControladorJuego getControlador() {
        return controlador;
    }

    public void setControlador(ControladorJuego controlador) {
        this.controlador = controlador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getLetra() {
        return String.valueOf(letra);
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public boolean esCPU() {
        return esCPU;
    }

    public void setEsCPU(boolean esCPU) {
        this.esCPU = esCPU;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //Método para incrementar la puntuación
    public void incrementarPuntuacion(int puntos) {
        this.puntuacion += puntos;
    }

    //Metodo de jugada de jugador real.
    public void realizarMovimiento(int fila, int columna){
        controlador.Jugada(fila, columna);
    }
}
