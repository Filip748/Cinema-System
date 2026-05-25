package com.example.frontend.EmployeeDashboard;

import com.example.frontend.creator.CinemaHallApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;


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

    @FXML
    public void logout(){
        contentArea.getChildren().clear();
        Label logoutLabel = new Label("logged out");
        StackPane.setAlignment(logoutLabel, javafx.geometry.Pos.CENTER);
        contentArea.getChildren().add(logoutLabel);
    }

    public StackPane getContentArea() {
        return contentArea;
    }
    
}
