package cz.cvut.fel.logic;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Tools {

    //Constants
    private final String strFontArial = "Arial";
    private final String srtBackgroundColorBlack = "-fx-border-color: #000000;";
    private final String strTextFillBlack = "-fx-text-fill: #000000;";
    private final String strBorderRadiusOpx = "-fx-border-radius: 0px;";
    private final String strBackgroundRadius0px = "-fx-background-radius: 0px;";

    //Labels
    public Label createLabel(String text, double width, double height, boolean visible) {
        Label label = new Label(text);
        label.setVisible(visible);
        label.setPrefSize(width, height);
        label.setStyle(
                "-fx-background-color: #FF9933; " +
                        "-fx-alignment: center; " +
                        "-fx-text-alignment: center; " +
                        "-fx-wrap-text: true;");
        label.setFont(Font.font(strFontArial, 18));
        return label;
    }

    //Create Button
    public Button createButton(String text, boolean disable, double buttonWidth, double buttonHigh) {
        Button button = new Button(text);
        button.setDisable(disable);
        button.setPrefSize(buttonWidth, buttonHigh);
        button.setFont(new Font(strFontArial, 16));
        standardButton(button);
        return button;
    }

    public Button createLongButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(200, 30);
        button.setFont(new Font(strFontArial, 20));
        standardButton(button);
        return button;
    }

    public Button createShortButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(150, 30);
        button.setFont(new Font(strFontArial, 20));
        standardButton(button);
        return button;
    }

    //Button look
    public Button selectedButton(Button button){
        button.isFocused();
        button.setDisable(false);
        button.setStyle(
                "-fx-background-color: #FF9933;" +
                        srtBackgroundColorBlack +
                        "-fx-border-width: 2px;" +
                        strBorderRadiusOpx +
                        strTextFillBlack +
                        "-fx-background-radius: 0px;"
        );
        return button;
    }

    public Button standardButton(Button button) {
        button.setFocusTraversable(false);
        button.setDisable(false);
        button.setStyle(
                "-fx-background-color: #FF9933;" +
                        srtBackgroundColorBlack +
                        "-fx-border-width: 1px;" +
                        strBorderRadiusOpx +
                        "-fx-text-fill: #000000;" +
                        strBackgroundRadius0px
        );
        return button;
    }

    public void disabledButton(Button button){
        button.setDisable(true);
        button.setStyle(
                "-fx-background-color: #FFB366;" +
                        srtBackgroundColorBlack +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 0px;" +
                        strTextFillBlack +
                        strBackgroundRadius0px

        );
    }

}
