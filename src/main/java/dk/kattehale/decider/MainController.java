package dk.kattehale.decider;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainController {

    // @FXML private Label welcomeText;

    @FXML private Button addButton;
    @FXML private Button delButton;
    @FXML private VBox textfieldContainer;

    /*@FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/

    @FXML
    protected void addTextField() {
        // Gets the amount of TextFields in VBox.
        int index = textfieldContainer.getChildren().size() - 1;

        // If the index is 1 less than MAXFIELDS, add a last TextField and disable button.
        // Else add a new TextField above the 'add'-button.
        if(index == Main.MAXFIELDS - 1) {
            addButton.setDisable(true);
            //addButton.setVisible(false);

            delButton.setDisable(false);
            TextField newField = new TextField();
            textfieldContainer.getChildren().add(index,newField);
        } else {
            System.out.println(index);
            delButton.setDisable(false);
            TextField newField = new TextField();
            textfieldContainer.getChildren().add(index,newField);
        }


    }

    @FXML
    protected void delTextField() {
        // Gets the amount of TextFields in VBox.
        int index = textfieldContainer.getChildren().size() - 2;

        // If index is equal to MINFIELDS, remove the last TextField and disable button.
        // Else remove a TextField
        if(index == Main.MINFIELDS) {
            delButton.setDisable(true);
            addButton.setDisable(false);
            textfieldContainer.getChildren().remove(index);
            //addButton.setVisible(false);
        } else {
            System.out.println(index);
            addButton.setDisable(false);
            textfieldContainer.getChildren().remove(index);
        }
    }
}