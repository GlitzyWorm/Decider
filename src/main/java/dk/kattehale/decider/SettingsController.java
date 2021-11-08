package dk.kattehale.decider;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    // Used with toolbar
    private double xOffset;
    private double yOffset;

    @FXML ComboBox<String> comboLang;
    @FXML JFXSlider defSlider;
    @FXML ToggleSwitch themeSwitch;
    @FXML Button settingsClose;

    private String selectedLanguage;
    private String lang;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // Language combobox
        String[] langOptions = new String[]{resources.getString("settingsLangChoiceEn"), resources.getString("settingsLangChoiceDa")};
        lang = Main.locale.toString();

        selectedLanguage = switch (lang) {
            case "da" -> resources.getString("settingsLangChoiceDa");   // Danish
            case "en" -> resources.getString("settingsLangChoiceEn");   // English
            default -> "English";
        };

        comboLang.getItems().removeAll(comboLang.getItems());
        comboLang.getItems().addAll(langOptions);
        comboLang.getSelectionModel().select(selectedLanguage);

        // Textfield slider
        defSlider.setValue(Main.prefs.getInt(Main.Settings.DEFNUM.toString(), 2));

        // ToggleSwitch
        themeSwitch.setSelected(Main.prefs.get(Main.Settings.THEME.toString(), "Dark").equals("Dark"));
    }

    @FXML
    protected void languageChanged() {
        // Gets the selected language
        lang = switch (comboLang.getSelectionModel().getSelectedItem()) {
            case "English", "Engelsk" -> "en";
            case "Danish", "Dansk" -> "da";
            default -> "da";
        };
    }

    @FXML // Gets input from the different options and saves it.
    protected void saveButtonAction () {

        // Gets the selected amount of TextFields
        int defnum = (int) defSlider.getValue();

        // Gets the selected theme
        String theme;
        if(themeSwitch.isSelected()) {
            theme = "Dark";
        } else {
            theme = "light";
        }

        // Saves preferences
        Main.setPref(Main.Settings.DEFNUM, "-1", defnum);
        Main.setPref(Main.Settings.LANGUAGE, lang, -1);
        Main.setPref(Main.Settings.THEME, theme, -1);

        // Sends it back and restarts application
        Stage stage = (Stage) comboLang.getScene().getWindow();
        stage.close();
        Main.reloadStage();
    }


    // Button to close settings window
    @FXML
    protected void closeProgram() {
        Stage stage = (Stage) settingsClose.getScene().getWindow();
        stage.close();
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
