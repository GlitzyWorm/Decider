package dk.kattehale.decider;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    // Constants
    public static final int MAXFIELDS = 10;      // Allows a maximum of MAXFIELDS of TextFields to be created.
    public static final int MINFIELDS = 2;       // Allows a minimum of MINFIELDS of TextFields to be created.

    static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("field-pick.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("css/Decider.css")));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Decider");
        stage.setScene(scene);
        stage.show();

        // Center in the middle of the screen
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        launch();
    }



    // TODO: add saving function.
    // Saves data and closes program.
    public static void closeProgram() {
        stage.close();
    }

    // Minimizes program
    public static void minimizeProgram() {
        stage.setIconified(true);
    }

    // Maximizes program
    public static void maximizeProgram() {
        stage.setMaximized(!stage.isMaximized());
    }

    // TODO: Add support for resizing.
}