package com.example.frontend.schedule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ScheduleBoardController {

    @FXML
    private TableView<ScreeningItem> scheduleTable;

    @FXML
    private TableColumn<ScreeningItem, String> dateColumn;

    @FXML
    private TableColumn<ScreeningItem, String> timeColumn;

    @FXML
    private TableColumn<ScreeningItem, String> movieColumn;

    @FXML
    private TableColumn<ScreeningItem, String> hallColumn;

    private ScheduleService scheduleService = new ScheduleService();

    public ScheduleBoardController() {}

    @FXML
    public void initialize() {
        getDateColumn().setCellValueFactory(new PropertyValueFactory<>("date"));
        getTimeColumn().setCellValueFactory(new PropertyValueFactory<>("time"));
        getMovieColumn().setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        getHallColumn().setCellValueFactory(new PropertyValueFactory<>("hallName"));

        refreshSchedule();
    }

    @FXML
    public void refreshSchedule() {
        ObservableList<ScreeningItem> items = FXCollections.observableArrayList();

        try {
            List<ScreeningDto> screeningsFromDb = getScheduleService().getAllScreenings();

            if (screeningsFromDb != null) {
                for (ScreeningDto screening : screeningsFromDb) {

                    String date = "No data";
                    String time = "No hour";

                    if (screening.getStartTime() != null) {
                        String[] parts = screening.getStartTime().split("T");
                        if (parts.length == 2) {
                            date = parts[0];
                            time = parts[1].substring(0, 5);
                        } else {
                            date = screening.getStartTime();
                        }
                    }

                    String movieTitle = (screening.getMovie() != null && screening.getMovie().getTitle() != null)
                            ? screening.getMovie().getTitle() : "No title";

                    String hallName = (screening.getCinemaHall() != null && screening.getCinemaHall().getName() != null)
                            ? screening.getCinemaHall().getName() : "No hall";

                    items.add(new ScreeningItem(date, time, movieTitle, hallName));
                }
            }

            getScheduleTable().setItems(items);

        } catch (Exception e) {
            //
        }
    }

    public TableView<ScreeningItem> getScheduleTable() {
        return scheduleTable;
    }

    public TableColumn<ScreeningItem, String> getDateColumn() {
        return dateColumn;
    }

    public TableColumn<ScreeningItem, String> getTimeColumn() {
        return timeColumn;
    }

    public TableColumn<ScreeningItem, String> getMovieColumn() {
        return movieColumn;
    }

    public TableColumn<ScreeningItem, String> getHallColumn() {
        return hallColumn;
    }

    public ScheduleService getScheduleService() {
        return scheduleService;
    }
}
