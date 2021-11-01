package dk.kattehale.decider;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.prefs.Preferences;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    // Constants
    public static final int MAXFIELDS = 10;      // Allows a maximum of MAXFIELDS of TextFields to be created.
    public static final int MINFIELDS = 2;       // Allows a minimum of MINFIELDS of TextFields to be created.

    // Used for saving settings
    private static Preferences prefs;

    public static Locale locale;

    static Stage stage;
    static ResourceBundle bundle;
    private static FXMLLoader fxmlLoader;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        prefs = Preferences.userRoot().node(this.getClass().getName());

        // Retrieves chosen language
        locale = new Locale(prefs.get(Settings.LANGUAGE.toString(), "en"));
        bundle = ResourceBundle.getBundle("DeciderBundle", locale);

        // Loads FXML and sets up scene and stage
        fxmlLoader = new FXMLLoader(Main.class.getResource("field-pick.fxml"), bundle);
        scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("css/Decider.css")));
        scene.setFill(Color.TRANSPARENT);                                                                               // TODO: Add dropshadow to main window.
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

    // Saves settins and reloads the stage
    public static void setPref(Settings setting, String value) {

        // Saves settings in preferences
        switch (setting) {
            case LANGUAGE, THEME -> prefs.put(setting.toString(), value);
            case MAXNUM -> prefs.putInt(setting.toString(), Integer.parseInt(value));
        }

        // Reloads Stage
        stage.close();
        Platform.runLater( () -> {
            try {
                new Main().start( new Stage() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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


    enum Settings {
        LANGUAGE,
        MAXNUM,
        THEME
    }
}