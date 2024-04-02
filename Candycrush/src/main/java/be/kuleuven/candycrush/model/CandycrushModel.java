package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.view.CandycrushView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class CandycrushModel {
    private final String speler;
    private final ArrayList<Candy> speelbord;
    private final BoardSize boardSize;
    private int score;
    private Position position;
    private boolean gestart = false;
    private CandycrushView view;

    public CandycrushModel(String speler) {
        this.speler = speler;
        speelbord = new ArrayList<>();
        boardSize = new BoardSize(5,5);
        position = new Position(boardSize.rows(), boardSize.columns(), boardSize);
        score = 0;
        this.view = new CandycrushView(this);
    }

    public void makePlayBoard(Position position, Candy candy){
        int index = position.getIndex();
        speelbord.add(index, candy);
    }

    public Candy randomCandy() {
        int randomNum = new Random().nextInt(10); // Kans van 1 op 10 voor elk type snoepje
        Candy candy = null;

        if (randomNum < 9) {
            int colorRandomNum = new Random().nextInt(4);
            switch (colorRandomNum) {
                case 0:
                    candy = new NormalCandy(Color.PINK);
                    break;
                case 1:
                    candy = new NormalCandy(Color.BLUE);
                    break;
                case 2:
                    candy = new NormalCandy(Color.GREEN);
                    break;
                case 3:
                    candy = new NormalCandy(Color.YELLOW);
                    break;
            }
        } else {
            int specialRandomNum = new Random().nextInt(3); // Verlaagd tot 3 voor de 20% kans op speciaal snoeptype
            switch (specialRandomNum) {
                case 0:
                    candy = new ChainReactor(Color.BLACK);
                    break;
                case 1:
                    candy = new DoublePointer(Color.GREY);
                    break;
                case 2:
                    candy = new LastRowBlower(Color.LIGHTGREY);
                    break;
                case 3:
                    candy = new TripleMove(Color.DARKGREY);
                    break;
            }
        }
        return candy;
    }



    public static void main(String[] args) {
        CandycrushModel model = new CandycrushModel("Robin");
        int i = 1;
        Iterator<Candy> iter = model.getSpeelbord().iterator();
        while (iter.hasNext()) {
            Candy candy = iter.next();
            System.out.print(candy);
            if (i % model.getBoardSize().columns() == 0) {
                System.out.print("\n");
                i = 1;
            }
            i++;
        }
        System.out.print("\n");
    }

    public String getSpeler() {
        return speler;
    }

    public ArrayList<Candy> getSpeelbord() {
        return speelbord;
    }

    public BoardSize getBoardSize(){
        return boardSize;
    }

    public int getScore() {
        return score;
    }

    public void candyWithPositionSelected(Position position) {
        Position invalid = new Position(-1,-1,boardSize);
        if (position != invalid) {
            ArrayList<Position> NeigborIds = getSameNeighbourPositions(position);
            for (Position i : NeigborIds) {
                Candy randomCandy = randomCandy();
                view.makeCandyShape(i, randomCandy);
                score++;
            }
        } else {
            System.out.println("model:candyWithPositionSelected:positionWasMinusOne");
        }
    }

    public Candy getCandy(Position position){
        int index = position.getIndex();
        return speelbord.get(index);
    }

    ArrayList<Position> getSameNeighbourPositions(Position position){
        ArrayList<Position> result = new ArrayList<>();
        ArrayList<Position> neighbors = position.neighborPositions();
        result.add(position);
        for (Position i : neighbors){
            if (getCandy(position).equals(getCandy(i)) && position.validPosition(i.rowNr(), i.columnNr())){
                result.add(i);
            }
        }
        return result;
    }

    public Position getPosition(int row, int column){
        return new Position(row, column, boardSize);
    }

    public void start(){
        gestart = true;
    }
    public boolean isGestart() {
        return gestart;
    }
}
