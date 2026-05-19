package com.example.frontend.adminDashboard;

import com.example.frontend.creator.CinemaHallApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private StackPane contentArea;

    public AdminDashboardController() {}

    @FXML
    public void initialize() {
        showSchedule();
    }

    @FXML
    public void showSchedule() {
        loadView("/com/example/frontend/schedule/schedule-board-view.fxml");
    }

    @FXML
    public void openAddMovie() {
        loadView("/com/example/frontend/schedule/add-movie-view.fxml");
    }

    @FXML
    public void openAddScreening() {
        loadView("/com/example/frontend/schedule/schedule-view.fxml");
    }

    @FXML
    public void openAddHall() {
        contentArea.getChildren().clear();

        Node view = new CinemaHallApplication();

        if (view instanceof javafx.scene.layout.Region) {
            ((javafx.scene.layout.Region) view).setMaxSize(
                    javafx.scene.layout.Region.USE_PREF_SIZE,
                    javafx.scene.layout.Region.USE_PREF_SIZE
            );
        }

        javafx.scene.layout.StackPane.setAlignment(view, javafx.geometry.Pos.CENTER);
        contentArea.getChildren().add(view);
    }

    @FXML
    public void showStatistics() {
        loadView("/com/example/frontend/stats/statistics-view.fxml");
    }

    @FXML
    public void logout() {
        showPlaceholder("Log out");
    }

    private void loadView(String fxmlPath) {
        try {
            javafx.scene.Node view = FXMLLoader.load(getClass().getResource(fxmlPath));

            contentArea.getChildren().clear();

            if (view instanceof javafx.scene.layout.Region) {
                ((javafx.scene.layout.Region) view).setMaxSize(
                        javafx.scene.layout.Region.USE_PREF_SIZE,
                        javafx.scene.layout.Region.USE_PREF_SIZE
                );
            }

            javafx.scene.layout.StackPane.setAlignment(view, javafx.geometry.Pos.CENTER);

            contentArea.getChildren().add(view);

        } catch (IOException e) {
            showPlaceholder("ERROR");
            e.printStackTrace();
        } catch (NullPointerException e) {
            showPlaceholder("ERROR" + fxmlPath);
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