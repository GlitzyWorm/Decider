package dk.kattehale.decider;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class FieldController {

    private double xOffset;
    private double yOffset;
    private int amountOfTextFields = Main.MINFIELDS;

    /* Toolbar and its buttons */
    @FXML private ToolBar toolBar;
    @FXML private Button closeButton;
    @FXML private Button resizeButton;
    @FXML private Button minButton;

    /* Buttons and textfieldContainer for handling adding and deleting TextFields */
    @FXML private Button addButton;
    @FXML private Button delButton;
    @FXML private VBox textfieldContainer;

    /* Pick button */
    @FXML private Button pickButton;



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
            newField.setPromptText("Indtast valgmulighed");
            textfieldContainer.getChildren().add(index,newField);
        } else {
            amountOfTextFields++;
            delButton.setDisable(false);
            TextField newField = new TextField();
            newField.setPromptText("Indtast valgmulighed");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("result-page.fxml"));
        Parent root = loader.load();

        // Retrieves the TextFields and place them in an array.
        ObservableList<Node> textfieldsVB = textfieldContainer.getChildren();
        TextField[] tfa = new TextField[amountOfTextFields];

        for(int i=0; i < amountOfTextFields; i++) {
            tfa[i] = (TextField) textfieldsVB.get(i);
            if(tfa[i].getText().isBlank()) {
                AlertBox.display("Advarsel!", "Alle tekstfelter skal være udfyldt!");
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
            stage.setScene(new Scene(root));
        }
    }


    /* If goBack is pressed, this method is called and reinstates TextFields */
    public void reinstateTF(TextField[] tfas) {
        textfieldContainer.getChildren().clear();
        textfieldContainer.getChildren().addAll(tfas);
        amountOfTextFields = textfieldContainer.getChildren().size();
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