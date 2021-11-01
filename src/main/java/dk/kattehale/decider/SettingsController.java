package dk.kattehale.decider;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    private double xOffset;
    private double yOffset;

    @FXML
    ComboBox<String> comboLang;

    @FXML Button settingsClose;
    @FXML Button settingsMax;
    @FXML Button settingsMin;

    String[] langOptions;
    String selectedLanguage;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        langOptions = new String[]{resources.getString("settingsLangChoiceEn"), resources.getString("settingsLangChoiceDa")};

        selectedLanguage = switch (Main.locale.toString()) {
            case "da" -> resources.getString("settingsLangChoiceDa"); // Danish
            default -> resources.getString("settingsLangChoiceEn");   // English
        };

        comboLang.getItems().removeAll(comboLang.getItems());
        comboLang.getItems().addAll(langOptions);
        comboLang.getSelectionModel().select(selectedLanguage);
    }

    @FXML // Gets input from the different options and saves it.
    protected void saveButtonAction () {

        // Gets the selected language
        String lang = switch (comboLang.getSelectionModel().getSelectedItem()) {
            case "English" -> "en";
            case "Danish" -> "da";
            default -> "en";
        };

        selectedLanguage = comboLang.getSelectionModel().getSelectedItem();

        // Gets the selected amount of TextFields

        // Gets the selected theme

        // Sends it back and restarts application
        Stage stage = (Stage) comboLang.getScene().getWindow();
        stage.close();
        Main.setPref(Main.Settings.LANGUAGE, lang);

    }


    /* Buttons to close, minimize and maximize the program. */
    @FXML
    protected void closeProgram() {
        Stage stage = (Stage) settingsClose.getScene().getWindow();
        stage.close();
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
