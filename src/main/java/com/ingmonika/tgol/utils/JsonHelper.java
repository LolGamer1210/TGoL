package com.ingmonika.tgol.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ingmonika.tgol.clases.Settings;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonHelper {
    private static final String SETTINGS_FILE = "settings.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /// Guarda un objeto Settings en un archivo JSON.
    public static void guardarSettings(Settings settings) {
        try (FileWriter writer = new FileWriter(SETTINGS_FILE)) {
            gson.toJson(settings, writer);
            System.out.println("Settings guardados en " + SETTINGS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /// Lee un objeto Settings desde un archivo JSON.
    public static Settings leerSettings() {
        try (FileReader reader = new FileReader(SETTINGS_FILE)) {
            return gson.fromJson(reader, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
