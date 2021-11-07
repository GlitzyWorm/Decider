package dk.kattehale.decider;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class FieldController implements Initializable {

    private double xOffset;
    private double yOffset;
    private int amountOfTextFields = Main.MINFIELDS;
    private boolean firstLaunch = true;

    /* Root */
    @FXML private BorderPane root;

    /* Toolbar and its buttons */
    @FXML private ToolBar toolBar;
    @FXML private Button closeButton;
    @FXML private Button minButton;

    /* Buttons and textfieldContainer for handling adding and deleting TextFields */
    @FXML private Button addButton;
    @FXML private Button delButton;
    @FXML private VBox textfieldContainer;

    /* Pick button */
    @FXML private Button pickButton;

    ResourceBundle resources;
    Preferences prefs;


    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        prefs = Main.prefs;

        if(firstLaunch) {
            delButton.setDisable(true);
        }

        int extraFields = prefs.getInt(Main.Settings.DEFNUM.toString(), 2) - Main.MINFIELDS;

        for(int i = 0; i<extraFields; i++) {
            addTextField();
        }

    }


    /* Add and delete TextFields. */
    @FXML
    protected void addTextField() {
        // Gets the amount of TextFields in VBox.
        int index = textfieldContainer.getChildren().size();

        // If the index is 1 less than MAXFIELDS, add a last TextField and disable button.
        // Else add a new TextField above the 'add'-button.
        if(index == Main.MAXFIELDS - 1) {
            amountOfTextFields++;
            addButton.setDisable(true);
            delButton.setDisable(false);
            TextField newField = new TextField();
            newField.setPromptText(resources.getString("fieldPickPromt"));
            textfieldContainer.getChildren().add(index,newField);
        } else {
            amountOfTextFields++;
            delButton.setDisable(false);
            TextField newField = new TextField();
            newField.setPromptText(resources.getString("fieldPickPromt"));
            textfieldContainer.getChildren().add(index,newField);
        }
    }

    @FXML
    protected void delTextField() {
        // Gets the amount of TextFields in VBox.
        int index = textfieldContainer.getChildren().size() - 1;

        // If index is equal to MINFIELDS, remove the last TextField and disable button.
        // Else remove a TextField
        if(index == Main.MINFIELDS) {
            amountOfTextFields--;
            delButton.setDisable(true);
            addButton.setDisable(false);
            textfieldContainer.getChildren().remove(index);
        } else {
            amountOfTextFields--;
            addButton.setDisable(false);
            textfieldContainer.getChildren().remove(index);
        }
    }

    @FXML
    protected void clearFields() {
        ObservableList<Node> textfieldsVB = textfieldContainer.getChildren();
        TextField[] tfa = new TextField[amountOfTextFields];

        for(int i=0; i < amountOfTextFields; i++) {
            tfa[i] = (TextField) textfieldsVB.get(i);
            tfa[i].setText(null);
        }
    }

    @FXML // Gets values from the textfields and sends it to be processed.
    protected void pickClick() throws IOException {

        boolean isFilled = true;

        // Loads next scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("result-page.fxml"), ResourceBundle.getBundle("DeciderBundle", Main.locale));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(String.valueOf(Main.class.getResource(Main.getThemePath())));
        scene.setFill(Color.TRANSPARENT);

        // Retrieves the TextFields and place them in an array.
        ObservableList<Node> textfieldsVB = textfieldContainer.getChildren();
        TextField[] tfa = new TextField[amountOfTextFields];

        for(int i=0; i < amountOfTextFields; i++) {
            tfa[i] = (TextField) textfieldsVB.get(i);
            if(tfa[i].getText().isEmpty() || tfa[i].getText().isBlank()) {
                AlertBox.display(resources.getString("alertboxTitle"), resources.getString("alertboxWarning"), resources);
                isFilled = false;
                break;
            }
        }

        // Only send the data further if all TextFields is non-blank.
        if(isFilled) {
            // Sends the array to the next scene's controller.
            ResultController rc = loader.getController();
            rc.receiveTextFields(tfa);

            // Switches to the next scene.
            Stage stage = (Stage) pickButton.getScene().getWindow();
//            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
        }
    }


    /* If goBack is pressed, this method is called and reinstates TextFields */
    public void reinstateTF(TextField[] tfas) {
        textfieldContainer.getChildren().clear();
        textfieldContainer.getChildren().addAll(tfas);
        amountOfTextFields = textfieldContainer.getChildren().size();
        if(amountOfTextFields == Main.MINFIELDS) {
            delButton.setDisable(true);
            firstLaunch = false;
        } else {
            delButton.setDisable(false);
        }
    }


    /* Buttons to close, minimize and maximize the program and open settings. */
    @FXML
    protected void openSettings() throws IOException {
        Stage settingsStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("settings.fxml"), ResourceBundle.getBundle("DeciderBundle", Main.locale));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(String.valueOf(Main.class.getResource(Main.getThemePath())));
        scene.setFill(Color.TRANSPARENT);

        // Centers settings stage.
        settingsStage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                settingsStage.setX((screenBounds.getWidth() - settingsStage.getWidth()) / 2);
                settingsStage.setY((screenBounds.getHeight() - settingsStage.getHeight()) / 2);

            }
        });

        settingsStage.initStyle(StageStyle.TRANSPARENT);
        settingsStage.setTitle("Decider");
        settingsStage.setScene(scene);
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.showAndWait();
    }

    @FXML
    protected void closeProgram() {
        Main.closeProgram();
    }

    @FXML
    protected void minimizeProgram() {
        Main.minimizeProgram();
    }


    /* Makes the toolbar draggable */
    @FXML
    protected void toolbarPressed(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        xOffset = event.getScreenX() - stage.getX();
        yOffset = event.getScreenY() - stage.getY();
        event.consume();
    }

    @FXML
    protected void toolbarDragged(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

}