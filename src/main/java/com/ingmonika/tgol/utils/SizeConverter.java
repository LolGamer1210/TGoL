package com.ingmonika.tgol.utils;

///Convertidor de int en Strings para la lista de tamaños del grid.
public class SizeConverter {


    ///Convierte una String NxN en un int N y lo retorna.
    /// @param size String NxN de la cual retornar el primer entero.
    public static int stringToInt(String size) {
        // Divide la cadena en partes usando 'x' como delimitador y retorna el primer número
        String[] parts = size.split("x");
        return Integer.parseInt(parts[0]);
    }

    ///Convierte un int N en una String NxN y la retorna.
    /// @param size int N para crear la String NxN.
    public static String intToString(int size) {
        // Retorna una cadena en el formato "NxN" usando el número entero
        return size + "x" + size;
    }
}
