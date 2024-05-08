package be.kuleuven.candycrush.view;

import be.kuleuven.candycrush.model.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.Map;

public class CandycrushView extends Region {

    private final CandycrushModel model;
    private final int widthCandy;
    private final int heigthCandy;

    public CandycrushView(CandycrushModel model) {
        this.model = model;
        widthCandy = 30;
        heigthCandy = 30;
    }

    public void update(){
        getChildren().clear();
        int i = 0;
        int j = 0;
        Map<Position, Candy> cells = model.getSpeelbord().getCells();

        for (Position position : cells.keySet()){
            Rectangle rectangle = new Rectangle(position.columnNr() * widthCandy, position.rowNr() * heigthCandy, widthCandy,heigthCandy);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.BLACK);

            Candy candy = cells.get(position);
            Node node = makeCandyShape(position, candy);

            getChildren().addAll(rectangle,node);
        }
    }

    public Position getPositionOfClicked(MouseEvent me){
        int row = (int) me.getY()/heigthCandy;
        int column = (int) me.getX()/widthCandy;
        int checkedRow = -1;
        int checkedColumn = -1;
        System.out.println(me.getX()+" - "+me.getY()+" - "+row+" - "+column);
        if (row < model.getBoardSize().columns() && column < model.getBoardSize().rows()){
            checkedRow = row;
            checkedColumn = column;
        }
        Position position = model.getPosition(checkedRow, checkedColumn);
        System.out.println(position);
        return position;
    }

    public void reset(){
        getChildren().clear();
    }

    public Node makeCandyShape(Position position, Candy candy){
        int x = position.columnNr()*widthCandy;
        int y = position.rowNr()*heigthCandy;
        Shape newCandy = null;
        if (candy instanceof NormalCandy){
            newCandy = new Circle(x+ (double) widthCandy /2, y+ (double) heigthCandy /2, (double) widthCandy /2);
            newCandy.setFill(candy.color());
        }
        else if (model.getSpeelbord().getCellAt(position).equals(null)){
            newCandy = null;
        }
        else{
            newCandy = new Rectangle(x,y,widthCandy,heigthCandy);
            newCandy.setFill(candy.color());
        }
        return newCandy;
    }

    public void makeBoard(){
        for (int k = 0; k < model.getBoardSize().columns(); k++) {
            for (int j = 0; j < model.getBoardSize().rows(); j++) {
                Position position = new Position(k,j,model.getBoardSize());
                Candy randomCandy = model.randomCandy();
                model.makePlayBoard(position, randomCandy);
            }
        }
        update();
    }
}