module com.wiki {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.wiki to javafx.fxml;
    exports com.wiki;
}
