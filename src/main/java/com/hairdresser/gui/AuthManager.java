package com.hairdresser.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class AuthManager {
    private final Stage primaryStage;
    private final MainApp mainApp;

    public AuthManager(Stage primaryStage, MainApp mainApp) {
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
    }

    public void showLoginScreen() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Вход в систему");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label usernameLabel = new Label("Имя пользователя:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Введите имя пользователя");

        Label passwordLabel = new Label("Пароль:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Введите пароль");

        Button loginButton = new Button("Войти");
        loginButton.setDefaultButton(true);

        Hyperlink registerLink = new Hyperlink("Зарегистрироваться");
        registerLink.setOnAction(e -> {
            loginStage.close();
            showRegisterScreen();
        });

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(registerLink, 1, 3);
        grid.add(errorLabel, 0, 4, 2, 1);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Заполните все поля!");
                return;
            }

            try {
                JSONObject request = new JSONObject();
                request.put("username", username);
                request.put("password", password);

                String response = ApiClient.getInstance().post("login", request.toString());
                JSONObject responseJson = new JSONObject(response);

                if (responseJson.has("user_id")) {
                    loginStage.close();
                    mainApp.startMainWindow(primaryStage);
                } else {
                    errorLabel.setText(responseJson.optString("error", "Неизвестная ошибка"));
                }
            } catch (IOException | InterruptedException ex) {
                errorLabel.setText("Ошибка: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid, 450, 200);
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();
    }

    private void showRegisterScreen() {
        Stage registerStage = new Stage();
        registerStage.setTitle("Регистрация");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label usernameLabel = new Label("Имя пользователя:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Введите имя пользователя");

        Label passwordLabel = new Label("Пароль:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Введите пароль");

        Label roleLabel = new Label("Роль:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("мастер", "администратор");
        roleComboBox.setPromptText("Выберите роль");
        roleComboBox.setValue("мастер");

        Button registerButton = new Button("Зарегистрироваться");
        registerButton.setDefaultButton(true);

        Hyperlink loginLink = new Hyperlink("Войти");
        loginLink.setOnAction(e -> {
            registerStage.close();
            showLoginScreen();
        });

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(roleLabel, 0, 2);
        grid.add(roleComboBox, 1, 2);
        grid.add(registerButton, 1, 3);
        grid.add(loginLink, 1, 4);
        grid.add(errorLabel, 0, 5, 2, 1);

        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String role = roleComboBox.getValue();

            if (username.isEmpty() || password.isEmpty() || role == null) {
                errorLabel.setText("Заполните все поля!");
                return;
            }

            try {
                JSONObject request = new JSONObject();
                request.put("username", username);
                request.put("password", password);
                request.put("role_name", role);

                String response = ApiClient.getInstance().post("register", request.toString());
                JSONObject responseJson = new JSONObject(response);

                if (responseJson.has("user_id")) {
                    errorLabel.setText("Регистрация успешна! Войдите в систему.");
                    registerButton.setDisable(true);
                } else {
                    errorLabel.setText(responseJson.optString("error", "Неизвестная ошибка"));
                }
            } catch (IOException | InterruptedException ex) {
                errorLabel.setText("Ошибка: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid, 450, 250);
        registerStage.setScene(scene);
        registerStage.setResizable(true);
        registerStage.show();
    }
}