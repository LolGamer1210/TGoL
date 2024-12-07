package com.ingmonika.tgol;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class test extends Application {
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Button drawHeadButton = new Button("Draw Head");
        drawHeadButton.setOnAction(event -> drawHead(gc));

        Button drawBodyButton = new Button("Draw Body");
        drawBodyButton.setOnAction(event -> drawBody(gc));

        Button drawEyesButton = new Button("Draw Eyes");
        drawEyesButton.setOnAction(event -> drawEyes(gc));

        Button drawAntennasButton = new Button("Draw Antennas");
        drawAntennasButton.setOnAction(event -> drawAntennas(gc));

        Button drawArmsButton = new Button("Draw Arms");
        drawArmsButton.setOnAction(event -> drawArms(gc));

        Button drawLegsButton = new Button("Draw Legs");
        drawLegsButton.setOnAction(event -> drawLegs(gc));

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(drawHeadButton, drawBodyButton, drawEyesButton, drawAntennasButton, drawArmsButton, drawLegsButton, canvas);

        // Create ChoiceBox with text items
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Option 1", "Option 2", "Option 3");

        VBox leftVBox = new VBox(10);
        leftVBox.getChildren().add(choiceBox);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vbox);
        borderPane.setLeft(leftVBox);

        Scene scene = new Scene(borderPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Draw Android Logo");
        primaryStage.show();
    }

    private void drawHead(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillOval(160, 50, 80, 80);
    }

    private void drawBody(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(150, 100, 100, 150);
    }

    private void drawEyes(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(175, 80, 10, 10);
        gc.fillOval(215, 80, 10, 10);
    }

    private void drawAntennas(GraphicsContext gc) {
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(5);
        gc.strokeLine(170, 50, 140, 20);
        gc.strokeLine(230, 50, 260, 20);
    }

    private void drawArms(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(120, 110, 30, 20);
        gc.fillRect(250, 110, 30, 20);
    }

    private void drawLegs(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(160, 250, 20, 50);
        gc.fillRect(220, 250, 20, 50);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
