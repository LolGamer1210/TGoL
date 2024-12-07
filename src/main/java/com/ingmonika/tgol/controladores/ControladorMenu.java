package com.ingmonika.tgol.controladores;

import com.ingmonika.Console;
import com.ingmonika.tgol.Main;
import com.ingmonika.tgol.implementaciones.Controlador;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class ControladorMenu implements Controlador {

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    @FXML
    private CheckBox player2Type;

    @FXML
    private Button playButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button aboutButton;

    @FXML
    private Text version;

    @FXML
    private ChoiceBox gridSize;

    private HostServices hostServices;
    private ObservableList sizes = FXCollections.observableArrayList("5x5", "10x10","15x15","20x20");

    @FXML
    private void initialize() {
        gridSize.setItems(sizes);
        playButton.setOnAction(event -> {
            printButtonName(playButton);
            Console.log(Console.LogType.DEBUG, player1Name.getCharacters().toString());
            Console.log(Console.LogType.DEBUG, player2Name.getCharacters().toString());
            Main.loadScene("Juego.fxml");
        });
        optionsButton.setOnAction(event -> printButtonName(optionsButton));
        aboutButton.setOnAction(event -> printButtonName(aboutButton));
        player2Type.selectedProperty().addListener((observable, oldValue, newValue) -> {
            player2Name.setDisable(newValue);
            if(newValue){
                Console.log(Console.LogType.DEBUG, "Player 2 set to CPU");
            } else {Console.log(Console.LogType.DEBUG, "Player 2 set to player");};
        });
        version.setOnMouseClicked(event -> openURL("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
    }

    private void printButtonName(Button button) {
        Console.log(Console.LogType.DEBUG, button.getText());
    }

    private void openURL(String url) {
        hostServices.showDocument(url);
    }

    @Override
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }
}
