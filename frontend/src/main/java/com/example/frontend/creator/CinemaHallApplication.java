package com.example.frontend.creator;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import com.example.frontend.creator.dto.SeatRequest;

// 1. Zmieniamy dziedziczenie z Application na VBox
public class CinemaHallApplication extends VBox {

    private final CinemaHallService hallService = new CinemaHallService();
    private Label statusLabel;
    private final ToggleButton[][] seatButtons = new ToggleButton[15][15];

    // 2. Metoda start() zamienia się w konstruktor klasy
    public CinemaHallApplication() {
        // Konfigurujemy samych siebie (ponieważ ta klasa to teraz VBox)
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        // Dodałem białe tło i lekkie zaokrąglenie, żeby wyglądało jak ładna karta na szarym Dashboardzie
        this.setStyle("-fx-font-family: 'Segoe UI', sans-serif; -fx-background-color: white; -fx-background-radius: 8px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

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

        // Dodajemy wszystkie elementy do NASZEGO VBoxa
        this.getChildren().addAll(statusLabel, new Separator(), hallBox, new Separator(), gridBox, dangerZone);
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