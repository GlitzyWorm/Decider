package dk.kattehale.decider;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();

        // Block user from interacting with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);

        Label label = new Label(message);
        label.setId("alert-label");

        Button closeButton = new Button("Luk vindue");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.setId("alert-layout");
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(layout);
        stackPane.setId("alert-stack");

        Scene scene = new Scene(stackPane);
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("css/Decider.css"))); // Set stylesheet
        scene.setFill(Color.TRANSPARENT);

        window.setScene(scene);
        window.showAndWait();  // Shows the window and block user interaction with other windows until this is closed.
    }

}
