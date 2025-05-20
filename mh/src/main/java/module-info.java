module com.wiki {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.wiki to javafx.fxml;
    exports com.wiki;
}
