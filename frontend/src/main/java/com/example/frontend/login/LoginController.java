package com.example.frontend.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final AuthService authService;

    public LoginController() {
        this.authService = new AuthService();
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public AuthService getAuthService() {
        return authService;
    }

    @FXML
    protected void handleLogin() {
        String username = getUsernameField().getText();
        String password = getPasswordField().getText();

        if(username.trim().isEmpty() || password.trim().isEmpty()) {
            getMessageLabel().setTextFill(Color.RED);
            getMessageLabel().setText("Put login and password");
            return;
        }

        AuthResponseDto response = getAuthService().login(username, password);

        if(response.getRole() != null) {
            getMessageLabel().setTextFill(Color.GREEN);
            getMessageLabel().setText(response.getMessage());
            routeUser(response.getRole());
        } else {
            getMessageLabel().setTextFill(Color.RED);
            getMessageLabel().setText(response.getMessage());
        }
    }

    @FXML
    protected void handleRegister() {
        String username = getUsernameField().getText();
        String password = getPasswordField().getText();

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            getMessageLabel().setTextFill(Color.RED);
            getMessageLabel().setText("Fields can not be empty.");
            return;
        }

        String response = getAuthService().register(username, password);

        if (response.contains("Register done")) {
            getMessageLabel().setTextFill(Color.GREEN);
            getUsernameField().clear();
            getPasswordField().clear();
        } else {
            getMessageLabel().setTextFill(Color.RED);
        }

        getMessageLabel().setText(response);
    }

    private void routeUser(String role) {
        String fxmlPath = "";

        if ("ROLE_ADMIN".equals(role)) {
            fxmlPath = "/com/example/frontend/admin-dashboard/AdminDashboardView.fxml";
        } else if ("ROLE_EMPLOYEE".equals(role)) {
            fxmlPath = "/com/example/frontend/employee-dashboard/EmployeeView.fxml";
        } else {
            getMessageLabel().setTextFill(Color.RED);
            getMessageLabel().setText("Unknown role: " + role);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) getUsernameField().getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (Exception e) {
            getMessageLabel().setTextFill(Color.RED);
            getMessageLabel().setText("Error loading dashboard");
        }
    }
}