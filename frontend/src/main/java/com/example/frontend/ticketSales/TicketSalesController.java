package com.example.frontend.ticketSales;

import com.example.frontend.schedule.ScheduleService;
import com.example.frontend.schedule.ScreeningDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class TicketSalesController {

    @FXML
    private TableView<ScreeningDto> screeningTable;

    @FXML
    private GridPane seatGrid;

    @FXML
    private Label selectedMovieLabel;

    @FXML
    private TextField clientEmailField;

    @FXML
    private Button sellTicketBtn;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private ComboBox<Integer> normalTicketComboBox;

    @FXML
    private ComboBox<Integer> studentTicketComboBox;

    @FXML
    private Label seatsWarningLabel;

    private final ScheduleService scheduleService = new ScheduleService();
    private final TicketService ticketService = new TicketService();
    private final double NORMAL_PRICE = 25.00;
    private final double STUDENT_PRICE = 15.00;

    private final List<Long> selectedSeatIds = new ArrayList<>();
    private ScreeningDto selectedScreening = null;

    public TicketSalesController() {}

    @FXML
    public void initialize() {
        setupTable();
        loadScreenings();
        ObservableList<Integer> options = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        normalTicketComboBox.setItems(options);
        normalTicketComboBox.getSelectionModel().select(0);
        normalTicketComboBox.valueProperty().addListener((obs, old, val) -> updateCartStatus());

        studentTicketComboBox.setItems(options);
        studentTicketComboBox.getSelectionModel().select(0);
        studentTicketComboBox.valueProperty().addListener((obs, old, val) -> updateCartStatus());


        screeningTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedScreening = newVal;
                String title = (newVal.getMovie() != null) ? newVal.getMovie().getTitle() : "Unknown";
                selectedMovieLabel.setText("Movie: " + title + "\nHall: " + newVal.getCinemaHall().getName());
                resetCart();
                drawSeats();
            }
        });
    }


    private void setupTable() {
        TableColumn<ScreeningDto, String> titleCol = new TableColumn<>("Movie");
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getMovie() != null ? data.getValue().getMovie().getTitle() : "No title"
        ));
        titleCol.setPrefWidth(200);

        TableColumn<ScreeningDto, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getStartTime() != null ? data.getValue().getStartTime().replace("T", " ") : ""
        ));

        screeningTable.getColumns().addAll(titleCol, timeCol);
    }

    private void loadScreenings() {
        try {
            List<ScreeningDto> screenings = scheduleService.getAllScreenings();
            if (screenings != null) {
                ObservableList<ScreeningDto> data = FXCollections.observableArrayList(screenings);
                screeningTable.setItems(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //metoda do wyswietlania miejsc w konkretnej sali
    private void drawSeats() {
        seatGrid.getChildren().clear();
        selectedSeatIds.clear();
        updateCartStatus();

        if (selectedScreening == null) return;

        try {
            List<SeatDto> seats = ticketService.getAvailableSeats(selectedScreening.getId());
            for (SeatDto seat : seats) {
                ToggleButton seatBtn = new ToggleButton(String.valueOf(seat.getSeat()));
                seatBtn.setPrefSize(40, 40);

                if (!seat.isAvailable()) {
                    seatBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                    seatBtn.setDisable(true);
                } else {
                    seatBtn.setStyle("-fx-background-color: #cccccc; -fx-cursor: hand;");

                    seatBtn.setOnAction(e -> {
                        if (seatBtn.isSelected()) {
                            seatBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
                            selectedSeatIds.add(seat.getId());
                        } else {
                            seatBtn.setStyle("-fx-background-color: #cccccc; -fx-text-fill: black; -fx-font-weight: normal;");
                            selectedSeatIds.remove(seat.getId());
                        }
                        updateCartStatus();
                    });
                }

                seatGrid.add(seatBtn, seat.getSeat() - 1, seat.getRow() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Label errorLabel = new Label("Could not load real seat configuration.");
            seatGrid.add(errorLabel, 0, 0);
        }
    }


    private void updateCartStatus() {
        Integer normal = normalTicketComboBox.getSelectionModel().getSelectedItem();
        Integer student = studentTicketComboBox.getSelectionModel().getSelectedItem();
        int totalTickets = normal + student;

        double total = (normal * NORMAL_PRICE) + (student * STUDENT_PRICE);
        totalPriceLabel.setText(String.format("Total: %.2f PLN", total));

        seatsWarningLabel.setVisible(selectedSeatIds.size() != totalTickets && totalTickets > 0);
    }

    //resetowanie koszyka
    private void resetCart() {
        normalTicketComboBox.getSelectionModel().select(0);
        studentTicketComboBox.getSelectionModel().select(0);
        selectedSeatIds.clear();
        clientEmailField.clear();
        seatsWarningLabel.setVisible(false);
    }

    @FXML
    public void handleSellTickets() {
        Integer normal = normalTicketComboBox.getSelectionModel().getSelectedItem();
        Integer student = studentTicketComboBox.getSelectionModel().getSelectedItem();

        if (normal == null) normal = 0;
        if (student == null) student = 0;

        int totalTickets = normal + student;

        if (selectedScreening == null || totalTickets == 0) {
            showAlert("Error", "No screening or tickets selected!", Alert.AlertType.WARNING);
            return;
        }

        if (selectedSeatIds.size() != totalTickets) {
            showAlert("Error", "number of ticket do not equals number of selected seats!", Alert.AlertType.ERROR);
            return;
        }

        try {
            String email = clientEmailField.getText();
            TicketDto purchaseData = new TicketDto(selectedScreening.getId(), selectedSeatIds, email);
            String result = ticketService.sendTicketPurchase(purchaseData);

            showAlert("Success", "Transaction Complete!\n" + result, Alert.AlertType.INFORMATION);

            resetCart();
            drawSeats();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to connect to server", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        if (screeningTable.getScene() != null && screeningTable.getScene().getWindow() != null) {
            alert.initOwner(screeningTable.getScene().getWindow());
        }

        alert.showAndWait();
    }
}