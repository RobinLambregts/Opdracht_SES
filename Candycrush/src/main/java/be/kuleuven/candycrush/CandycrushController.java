package be.kuleuven.candycrush;

import java.net.URL;
import java.util.ResourceBundle;

import be.kuleuven.candycrush.model.CandycrushModel;
import be.kuleuven.candycrush.view.CandycrushView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CandycrushController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Label;

    @FXML
    private Button btn;

    @FXML
    private AnchorPane paneel;

    @FXML
    private AnchorPane speelbord;

    @FXML
    private TextField textInput;

    @FXML
    private Label scoreInput;

    @FXML
    private Button resetknop;

    private CandycrushModel model;
    private CandycrushView view;

    @FXML
    void initialize() {
        assert Label != null : "fx:id=\"Label\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert btn != null : "fx:id=\"btn\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert paneel != null : "fx:id=\"paneel\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert speelbord != null : "fx:id=\"speelbord\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert scoreInput != null : "fx:id=\"scoreInput\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        model = new CandycrushModel("Test");
        view = new CandycrushView(model);
        view.setOnMouseClicked(this::onCandyClicked);
        btn.setOnAction(e -> gestart());
        resetknop.setOnAction(e -> reset());
        resetknop.setDisable(true);
    }

    public void update(){
        String score = String.valueOf(model.getScore());
        scoreInput.setText(score);
        view.update();
    }

    public void onCandyClicked(MouseEvent me){
        int candyIndex = view.getIndexOfClicked(me);
        model.candyWithIndexSelected(candyIndex);
        update();
    }

    public void gestart(){
        model.start();
        btn.setDisable(true);
        resetknop.setDisable(false);
        speelbord.getChildren().add(view);
        if (textInput.getText().equals("Robin")){
            textInput.setText("Hello Robin");
            paneel.setStyle("-fx-background-color: red");
            speelbord.setStyle("-fx-background-color: white");
        }
        else{
            textInput.setText("welcome guest");
            paneel.setStyle("-fx-background-color: white");
            speelbord.setStyle("-fx-background-color: red");
        }
    }

    public void reset(){
        view.reset();
        paneel.setStyle("-fx-background-color: white");
        speelbord.setStyle("-fx-background-color: white");
        btn.setDisable(false);
        resetknop.setDisable(true);
        textInput.setText("");
        scoreInput.setText("");
        this.initialize();
    }
}
