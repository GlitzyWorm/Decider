module dk.kattehale.decider {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens dk.kattehale.decider to javafx.fxml;
    exports dk.kattehale.decider;
}