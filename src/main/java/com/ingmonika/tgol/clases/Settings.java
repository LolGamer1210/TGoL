package com.ingmonika.tgol.clases;

public class Settings {
    public int tamanioSeleccionado = 5;
    public String colorJ1 = "#0000FF";
    public String colorJ2 = "#FF0000";
    public String colorUltima = "#FFFF00";
    public String colorLOL = "#00FF00";
    public String nombreJ1 = "";
    public String nombreJ2 = "";
    public boolean jugadorCPU = false;
    public boolean CPUDificil = false;

    ///Objeto que contiene los ajustes del juego.
    public Settings() {
    }

    ///Define el tamaño del grid en el objeto de settings.
    public void setTamanioSeleccionado(int tamanioSeleccionado) {
        this.tamanioSeleccionado = tamanioSeleccionado;
    }

    public String getColorJ1() {
        return colorJ1;
    }

    public void setColorJ1(String colorJ1) {
        this.colorJ1 = colorJ1;
    }

    public String getColorJ2() {
        return colorJ2;
    }

    public void setColorJ2(String colorJ2) {
        this.colorJ2 = colorJ2;
    }

    public String getColorUltima() {
        return colorUltima;
    }

    public void setColorUltima(String colorUltima) {
        this.colorUltima = colorUltima;
    }

    public String getColorLOL() {
        return colorLOL;
    }

    public void setColorLOL(String colorLOL) {
        this.colorLOL = colorLOL;
    }

    public String getNombreJ1() {
        return nombreJ1;
    }

    public void setNombreJ1(String nombreJ1) {
        this.nombreJ1 = nombreJ1;
    }

    public String getNombreJ2() {
        return nombreJ2;
    }

    public void setNombreJ2(String nombreJ2) {
        this.nombreJ2 = nombreJ2;
    }

    public boolean isJugadorCPU() {
        return jugadorCPU;
    }

    public void setJugadorCPU(boolean jugadorCPU) {
        this.jugadorCPU = jugadorCPU;
    }

    public boolean isCPUDificil() {
        return CPUDificil;
    }

    public void setCPUDificil(boolean CPUDificil) {
        this.CPUDificil = CPUDificil;
    }
}
