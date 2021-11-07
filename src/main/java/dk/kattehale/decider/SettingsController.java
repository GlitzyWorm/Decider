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

    private double xOffset;
    private double yOffset;

    @FXML
    ComboBox<String> comboLang;

    @FXML
    JFXSlider defSlider;

    @FXML
    ToggleSwitch themeSwitch;

    @FXML Button settingsClose;

    String[] langOptions;
    String selectedLanguage;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // Language combobox
        langOptions = new String[]{resources.getString("settingsLangChoiceEn"), resources.getString("settingsLangChoiceDa")};

        selectedLanguage = switch (Main.locale.toString()) {
            case "da" -> resources.getString("settingsLangChoiceDa"); // Danish
            default -> resources.getString("settingsLangChoiceEn");   // English
        };

        comboLang.getItems().removeAll(comboLang.getItems());
        comboLang.getItems().addAll(langOptions);
        comboLang.getSelectionModel().select(selectedLanguage);

        // Textfield slider
        defSlider.setValue(Main.prefs.getInt(Main.Settings.DEFNUM.toString(), 2));

        // ToggleSwitch
        if(Main.prefs.get(Main.Settings.THEME.toString(), "Dark").equals("Dark")) {
            themeSwitch.setSelected(true);
        } else {
            themeSwitch.setSelected(false);
        }

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
        int defnum = (int) defSlider.getValue();

        // Gets the selected theme
        String theme;
        if(themeSwitch.isSelected()) {
            theme = "Dark";
        } else {
            theme = "light";
        }


        // Sends it back and restarts application
        Stage stage = (Stage) comboLang.getScene().getWindow();
        stage.close();
        Main.setPref(Main.Settings.DEFNUM, "-1", defnum);
        Main.setPref(Main.Settings.LANGUAGE, lang, -1);
        Main.setPref(Main.Settings.THEME, theme, -1);

        Main.reloadStage();


    }


    /* Buttons to close, minimize and maximize the program. */
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
