package com.ingmonika.tgol.clases;

///Clase jugador, incluye el nombre, puntuación, letra de juego, y si es un CPU.
public class Jugador {
    private String nombre;
    private int puntuacion;
    private char letra;
    private boolean esCPU;
    private int numero;

    //Constructor
    public Jugador(String nombre, char letra, boolean esCPU, int numero) {
        this.nombre = nombre;
        this.puntuacion = 0; // Inicialmente la puntuación es 0
        this.letra = letra;
        this.esCPU = esCPU;
        this.numero = numero;
    }

    //Getters y Setters
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

    public void setLetra(char letra) {
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

    //Método para incrementar la puntuación
    public void incrementarPuntuacion(int puntos) {
        this.puntuacion += puntos;
    }
}
