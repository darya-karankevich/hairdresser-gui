module com.hairdressergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.net.http;

    exports com.hairdresser.gui;
    opens com.hairdresser.gui to javafx.fxml;
}