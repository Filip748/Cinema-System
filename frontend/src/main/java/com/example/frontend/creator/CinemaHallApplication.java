package com.example.frontend.creator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import com.example.frontend.creator.dto.SeatRequest;

public class CinemaHallApplication extends Application {

    private final CinemaHallService hallService = new CinemaHallService();
    private Label statusLabel;
    private final ToggleButton[][] seatButtons = new ToggleButton[15][15];

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CinemaHall Creator Panel");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-font-family: 'Segoe UI', sans-serif;");

        statusLabel = new Label("Status: Make new CinemaHall");

        Label step1Label = new Label("1. Create new CinemaHall:");
        TextField hallNameField = new TextField("");
        hallNameField.setPromptText("Name of the hall...");

        Button addHallBtn = new Button("+ Add CinemaHall");
        addHallBtn.setOnAction(e -> addHall(hallNameField.getText()));

        VBox hallBox = new VBox(5, step1Label, hallNameField, addHallBtn);

        Label GenerateLabel = new Label("2. Generate seats for the hall:");

        HBox idBox = new HBox(10);
        idBox.setAlignment(Pos.CENTER_LEFT);
        Label idLabel = new Label("CinemaHall ID:");
        TextField hallIdField = new TextField();
        hallIdField.setPrefWidth(50);

        Button generateBtn = new Button("Generate Seats");
        idBox.getChildren().addAll(idLabel, hallIdField, generateBtn);

        GridPane seatGrid = createSeatGrid();
        generateBtn.setOnAction(e -> generateSeats(hallIdField.getText()));
        VBox gridBox = new VBox(10, GenerateLabel, idBox, seatGrid);

        //usuwanie sali
        Label step4Label = new Label("3. Delete CinemaHall:");
        HBox deleteBox = new HBox(10);

        TextField deleteHallIdField = new TextField();
        deleteHallIdField.setPromptText("CinemaHall ID to delete...");
        deleteHallIdField.setPrefWidth(70);

        Button deleteBtn = new Button("🗑️ Delete CinemaHall");
        deleteBtn.setOnAction(e -> deleteHall(deleteHallIdField.getText()));

        deleteBox.getChildren().addAll(deleteHallIdField, deleteBtn);
        VBox dangerZone = new VBox(5, step4Label, deleteBox);



        root.getChildren().addAll(statusLabel, new Separator(), hallBox, new Separator(), gridBox, dangerZone);

        Scene scene = new Scene(root, 650, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createSeatGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(3);
        grid.setVgap(3);
        grid.setPadding(new Insets(10, 0, 10, 0));

        for (int row = 0; row < 15; row++) {
            for (int seat = 0; seat < 15; seat++) {
                ToggleButton selectedSeat = new ToggleButton();
                selectedSeat.setPrefSize(30, 30);
                selectedSeat.setOnAction(e -> {
                    if (selectedSeat.isSelected()) {
                        selectedSeat.setStyle("-fx-background-color: #4CAF50; -fx-border-color: #388E3C; -fx-cursor: hand;");
                    } else {
                        selectedSeat.setStyle("-fx-background-color: #cccccc; -fx-border-color: #999999; -fx-cursor: hand;");
                    }
                });

                seatButtons[row][seat] = selectedSeat;
                grid.add(selectedSeat, seat, row);
            }
        }
        return grid;
    }

    private void addHall(String name) {
        if (name.trim().isEmpty()) {
            updateStatus("There's no name for CinemaHall");
            return;
        }
        String response = hallService.createHall(name);
        updateStatus(response);
    }

    private void generateSeats(String hallId) {
        if (hallId.isEmpty()) {
            updateStatus("You need to type the CinemaHall ID");
            return;
        }

        List<SeatRequest> selectedSeats = new ArrayList<>();
        for (int r = 0; r < 15; r++) {
            for (int s = 0; s < 15; s++) {
                if (seatButtons[r][s].isSelected()) {
                    selectedSeats.add(new SeatRequest(r + 1, s + 1));
                }
            }
        }
        if (selectedSeats.isEmpty()) {
            updateStatus("Hall can not be empty");
            return;
        }
        String response = hallService.generateSeats(hallId, selectedSeats);
        updateStatus(response);
    }

    private void deleteHall(String hallId) {
        if (hallId.trim().isEmpty()) {
            updateStatus("You need to type the CinemaHall ID to delete");
            return;
        }
        String response = hallService.deleteHall(hallId);
        updateStatus(response);
    }

    private void updateStatus(String text) {
        Platform.runLater(() -> {
            statusLabel.setText("Status: " + text);
        });
    }
}