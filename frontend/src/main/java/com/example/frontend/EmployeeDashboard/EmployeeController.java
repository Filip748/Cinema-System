package com.example.frontend.EmployeeDashboard;

import com.example.frontend.creator.CinemaHallApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;


public class EmployeeController {
    @FXML
    private StackPane contentArea;
    
    public EmployeeController() {}
    
    @FXML
    public void initialize() {
        showSchedule();
    }
    
    @FXML
    public void showSchedule(){
        try {
            contentArea.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/schedule/schedule-board-view.fxml"));
            Node scheduleView = loader.load();
            if(scheduleView instanceof Region){
                ((Region) scheduleView).setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            }
            StackPane.setAlignment(scheduleView, javafx.geometry.Pos.CENTER);
            contentArea.getChildren().add(scheduleView);

        } catch (IOException e) {
            Label errorLabel = new Label("Error, cannot load schedule");
            contentArea.getChildren().add(errorLabel);
            e.printStackTrace();

        }
    }
    //guzik od przejscia do sprzedazy
    @FXML
    public void showTicketSales() {
        try {
            contentArea.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/tickets/ticket-sales-view.fxml"));
            Node salesView = loader.load();
            StackPane.setAlignment(salesView, javafx.geometry.Pos.CENTER);
            contentArea.getChildren().add(salesView);

        } catch (IOException e) {
            Label errorLabel = new Label("Error, cannot load ticket sales");
            contentArea.getChildren().add(errorLabel);
            e.printStackTrace();
        }
    }


    @FXML
    public void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/login/login-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) contentArea.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.setFullScreen(false);
            stage.centerOnScreen();

        } catch (IOException e) {
            showPlaceholder("Error");
        }
    }

    private void showPlaceholder(String text) {
        contentArea.getChildren().clear();
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");
        contentArea.getChildren().add(label);
    }

    public StackPane getContentArea() {
        return contentArea;
    }
    
}
