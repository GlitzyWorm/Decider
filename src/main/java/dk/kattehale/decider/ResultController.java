package dk.kattehale.decider;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class ResultController {

    private double xOffset;
    private double yOffset;

    private String[] choices;
    private TextField[] tfa;

    /* Toolbar and its buttons */
    @FXML private ToolBar toolBar;
    @FXML private Button closeButton;
    @FXML private Button resizeButton;
    @FXML private Button minButton;

    /* Labels */
    @FXML private Label chosenLabel;

    /* Bottom buttons */
    @FXML private Button menuButton;
    @FXML private Button changeButton;
    @FXML private Button redoButton;
    @FXML private Button quitButton;




    // Method to receive array of TextFields
    public void receiveTextFields(TextField[] tfa) {
        this.choices = new String[tfa.length];
        this.tfa = tfa;

        for(int i = 0; i < choices.length; i++) {
            choices[i] = tfa[i].getText();
        }

        pickRandom();
    }

    // Picks a random choice and sets chosenLabels text.
    private void pickRandom() {
        Random rng = new Random();
        chosenLabel.setText(choices[rng.nextInt(choices.length)]);
    }


    @FXML // Changes scene to main screen (for now it goes to field-pick)
    protected void goToMainMenu() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("DeciderBundle");;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("field-pick.fxml")), bundle);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) menuButton.getScene().getWindow();
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("css/Decider.css")));
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
    }

    @FXML // Go back to field-pick and retain input
    protected void goBack() throws IOException {
        // Loads next scene
        ResourceBundle bundle = ResourceBundle.getBundle("DeciderBundle");;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("field-pick.fxml")), bundle);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("css/Decider.css")));
        scene.setFill(Color.TRANSPARENT);

        // Sends TextFields back to FieldController
        FieldController fc = loader.getController();
        fc.reinstateTF(tfa);

        // Switches to the next scene.
        Stage stage = (Stage) changeButton.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML // Repeats the selection and updates the result (maybe fires the animation again?)
    protected void repeatSelection() {
        pickRandom();
    }

    // quitButton action is sent to closeProgram().




    /* Buttons to close, minimize and maximize the program */
    @FXML
    protected void closeProgram() {
        Main.closeProgram();
    }

    @FXML
    protected void minimizeProgram() {
        Main.minimizeProgram();
    }

    @FXML
    protected void maximizeProgram() {
        Main.maximizeProgram();
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
