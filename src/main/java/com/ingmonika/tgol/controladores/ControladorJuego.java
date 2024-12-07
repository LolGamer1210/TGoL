package com.ingmonika.tgol.controladores;

import com.ingmonika.Console;
import com.ingmonika.tgol.Main;
import com.ingmonika.tgol.implementaciones.Controlador;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ControladorJuego implements Controlador {

    @FXML
    private Button back;

    @FXML
    private Text version;

    private HostServices hostServices;

    @FXML
    private Label cell00, cell01, cell02, cell03, cell04;

    @FXML
    private void initialize() {
        back.setOnAction(event -> Main.loadScene("Menu.fxml"));
        version.setOnMouseClicked(event -> openURL("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));

        // Add event handlers for cell clicks
        cell00.setOnMouseClicked(this::handleCellClick);
        cell01.setOnMouseClicked(this::handleCellClick);
        cell02.setOnMouseClicked(this::handleCellClick);
        cell03.setOnMouseClicked(this::handleCellClick);
        cell04.setOnMouseClicked(this::handleCellClick);
    }

    private void handleCellClick(MouseEvent event) {
        Label clickedCell = (Label) event.getSource();
        String id = clickedCell.getId();
        String[] coords = id.substring(4).split("");
        Console.log(Console.LogType.DEBUG, "Cell clicked: (" + coords[0] + ", " + coords[1] + ")");
    }

    private void printButtonName(Button button) {
        Console.log(Console.LogType.DEBUG, button.getText());
    }

    private void openURL(String url) {
        hostServices.showDocument(url);
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }
}
