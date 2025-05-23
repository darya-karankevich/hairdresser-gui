package com.hairdresser.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        grid.setPadding(new Insets(10));

        // ComboBox для посетителей
        ComboBox<VisitorModel> visitorComboBox = new ComboBox<>();
        visitorComboBox.setPromptText("Выберите посетителя");
        visitorComboBox.setPrefWidth(200);

        // ComboBox для услуг
        ComboBox<ServiceTypeModel> serviceComboBox = new ComboBox<>();
        serviceComboBox.setPromptText("Выберите услугу");
        serviceComboBox.setPrefWidth(200);

        // ComboBox для мастеров
        ComboBox<UserModel> masterComboBox = new ComboBox<>();
        masterComboBox.setPromptText("Выберите мастера");
        masterComboBox.setPrefWidth(200);

        // ComboBox для смен
        ComboBox<ShiftModel> shiftComboBox = new ComboBox<>();
        shiftComboBox.setPromptText("Выберите смену");
        shiftComboBox.setPrefWidth(200);

        // DatePicker для даты
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Выберите дату");
        datePicker.setPrefWidth(200);

        // Загрузка данных для ComboBox
        try {
            // Посетители
            String visitorJson = ApiClient.getInstance().get("visitors");
            JSONArray visitorArray = new JSONArray(visitorJson);
            ObservableList<VisitorModel> visitors = FXCollections.observableArrayList();
            for (int i = 0; i < visitorArray.length(); i++) {
                JSONObject obj = visitorArray.getJSONObject(i);
                visitors.add(new VisitorModel(
                        obj.getInt("visitor_id"),
                        obj.getString("full_name"),
                        obj.getString("phone_number")
                ));
            }
            visitorComboBox.setItems(visitors);

            // Услуги
            String serviceJson = ApiClient.getInstance().get("serviceTypes");
            JSONArray serviceArray = new JSONArray(serviceJson);
            ObservableList<ServiceTypeModel> services = FXCollections.observableArrayList();
            for (int i = 0; i < serviceArray.length(); i++) {
                JSONObject obj = serviceArray.getJSONObject(i);
                services.add(new ServiceTypeModel(
                        obj.getInt("service_type_id"),
                        obj.getString("service_name")
                ));
            }
            serviceComboBox.setItems(services);

            String userJson = ApiClient.getInstance().get("users");
            JSONArray userArray = new JSONArray(userJson);
            ObservableList<UserModel> masters = FXCollections.observableArrayList();
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject obj = userArray.getJSONObject(i);
                if (obj.getInt("role_id") == 2) {
                    masters.add(new UserModel(
                            obj.getInt("user_id"),
                            obj.getString("username"),
                            obj.getString("password"),
                            obj.getInt("role_id"),
                            obj.getString("role_name")
                    ));
                }
            }
            masterComboBox.setItems(masters);

            // Смены
            String shiftJson = ApiClient.getInstance().get("shifts");
            JSONArray shiftArray = new JSONArray(shiftJson);
            ObservableList<ShiftModel> shifts = FXCollections.observableArrayList();
            for (int i = 0; i < shiftArray.length(); i++) {
                JSONObject obj = shiftArray.getJSONObject(i);
                shifts.add(new ShiftModel(
                        obj.getInt("shift_id"),
                        obj.getString("shift_hours")
                ));
            }
            shiftComboBox.setItems(shifts);
        } catch (IOException | InterruptedException e) {
            showError("Ошибка загрузки данных: " + e.getMessage());
        }

        // Настройка отображения в ComboBox
        visitorComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(VisitorModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getFullName());
            }
        });
        visitorComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(VisitorModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getFullName());
            }
        });

        serviceComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ServiceTypeModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getServiceName());
            }
        });
        serviceComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ServiceTypeModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getServiceName());
            }
        });

        masterComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(UserModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getUsername());
            }
        });
        masterComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(UserModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getUsername());
            }
        });

        shiftComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ShiftModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getShiftHours());
            }
        });
        shiftComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ShiftModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getShiftHours());
            }
        });

        // Размещение элементов в GridPane
        grid.add(new Label("Посетитель:"), 0, 0);
        grid.add(visitorComboBox, 1, 0);
        grid.add(new Label("Услуга:"), 0, 1);
        grid.add(serviceComboBox, 1, 1);
        grid.add(new Label("Мастер:"), 0, 2);
        grid.add(masterComboBox, 1, 2);
        grid.add(new Label("Смена:"), 0, 3);
        grid.add(shiftComboBox, 1, 3);
        grid.add(new Label("Дата:"), 0, 4);
        grid.add(datePicker, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Валидация и отправка запроса
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        // Валидация: активировать кнопку OK только если все поля заполнены
        visitorComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            okButton.setDisable(
                    visitorComboBox.getSelectionModel().isEmpty() ||
                            serviceComboBox.getSelectionModel().isEmpty() ||
                            masterComboBox.getSelectionModel().isEmpty() ||
                            shiftComboBox.getSelectionModel().isEmpty() ||
                            datePicker.getValue() == null
            );
        });
        serviceComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            okButton.setDisable(
                    visitorComboBox.getSelectionModel().isEmpty() ||
                            serviceComboBox.getSelectionModel().isEmpty() ||
                            masterComboBox.getSelectionModel().isEmpty() ||
                            shiftComboBox.getSelectionModel().isEmpty() ||
                            datePicker.getValue() == null
            );
        });
        masterComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            okButton.setDisable(
                    visitorComboBox.getSelectionModel().isEmpty() ||
                            serviceComboBox.getSelectionModel().isEmpty() ||
                            masterComboBox.getSelectionModel().isEmpty() ||
                            shiftComboBox.getSelectionModel().isEmpty() ||
                            datePicker.getValue() == null
            );
        });
        shiftComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            okButton.setDisable(
                    visitorComboBox.getSelectionModel().isEmpty() ||
                            serviceComboBox.getSelectionModel().isEmpty() ||
                            masterComboBox.getSelectionModel().isEmpty() ||
                            shiftComboBox.getSelectionModel().isEmpty() ||
                            datePicker.getValue() == null
            );
        });
        datePicker.valueProperty().addListener((obs, old, newVal) -> {
            okButton.setDisable(
                    visitorComboBox.getSelectionModel().isEmpty() ||
                            serviceComboBox.getSelectionModel().isEmpty() ||
                            masterComboBox.getSelectionModel().isEmpty() ||
                            shiftComboBox.getSelectionModel().isEmpty() ||
                            datePicker.getValue() == null
            );
        });

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    VisitorModel selectedVisitor = visitorComboBox.getSelectionModel().getSelectedItem();
                    ServiceTypeModel selectedService = serviceComboBox.getSelectionModel().getSelectedItem();
                    UserModel selectedMaster = masterComboBox.getSelectionModel().getSelectedItem();
                    ShiftModel selectedShift = shiftComboBox.getSelectionModel().getSelectedItem();
                    LocalDate selectedDate = datePicker.getValue();

                    if (selectedVisitor == null || selectedService == null || selectedMaster == null ||
                            selectedShift == null || selectedDate == null) {
                        showError("Все поля должны быть заполнены!");
                        return null;
                    }

                    JSONObject json = new JSONObject();
                    json.put("visitor_id", selectedVisitor.getVisitorId());
                    json.put("service_type_id", selectedService.getServiceTypeId());
                    json.put("user_id", selectedMaster.getUserId());
                    json.put("shift_id", selectedShift.getShiftId());
                    json.put("visit_date", selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE));

                    System.out.println(json);

                    ApiClient.getInstance().post("visits", json.toString());
                } catch (IOException | InterruptedException e) {
                    showError("Ошибка отправки данных: " + e.getMessage());
                    System.out.println(e.getMessage());
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

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static Dialog<ButtonType> createMasterScheduleDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Расписание мастера");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // ComboBox для выбора мастера
        ComboBox<UserModel> masterComboBox = new ComboBox<>();
        masterComboBox.setPromptText("Выберите мастера");
        masterComboBox.setPrefWidth(200);

        // Загрузка списка мастеров
        ObservableList<UserModel> masters = FXCollections.observableArrayList();
        try {
            String json = ApiClient.getInstance().get("users");
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                masters.add(new UserModel(
                        obj.getInt("user_id"),
                        obj.getString("username"),
                        obj.getString("password"),
                        obj.getInt("role_id"),
                        obj.getString("role_name")
                ));
            }
            masterComboBox.setItems(masters);
        } catch (IOException | InterruptedException e) {
            showError("Ошибка загрузки мастеров: " + e.getMessage());
        }

        // Настройка отображения имени мастера в ComboBox
        masterComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(UserModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getUsername());
            }
        });
        masterComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(UserModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getUsername());
            }
        });

        // Таблица для отображения расписания
        TableView<VisitModel> table = new TableView<>();
        TableColumn<VisitModel, Integer> visitIdCol = new TableColumn<>("ID визита");
        visitIdCol.setCellValueFactory(new PropertyValueFactory<>("visitId"));
        TableColumn<VisitModel, String> visitorCol = new TableColumn<>("Клиент");
        visitorCol.setCellValueFactory(new PropertyValueFactory<>("visitorName"));
        TableColumn<VisitModel, String> serviceCol = new TableColumn<>("Услуга");
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        TableColumn<VisitModel, String> shiftCol = new TableColumn<>("Смена");
        shiftCol.setCellValueFactory(new PropertyValueFactory<>("shiftHours"));
        TableColumn<VisitModel, String> dateCol = new TableColumn<>("Дата");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        table.getColumns().addAll(visitIdCol, visitorCol, serviceCol, shiftCol, dateCol);

        // Обработчик выбора мастера
        masterComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    String json = ApiClient.getInstance().get("visits?user_id=" + newValue.getUserId());
                    System.out.println(newValue);
                    JSONArray array = new JSONArray(json);
                    ObservableList<VisitModel> visits = FXCollections.observableArrayList();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        visits.add(new VisitModel(
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
                    table.setItems(visits);
                } catch (IOException | InterruptedException e) {
                    showError("Ошибка загрузки расписания: " + e.getMessage());
                }
            } else {
                table.setItems(FXCollections.observableArrayList());
            }
        });

        vbox.getChildren().addAll(new Label("Мастер:"), masterComboBox, table);
        dialog.getDialogPane().setContent(vbox);
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