package sample.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //-------------------------------FXML Instance Variable-----------------
    @FXML private ImageView close;
    @FXML private ImageView maximize;
    @FXML private ImageView minimize;
    @FXML private JFXTextField frame_textfield;
    @FXML private JFXTextField referenceString_textfield;
    @FXML private TextArea output_textarea;
    @FXML private Label hits;
    @FXML private Label hit_ratio;
    @FXML private Label faults;
    @FXML private Pane actionBar;

    //--------------------------Global Variables--------------------------
    private int frameValue;
    private int referenceStringValue;
    private List<Integer> referenceStringList =  new ArrayList<>();
    private FIFO fifo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        frame_textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            frameValue = attachedListener(frame_textfield, newValue);
        });

        referenceString_textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            referenceStringValue = attachedListener(referenceString_textfield, newValue);
        });


    }

    @FXML
    void frameKeyPressed(KeyEvent event) {
    }

    @FXML
    void referenceStringKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            referenceStringList.add(referenceStringValue);
            referenceString_textfield.clear();
        }

        fifo = new FIFO(frameValue,referenceStringList);

        output_textarea.setText(fifo.calculate());

    }

    @FXML
    void enterButtonOnAction(ActionEvent event) {
        referenceStringList.add(referenceStringValue);
        referenceString_textfield.clear();

        fifo = new FIFO(frameValue,referenceStringList);

        output_textarea.setText(fifo.calculate());

    }

    @FXML
    void resetButtonOnAction(ActionEvent event) {
        output_textarea.clear();
        referenceStringList.clear();
        frame_textfield.clear();
        frameValue = 0;
    }

    @FXML
    void actionBarMouseDragged(MouseEvent event) {

    }

    @FXML
    void actionBarMousePressed(MouseEvent event) {

    }

    @FXML
    void closeButton(MouseEvent event) {
        Platform.exit();
    }

    //------------------------------------private methods------------------------------------
    private int attachedListener(JFXTextField textField,String newValue) throws NumberFormatException{
        if (!newValue.matches("\\d*")) {
            textField.setText(newValue.replaceAll("[^\\d]", ""));
        }
        String textFieldText = textField.getText();
        try{
            return Integer.parseInt(textFieldText);
        } catch (NumberFormatException e){
            return 0;
        }
    }
}
