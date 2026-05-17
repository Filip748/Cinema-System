module com.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires java.net.http;
    requires com.google.gson;
    requires static lombok;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    opens com.example.frontend to javafx.fxml;
    exports com.example.frontend;

    opens com.example.frontend.login to javafx.fxml, com.google.gson;
    opens com.example.frontend.creator.dto to com.google.gson;

    opens com.example.frontend.schedule to javafx.fxml, com.google.gson, javafx.base, com.fasterxml.jackson.databind;

    exports com.example.frontend.creator;
    opens com.example.frontend.creator to javafx.graphics, javafx.fxml;
    opens com.example.frontend.adminDashboard to javafx.fxml;
}