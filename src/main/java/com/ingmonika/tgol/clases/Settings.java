package com.ingmonika.tgol.clases;

public class Settings {
    public int selectedSize;

    ///Objeto que contiene los ajustes del juego.
    /// @param gridSize especifica el tamaño del grid.
    public Settings(int gridSize) {
        this.selectedSize = gridSize;
    }

    ///Retorna el tamaño del grid desde el objeto de settings.
    public int getSelectedSize() {
        return selectedSize;
    }

    ///Define el tamaño del grid en el objeto de settings.
    public void setSelectedSize(int selectedSize) {
        this.selectedSize = selectedSize;
    }
}
