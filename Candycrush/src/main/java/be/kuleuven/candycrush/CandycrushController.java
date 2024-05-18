package be.kuleuven.candycrush;

import java.net.URL;
import java.util.ResourceBundle;

import be.kuleuven.candycrush.model.*;
import be.kuleuven.candycrush.view.CandycrushView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javax.lang.model.element.PackageElement;

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
        // model = new CandycrushModel("Robin", 5, 5);
        CandycrushModel model1 = createBoardFromString("""
   @@o#
   o*#o
   @@**
   *#@@""");

//        CandycrushModel model2 = createBoardFromString("""
//   #oo##
//   #@o@@
//   *##o@
//   @@*@o
//   **#*o""");

//        CandycrushModel model3 = createBoardFromString("""
//   #@#oo@
//   @**@**
//   o##@#o
//   @#oo#@
//   @*@**@
//   *#@##*""");
        model = model1;
        view = new CandycrushView(model);
        view.setOnMouseClicked(this::onCandyClicked);
        btn.setOnAction(e -> gestart());
        resetknop.setOnAction(e -> reset());
        resetknop.setDisable(true);
    }

    public void update() {
        String score = String.valueOf(model.getScore());
        scoreInput.setText(score);
        view.update();
    }

    public void onCandyClicked(MouseEvent me) {
        Position candyPosition = view.getPositionOfClicked(me);
        model.candyWithPositionSelected(candyPosition);
        update();
    }

    public void gestart() {
        model.start();
        view.makeBoard();
        btn.setDisable(true);
        resetknop.setDisable(false);
        speelbord.getChildren().add(view);
        if (textInput.getText().equals("Robin")) {
            textInput.setText("Hello Robin");
            paneel.setStyle("-fx-background-color: red");
            speelbord.setStyle("-fx-background-color: white");
        } else {
            textInput.setText("welcome guest");
            paneel.setStyle("-fx-background-color: blue");
            speelbord.setStyle("-fx-background-color: white");
        }
    }

    public void reset() {
        view.reset();
        paneel.setStyle("-fx-background-color: white");
        speelbord.setStyle("-fx-background-color: white");
        btn.setDisable(false);
        resetknop.setDisable(true);
        textInput.setText("");
        scoreInput.setText("");
        this.initialize();
    }

    public static CandycrushModel createBoardFromString(String configuration) {
        var lines = configuration.toLowerCase().lines().toList();
        BoardSize size = new BoardSize(lines.size(), lines.getFirst().length());
        var model = new CandycrushModel("Robin", size.rows(), size.columns()); // deze moet je zelf voorzien
        for (int row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                model.makePlayBoard(new Position(row, col, size), characterToCandy(line.charAt(col)));
            }
        }
        System.out.println("---" + model.getSpeelbord().getCells());
        return model;
    }

    private static Candy characterToCandy(char c) {
        return switch(c) {
            case '.' -> null;
            case 'o' -> new NormalCandy(Color.BLUE);
            case '*' -> new NormalCandy(Color.RED);
            case '#' -> new NormalCandy(Color.GREEN);
            case '@' -> new NormalCandy(Color.YELLOW);
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
}