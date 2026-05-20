package com.example.frontend.statistics;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.List;

public class StatisticsController {

    @FXML
    private BarChart<String, Number> movieBarChart;

    @FXML
    private BarChart<String, Number> employeeBarChart;

    private StatsService statsService = new StatsService();

    public StatisticsController() {}

    @FXML
    public void initialize() {
        loadMovieStatistics();
        loadEmployeeStatistics();
    }

    private void loadMovieStatistics() {
        List<MovieStatsDto> movieData = getStatsService().getMovieTicketsStats();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tickets Sold");

        for (MovieStatsDto stat : movieData) {
            series.getData().add(new XYChart.Data<>(stat.getMovieTitle(), stat.getTicketsSold()));
        }

        getMovieBarChart().getData().clear();
        getMovieBarChart().getData().add(series);

        for (XYChart.Data<String, Number> data : series.getData()) {
            javafx.scene.Node bar = data.getNode();
            if (bar != null) {
                bar.setStyle("-fx-bar-fill: #8A2BE2;");
            }
        }

        javafx.application.Platform.runLater(() -> {
            for (javafx.scene.Node legendSymbol : getMovieBarChart().lookupAll(".chart-legend-item-symbol")) {
                legendSymbol.setStyle("-fx-background-color: #8A2BE2;");
            }
        });
    }

    private void loadEmployeeStatistics() {
        List<EmployeeStatsDto> employeeData = getStatsService().getEmployeeTicketsStats();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Efficiency");

        for (EmployeeStatsDto stat : employeeData) {
            series.getData().add(new XYChart.Data<>(stat.getEmployeeName(), stat.getTicketsSold()));
        }

        getEmployeeBarChart().getData().clear();
        getEmployeeBarChart().getData().add(series);
    }

    public BarChart<String, Number> getMovieBarChart() {
        return movieBarChart;
    }

    public BarChart<String, Number> getEmployeeBarChart() {
        return employeeBarChart;
    }

    public StatsService getStatsService() {
        return statsService;
    }
}