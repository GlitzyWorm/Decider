package dk.kattehale.decider;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.util.ResourceBundle;

public class AlertBox {

    public static void display(String title, String message, ResourceBundle bundle) {
        Stage window = new Stage();

        // Block user from interacting with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);

        Label label = new Label(message);
        label.setId("alert-label");

        Button closeButton = new Button(bundle.getString("alertboxBtn"));
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

        // Centers the window.
        window.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                window.setX((screenBounds.getWidth() - window.getWidth()) / 2);
                window.setY((screenBounds.getHeight() - window.getHeight()) / 2 - 150);

            }
        });

        window.setScene(scene);
        window.showAndWait();  // Shows the window and block user interaction with other windows until this is closed.


    }
}
