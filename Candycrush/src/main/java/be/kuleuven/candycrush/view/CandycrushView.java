package be.kuleuven.candycrush.view;

import be.kuleuven.candycrush.model.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import java.util.Iterator;

public class CandycrushView extends Region {
    private CandycrushModel model;
    private int widthCandy;
    private int heigthCandy;

    public CandycrushView(CandycrushModel model) {
        this.model = model;
        widthCandy = 30;
        heigthCandy = 30;
        update();
    }

    public void update(){
        getChildren().clear();
        int i = 0;
        int j = 0;
        Iterator<Candy> iter = model.getSpeelbord().iterator();
        while(iter.hasNext()) {
            Candy candy = iter.next();
            Position position = model.getPosition(j, i);
            makeCandyShape(position, candy);
            if (i == model.getBoardSize().columns() - 1) {
                i = 0;
                j++;
            } else {
                i++;
            }
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

    public void makeCandyShape(Position position, Candy candy){
        int x = position.columnNr()*widthCandy;
        int y = position.rowNr()*heigthCandy;
        Shape newCandy = null;
        if (candy instanceof NormalCandy){
            newCandy = new Circle(x+widthCandy/2, y+heigthCandy/2, widthCandy/2);
            newCandy.setFill(candy.color());
        }
        else{
            newCandy = new Rectangle(x,y,widthCandy,heigthCandy);
            newCandy.setFill(candy.color());
        }
        getChildren().add(newCandy);
    }

    public void makeBoard(){
        for (int k = 0; k < model.getBoardSize().rows(); k++) {
            for (int j = 0; j < model.getBoardSize().columns(); j++) {
                BoardSize boardSize = new BoardSize(model.getBoardSize().rows(), model.getBoardSize().columns());
                Position position = new Position(k,j,boardSize);
                Candy randomCandy = model.randomCandy();
                makeCandyShape(position, randomCandy);
                model.makePlayBoard(position, randomCandy);
            }
        }
    }
}
