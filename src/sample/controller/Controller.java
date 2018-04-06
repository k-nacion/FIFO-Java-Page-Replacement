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
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.view.Main;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //TODO: add onDragListener to Custom Action bar to enable the platform draggable.
    //TODO: Function for fault rate Ma'am's formula.
    //TODO:

    //-------------------------------FXML Instance Variable-----------------
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
    private double xOffset = 0;
    private double yOffset = 0;


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
            referenceListener();
        }

    }

    @FXML
    void enterButtonOnAction(ActionEvent event) {
        referenceListener();
    }

    @FXML
    void resetButtonOnAction(ActionEvent event) {
        output_textarea.clear();
        referenceStringList.clear();
        frame_textfield.clear();
        frameValue = 0;
        hit_ratio.setText("0");
        hits.setText("0");
        faults.setText("0");
    }

    @FXML
    void actionBarMouseDragged(MouseEvent event) {
        Window window = actionBar.getScene().getWindow();
        window.setX(event.getScreenX() - xOffset);
        window.setY(event.getScreenY() - yOffset);
        System.out.println("actionBarMouseDragged method:");
        System.out.println("X = " + (event.getScreenX() - xOffset));
        System.out.println("Y = " + (event.getScreenY() - yOffset));
    }

    @FXML
    void actionBarMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();

        System.out.println("actionBarMousePressed method:");
        System.out.println("X = " + xOffset);
        System.out.println("X = " + yOffset);
    }

    @FXML
    void closeButton(MouseEvent event) {
        Platform.exit();
    }

    //------------------------------------private methods------------------------------------

    private void referenceListener() {

        referenceStringList.add(referenceStringValue);
        referenceString_textfield.clear();

        fifo = new FIFO(frameValue,referenceStringList);

        output_textarea.setText(fifo.calculate());
        hits.setText(fifo.getHit().toString());
        hit_ratio.setText(fifo.getHitRatio());
        faults.setText(fifo.getFault().toString());
        output_textarea.setScrollLeft(Double.MAX_VALUE);
    }
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

    //------------------------------------------------------------Public methods---------------------------------------------

}
