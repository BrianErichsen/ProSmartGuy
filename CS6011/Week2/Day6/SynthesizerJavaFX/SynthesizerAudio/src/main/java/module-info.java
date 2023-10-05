module com.example.synthesizeraudio {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.example.synthesizeraudio to javafx.fxml;
    exports com.example.synthesizeraudio;
}