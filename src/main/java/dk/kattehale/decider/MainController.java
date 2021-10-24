package dk.kattehale.decider;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {

    private double xOffset;
    private double yOffset;

    @FXML private ToolBar toolBar;
    @FXML private Button closeButton;
    @FXML private Button resizeButton;
    @FXML private Button minButton;

    @FXML private Button addButton;
    @FXML private Button delButton;
    @FXML private VBox textfieldContainer;

    /*@FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/


    // Add and delete TextFields.
    @FXML
    protected void addTextField() {
        // Gets the amount of TextFields in VBox.
        int index = textfieldContainer.getChildren().size() - 1;

        // If the index is 1 less than MAXFIELDS, add a last TextField and disable button.
        // Else add a new TextField above the 'add'-button.
        if(index == Main.MAXFIELDS - 1) {
            addButton.setDisable(true);
            delButton.setDisable(false);
            TextField newField = new TextField();
            textfieldContainer.getChildren().add(index,newField);
        } else {
            delButton.setDisable(false);
            TextField newField = new TextField();
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
            delButton.setDisable(true);
            addButton.setDisable(false);
            textfieldContainer.getChildren().remove(index);
        } else {
            addButton.setDisable(false);
            textfieldContainer.getChildren().remove(index);
        }
    }


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