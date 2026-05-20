package com.example.frontend.login;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final AuthService authService;

    public LoginController() {
        this.authService = new AuthService();
    }

    @FXML
    protected void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(username.trim().isEmpty() || password.trim().isEmpty()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Put login and password");
            return;
        }

        String response = authService.login(username, password);

        if(response.contains("Success")) {
            messageLabel.setTextFill(Color.GREEN);
        } else {
            messageLabel.setTextFill(Color.RED);
        }

        messageLabel.setText(response);
    }

    @FXML
    protected void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Fields can not be empty.");
            return;
        }

        String response = authService.register(username, password);

        if (response.contains("succes")) {
            messageLabel.setTextFill(Color.GREEN);
            usernameField.clear();
            passwordField.clear();
        } else {
            messageLabel.setTextFill(Color.RED);
        }

        messageLabel.setText(response);
    }
}
