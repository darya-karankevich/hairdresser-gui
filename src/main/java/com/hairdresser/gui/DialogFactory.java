package com.hairdresser.gui;

import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogFactory {
    public static Dialog<?> createDialog(String type) {
        switch (type.toLowerCase()) {
            case "users":
                return createUsersDialog();
            case "roles":
                return createRolesDialog();
            case "servicetypes":
                return createServiceTypesDialog();
            case "bookclient":
                return createBookClientDialog();
            case "addservice":
                return createAddServiceDialog();
            case "freeslots":
                return createFreeSlotsDialog();
            case "masterschedule":
                return createMasterScheduleDialog();
            case "clientreport":
                return createClientReportDialog();
            case "masterreport":
                return createMasterReportDialog();
            case "revenuechart":
                return createRevenueChartDialog();
            case "clientsbytimechart":
                return createClientsByTimeChartDialog();
            case "profitbyservicechart":
                return createProfitByServiceChartDialog();
            case "salarycalculation":
                return createSalaryCalculationDialog();
            default:
                throw new IllegalArgumentException("Неизвестный тип диалога: " + type);
        }
    }

    private static Dialog<List<UserModel>> createUsersDialog() {
        Dialog<List<UserModel>> dialog = new Dialog<>();
        dialog.setTitle("Пользователи");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TableView<UserModel> table = new TableView<>();
        TableColumn<UserModel, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        TableColumn<UserModel, String> usernameCol = new TableColumn<>("Имя пользователя");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<UserModel, String> roleCol = new TableColumn<>("Роль");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        table.getColumns().addAll(idCol, usernameCol, roleCol);

        try {
            String json = ApiClient.getInstance().get("users");
            JSONArray array = new JSONArray(json);
            List<UserModel> users = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                users.add(new UserModel(
                        obj.getInt("user_id"),
                        obj.getString("username"),
                        obj.getString("password"),
                        obj.getInt("role_id"),
                        obj.getString("role_name")
                ));
            }
            table.getItems().addAll(users);
        } catch (IOException | InterruptedException e) {
            dialog.setHeaderText("Ошибка загрузки данных: " + e.getMessage());
        }

        dialog.getDialogPane().setContent(table);
        dialog.setResultConverter(button -> button == ButtonType.OK ? table.getItems() : null);
        return dialog;
    }

    private static Dialog<List<RoleModel>> createRolesDialog() {
        Dialog<List<RoleModel>> dialog = new Dialog<>();
        dialog.setTitle("Роли пользователей");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TableView<RoleModel> table = new TableView<>();
        TableColumn<RoleModel, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        TableColumn<RoleModel, String> nameCol = new TableColumn<>("Название роли");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        table.getColumns().addAll(idCol, nameCol);

        try {
            String json = ApiClient.getInstance().get("roles");
            JSONArray array = new JSONArray(json);
            List<RoleModel> roles = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                roles.add(new RoleModel(
                        obj.getInt("role_id"),
                        obj.getString("role_name")
                ));
            }
            table.getItems().addAll(roles);
        } catch (IOException | InterruptedException e) {
            dialog.setHeaderText("Ошибка загрузки данных: " + e.getMessage());
        }

        dialog.getDialogPane().setContent(table);
        dialog.setResultConverter(button -> button == ButtonType.OK ? table.getItems() : null);
        return dialog;
    }

    private static Dialog<List<ServiceTypeModel>> createServiceTypesDialog() {
        Dialog<List<ServiceTypeModel>> dialog = new Dialog<>();
        dialog.setTitle("Виды услуг");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TableView<ServiceTypeModel> table = new TableView<>();
        TableColumn<ServiceTypeModel, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("serviceTypeId"));
        TableColumn<ServiceTypeModel, String> nameCol = new TableColumn<>("Название услуги");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        table.getColumns().addAll(idCol, nameCol);

        try {
            String json = ApiClient.getInstance().get("serviceTypes");
            JSONArray array = new JSONArray(json);
            List<ServiceTypeModel> services = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                services.add(new ServiceTypeModel(
                        obj.getInt("service_type_id"),
                        obj.getString("service_name")
                ));
            }
            table.getItems().addAll(services);
        } catch (IOException | InterruptedException e) {
            dialog.setHeaderText("Ошибка загрузки данных: " + e.getMessage());
        }

        dialog.getDialogPane().setContent(table);
        dialog.setResultConverter(button -> button == ButtonType.OK ? table.getItems() : null);
        return dialog;
    }

    private static Dialog<ButtonType> createAddServiceDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Добавление новой услуги");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField serviceNameField = new TextField();
        TextField priceField = new TextField();
        grid.add(new Label("Название услуги:"), 0, 0);
        grid.add(serviceNameField, 1, 0);
        grid.add(new Label("Цена:"), 0, 1);
        grid.add(priceField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("service_name", serviceNameField.getText());
                    json.put("price", Double.parseDouble(priceField.getText()));
                    ApiClient.getInstance().post("serviceTypes", json.toString());
                } catch (IOException | InterruptedException | NumberFormatException e) {
                    dialog.setHeaderText("Ошибка: " + e.getMessage());
                    return null;
                }
            }
            return button;
        });

        return dialog;
    }

    private static Dialog<ButtonType> createBookClientDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Запись клиента");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField visitorIdField = new TextField();
        TextField serviceTypeIdField = new TextField();
        TextField userIdField = new TextField();
        TextField shiftIdField = new TextField();
        TextField visitDateField = new TextField();

        grid.add(new Label("ID посетителя:"), 0, 0);
        grid.add(visitorIdField, 1, 0);
        grid.add(new Label("ID услуги:"), 0, 1);
        grid.add(serviceTypeIdField, 1, 1);
        grid.add(new Label("ID мастера:"), 0, 2);
        grid.add(userIdField, 1, 2);
        grid.add(new Label("ID смены:"), 0, 3);
        grid.add(shiftIdField, 1, 3);
        grid.add(new Label("Дата (YYYY-MM-DD):"), 0, 4);
        grid.add(visitDateField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("visitor_id", Integer.parseInt(visitorIdField.getText()));
                    json.put("service_type_id", Integer.parseInt(serviceTypeIdField.getText()));
                    json.put("user_id", Integer.parseInt(userIdField.getText()));
                    json.put("shift_id", Integer.parseInt(shiftIdField.getText()));
                    json.put("visit_date", visitDateField.getText());
                    ApiClient.getInstance().post("visits", json.toString());
                } catch (IOException | InterruptedException | NumberFormatException e) {
                    dialog.setHeaderText("Ошибка: " + e.getMessage());
                    return null;
                }
            }
            return button;
        });

        return dialog;
    }

    private static Dialog<ButtonType> createFreeSlotsDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Список свободных мест");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField userIdField = new TextField();
        grid.add(new Label("ID мастера:"), 0, 0);
        grid.add(userIdField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    dialog.setHeaderText("Функция в разработке");
                } catch (Exception e) {
                    dialog.setHeaderText("Ошибка: " + e.getMessage());
                    return null;
                }
            }
            return button;
        });

        return dialog;
    }

    private static Dialog<ButtonType> createMasterScheduleDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Расписание мастера");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField userIdField = new TextField();
        grid.add(new Label("ID мастера:"), 0, 0);
        grid.add(userIdField, 1, 0);

        TableView<ScheduleEntry> table = new TableView<>();
        TableColumn<ScheduleEntry, String> dateCol = new TableColumn<>("Дата");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        TableColumn<ScheduleEntry, String> shiftCol = new TableColumn<>("Смена");
        shiftCol.setCellValueFactory(new PropertyValueFactory<>("shiftHours"));
        TableColumn<ScheduleEntry, String> visitorCol = new TableColumn<>("Клиент");
        visitorCol.setCellValueFactory(new PropertyValueFactory<>("visitorName"));
        TableColumn<ScheduleEntry, String> serviceCol = new TableColumn<>("Услуга");
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        table.getColumns().addAll(dateCol, shiftCol, visitorCol, serviceCol);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().setExpandableContent(table);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    String json = ApiClient.getInstance().get("reports/master-schedule?userId=" + userIdField.getText());
                    JSONArray array = new JSONArray(json);
                    List<ScheduleEntry> entries = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        entries.add(new ScheduleEntry(
                                obj.getString("visit_date"),
                                obj.getString("shift_hours"),
                                obj.getString("visitor_name"),
                                obj.getString("service_name")
                        ));
                    }
                    table.getItems().clear();
                    table.getItems().addAll(entries);
                } catch (IOException | InterruptedException e) {
                    dialog.setHeaderText("Ошибка: " + e.getMessage());
                    return null;
                }
            }
            return button;
        });

        return dialog;
    }

    private static Dialog<ButtonType> createClientReportDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Отчет по клиентам");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TableView<ClientReportEntry> table = new TableView<>();
        TableColumn<ClientReportEntry, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("visitorId"));
        TableColumn<ClientReportEntry, String> nameCol = new TableColumn<>("ФИО");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        TableColumn<ClientReportEntry, String> phoneCol = new TableColumn<>("Телефон");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<ClientReportEntry, Integer> visitsCol = new TableColumn<>("Кол-во посещений");
        visitsCol.setCellValueFactory(new PropertyValueFactory<>("visitCount"));
        TableColumn<ClientReportEntry, Double> spentCol = new TableColumn<>("Потрачено");
        spentCol.setCellValueFactory(new PropertyValueFactory<>("totalSpent"));
        table.getColumns().addAll(idCol, nameCol, phoneCol, visitsCol, spentCol);

        try {
            String json = ApiClient.getInstance().get("reports/client-report");
            JSONArray array = new JSONArray(json);
            List<ClientReportEntry> entries = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                entries.add(new ClientReportEntry(
                        obj.getInt("visitor_id"),
                        obj.getString("full_name"),
                        obj.getString("phone_number"),
                        obj.getInt("visit_count"),
                        obj.getDouble("total_spent")
                ));
            }
            table.getItems().addAll(entries);
        } catch (IOException | InterruptedException e) {
            dialog.setHeaderText("Ошибка: " + e.getMessage());
        }

        dialog.getDialogPane().setContent(table);
        return dialog;
    }

    private static Dialog<ButtonType> createMasterReportDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Отчет по мастерам");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TableView<MasterReportEntry> table = new TableView<>();
        TableColumn<MasterReportEntry, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        TableColumn<MasterReportEntry, String> nameCol = new TableColumn<>("Имя");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<MasterReportEntry, Integer> visitsCol = new TableColumn<>("Кол-во посещений");
        visitsCol.setCellValueFactory(new PropertyValueFactory<>("visitCount"));
        TableColumn<MasterReportEntry, Double> revenueCol = new TableColumn<>("Выручка");
        revenueCol.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
        table.getColumns().addAll(idCol, nameCol, visitsCol, revenueCol);

        try {
            String json = ApiClient.getInstance().get("reports/master-report");
            JSONArray array = new JSONArray(json);
            List<MasterReportEntry> entries = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                entries.add(new MasterReportEntry(
                        obj.getInt("user_id"),
                        obj.getString("username"),
                        obj.getInt("visit_count"),
                        obj.getDouble("total_revenue")
                ));
            }
            table.getItems().addAll(entries);
        } catch (IOException | InterruptedException e) {
            dialog.setHeaderText("Ошибка: " + e.getMessage());
        }

        dialog.getDialogPane().setContent(table);
        return dialog;
    }

    private static Dialog<ButtonType> createRevenueChartDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Диаграмма выручки по мастерам");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Мастер");
        yAxis.setLabel("Выручка");
        chart.setTitle("Выручка по мастерам");

        try {
            String json = ApiClient.getInstance().get("reports/revenue-by-master");
            JSONArray array = new JSONArray(json);
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                series.getData().add(new XYChart.Data<>(obj.getString("username"), obj.getDouble("revenue")));
            }
            chart.getData().add(series);
        } catch (IOException | InterruptedException e) {
            dialog.setHeaderText("Ошибка: " + e.getMessage());
        }

        dialog.getDialogPane().setContent(chart);
        return dialog;
    }

    private static Dialog<ButtonType> createClientsByTimeChartDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Диаграмма количества клиентов по сменам");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Смена");
        yAxis.setLabel("Количество клиентов");
        chart.setTitle("Клиенты по сменам");

        try {
            String json = ApiClient.getInstance().get("reports/clients-by-time");
            JSONArray array = new JSONArray(json);
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                series.getData().add(new XYChart.Data<>(obj.getString("shift_hours"), obj.getInt("client_count")));
            }
            chart.getData().add(series);
        } catch (IOException | InterruptedException e) {
            dialog.setHeaderText("Ошибка: " + e.getMessage());
        }

        dialog.getDialogPane().setContent(chart);
        return dialog;
    }

    private static Dialog<ButtonType> createProfitByServiceChartDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Диаграмма прибыли по услугам");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Услуга");
        yAxis.setLabel("Прибыль");
        chart.setTitle("Прибыль по услугам");

        try {
            String json = ApiClient.getInstance().get("reports/profit-by-service");
            JSONArray array = new JSONArray(json);
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                series.getData().add(new XYChart.Data<>(obj.getString("service_name"), obj.getDouble("profit")));
            }
            chart.getData().add(series);
        } catch (IOException | InterruptedException e) {
            dialog.setHeaderText("Ошибка: " + e.getMessage());
        }

        dialog.getDialogPane().setContent(chart);
        return dialog;
    }

    private static Dialog<ButtonType> createSalaryCalculationDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Расчет зарплаты мастеров");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TableView<SalaryEntry> table = new TableView<>();
        TableColumn<SalaryEntry, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        TableColumn<SalaryEntry, String> nameCol = new TableColumn<>("Имя");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<SalaryEntry, Double> percentCol = new TableColumn<>("Процент");
        percentCol.setCellValueFactory(new PropertyValueFactory<>("salaryPercentage"));
        TableColumn<SalaryEntry, Double> salaryCol = new TableColumn<>("Зарплата");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        table.getColumns().addAll(idCol, nameCol, percentCol, salaryCol);

        try {
            String json = ApiClient.getInstance().get("reports/salary-calculation");
            JSONArray array = new JSONArray(json);
            List<SalaryEntry> entries = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                entries.add(new SalaryEntry(
                        obj.getInt("user_id"),
                        obj.getString("username"),
                        obj.getDouble("salary_percentage"),
                        obj.getDouble("salary")
                ));
            }
            table.getItems().addAll(entries);
        } catch (IOException | InterruptedException e) {
            dialog.setHeaderText("Ошибка: " + e.getMessage());
        }

        dialog.getDialogPane().setContent(table);
        return dialog;
    }

    // Вспомогательные классы для отображения данных
    public static class ScheduleEntry {
        private final String visitDate;
        private final String shiftHours;
        private final String visitorName;
        private final String serviceName;

        public ScheduleEntry(String visitDate, String shiftHours, String visitorName, String serviceName) {
            this.visitDate = visitDate;
            this.shiftHours = shiftHours;
            this.visitorName = visitorName;
            this.serviceName = serviceName;
        }

        public String getVisitDate() { return visitDate; }
        public String getShiftHours() { return shiftHours; }
        public String getVisitorName() { return visitorName; }
        public String getServiceName() { return serviceName; }
    }

    public static class ClientReportEntry {
        private final int visitorId;
        private final String fullName;
        private final String phoneNumber;
        private final int visitCount;
        private final double totalSpent;

        public ClientReportEntry(int visitorId, String fullName, String phoneNumber, int visitCount, double totalSpent) {
            this.visitorId = visitorId;
            this.fullName = fullName;
            this.phoneNumber = phoneNumber;
            this.visitCount = visitCount;
            this.totalSpent = totalSpent;
        }

        public int getVisitorId() { return visitorId; }
        public String getFullName() { return fullName; }
        public String getPhoneNumber() { return phoneNumber; }
        public int getVisitCount() { return visitCount; }
        public double getTotalSpent() { return totalSpent; }
    }

    public static class MasterReportEntry {
        private final int userId;
        private final String username;
        private final int visitCount;
        private final double totalRevenue;

        public MasterReportEntry(int userId, String username, int visitCount, double totalRevenue) {
            this.userId = userId;
            this.username = username;
            this.visitCount = visitCount;
            this.totalRevenue = totalRevenue;
        }

        public int getUserId() { return userId; }
        public String getUsername() { return username; }
        public int getVisitCount() { return visitCount; }
        public double getTotalRevenue() { return totalRevenue; }
    }

    public static class SalaryEntry {
        private final int userId;
        private final String username;
        private final double salaryPercentage;
        private final double salary;

        public SalaryEntry(int userId, String username, double salaryPercentage, double salary) {
            this.userId = userId;
            this.username = username;
            this.salaryPercentage = salaryPercentage;
            this.salary = salary;
        }

        public int getUserId() { return userId; }
        public String getUsername() { return username; }
        public double getSalaryPercentage() { return salaryPercentage; }
        public double getSalary() { return salary; }
    }
}