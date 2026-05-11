module com.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.net.http;
    requires com.google.gson;
    requires static lombok;

    opens com.example.frontend to javafx.fxml;
    exports com.example.frontend;

    opens com.example.frontend.login to javafx.fxml, com.google.gson;

    opens com.example.frontend.schedule to javafx.fxml, com.google.gson;
}