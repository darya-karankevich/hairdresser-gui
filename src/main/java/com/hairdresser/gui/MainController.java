package com.hairdresser.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends BaseController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Инициализация главного окна
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

    @FXML
    private void showUsers() {
        DialogFactory.createDialog("users").showAndWait();
    }

    @FXML
    private void showRoles() {
        DialogFactory.createDialog("roles").showAndWait();
    }

    @FXML
    private void showServiceTypes() {
        DialogFactory.createDialog("serviceTypes").showAndWait();
    }

    @FXML
    private void bookClient() {
        DialogFactory.createDialog("bookClient").showAndWait();
    }

    @FXML
    private void addService() {
        DialogFactory.createDialog("addService").showAndWait();
    }

    @FXML
    private void listFreeSlots() {
        DialogFactory.createDialog("freeSlots").showAndWait();
    }

    @FXML
    private void showMasterSchedule() {
        DialogFactory.createDialog("masterSchedule").showAndWait();
    }

    @FXML
    private void showClientReport() {
        DialogFactory.createDialog("clientReport").showAndWait();
    }

    @FXML
    private void showMasterReport() {
        DialogFactory.createDialog("masterReport").showAndWait();
    }

    @FXML
    private void showRevenueChart() {
        DialogFactory.createDialog("revenueChart").showAndWait();
    }

    @FXML
    private void showClientsByTimeChart() {
        DialogFactory.createDialog("clientsByTimeChart").showAndWait();
    }

    @FXML
    private void showProfitByServiceChart() {
        DialogFactory.createDialog("profitByServiceChart").showAndWait();
    }

    @FXML
    private void showSalaryCalculation() {
        DialogFactory.createDialog("salaryCalculation").showAndWait();
    }

    @FXML
    private void showAbout() {
        showInfo("Парикмахерская\nВерсия 1.0\nРазработано для курсового проекта");
    }

    @Override
    public void refreshData() {
        // Главное окно не содержит данных для обновления
    }
}