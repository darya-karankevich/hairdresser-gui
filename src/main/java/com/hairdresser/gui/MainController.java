package com.hairdresser.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class MainController {
    @FXML
    private TableView<VisitModel> visitsTable;

    @FXML
    private void initialize() {
        // Загрузка данных визитов при инициализации
        loadVisits();
    }

    private void loadVisits() {
        try {
            String json = ApiClient.getInstance().get("visits");
            JSONArray array = new JSONArray(json);
            visitsTable.getItems().clear();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                visitsTable.getItems().add(new VisitModel(
                        obj.getInt("visit_id"),
                        obj.getInt("visitor_id"),
                        obj.getString("visitor_name"),
                        obj.getInt("service_type_id"),
                        obj.getString("service_name"),
                        obj.getInt("user_id"),
                        obj.getString("master_name"),
                        obj.getInt("shift_id"),
                        obj.getString("shift_hours"),
                        obj.getString("visit_date")
                ));
            }
        } catch (IOException | InterruptedException e) {
            showError("Ошибка загрузки визитов: " + e.getMessage());
        }
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    @FXML
    private void showUsersDialog() {
        DialogFactory.createDialog("users").showAndWait();
    }

    @FXML
    private void showRolesDialog() {
        DialogFactory.createDialog("roles").showAndWait();
    }

    @FXML
    private void showServiceTypesDialog() {
        DialogFactory.createDialog("serviceTypes").showAndWait();
    }

    @FXML
    private void showBookClientDialog() {
        DialogFactory.createDialog("bookClient").showAndWait();
        loadVisits(); // Обновляем таблицу после записи клиента
    }

    @FXML
    private void showAddServiceDialog() {
        DialogFactory.createDialog("addService").showAndWait();
    }

    @FXML
    private void showFreeSlotsDialog() {
        DialogFactory.createDialog("freeSlots").showAndWait();
    }

    @FXML
    private void showMasterScheduleDialog() {
        DialogFactory.createDialog("masterSchedule").showAndWait();
    }

    @FXML
    private void showClientReportDialog() {
        DialogFactory.createDialog("clientReport").showAndWait();
    }

    @FXML
    private void showMasterReportDialog() {
        DialogFactory.createDialog("masterReport").showAndWait();
    }

    @FXML
    private void showRevenueChartDialog() {
        DialogFactory.createDialog("revenueChart").showAndWait();
    }

    @FXML
    private void showClientsByTimeChartDialog() {
        DialogFactory.createDialog("clientsByTimeChart").showAndWait();
    }

    @FXML
    private void showProfitByServiceChartDialog() {
        DialogFactory.createDialog("profitByServiceChart").showAndWait();
    }

    @FXML
    private void showSalaryCalculationDialog() {
        DialogFactory.createDialog("salaryCalculation").showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showAbout(ActionEvent actionEvent) {
    }
}