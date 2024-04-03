package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.CandycrushController;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CandycrushModelTests {

    /*
    @Test
    public void gegeven_wanneer_dan(){
        CandycrushModel model = new CandycrushModel("Arne");
        String result = model.getSpeler();
        assert (result.equals("Arne"));
    }
    */

    @Test
    public void gegevenNaam_alsDiejuistIs_isTrue() {
        CandycrushModel model = new CandycrushModel("Robin");
        String result = model.getSpeler();
        assert (result.equals("Robin"));
    }

    @Test
    public void gegevenRowEnColumn_PositionIsJuist_isTrue() {
        CandycrushModel model = new CandycrushModel("Robin");
        BoardSize boardsize = new BoardSize(5,5);
        Position position = new Position(2,2,boardsize);
        assert (position.equals(model.getPosition(2,2)));
    }

    @Test
    public void spellenGegenereerd_namenZijnHetzelfde_isTrue() {
        CandycrushModel model1 = new CandycrushModel("Robin");
        CandycrushModel model2 = new CandycrushModel("Robin");
        assert (model1.getSpeler().equals(model2.getSpeler()));
    }

    @Test
    public void startknop_isIngedrukt_isTrue() {
        CandycrushModel model = new CandycrushModel("Robin");
        model.start();
        assert (model.isGestart());
    }

    @Test
    public void rowEnColumn_PositionIsJuist_isTrue() {
        CandycrushModel model = new CandycrushModel("Robin");
        BoardSize boardsize = new BoardSize(5,5);
        Position position = new Position(4,4,boardsize);
        assert(model.getPosition(4, 4).equals(position));
    }

    @Test
    public void spelGestart_checkWidth_isJuist(){
        CandycrushModel model = new CandycrushModel("Robin");
        assert (model.getBoardSize().columns() == 5);
    }

    @Test
    public void spelGestart_scoreIsNul_isTrue(){
        CandycrushModel model = new CandycrushModel("Robin");
        assert (model.getScore() == 0);
    }

    @Test
    void indexGegeven_geeftRowColumn_isTrue(){
        BoardSize boardSize = new BoardSize(4,3);
        Position position = new Position(2,2, boardSize);
        assert (position.equals(Position.fromIndex(8,boardSize)));
    }

    @Test
    void indexGegeven_NeigboursJuist_isTrue(){
        BoardSize boardSize = new BoardSize(4,3);
        Position position = new Position(2,2, boardSize);
        
        ArrayList<Position> test = new ArrayList<>();
        Position position1 = new Position(1,2,boardSize);
        test.add(position1);
        Position position2 = new Position(3,2,boardSize);
        test.add(position2);
        Position position3 = new Position(2,1,boardSize);
        test.add(position3);
        Position position4 = new Position(3,1,boardSize);
        test.add(position4);
        Position position5 = new Position(1,1,boardSize);
        test.add(position5);
        assert (position.neighborPositions().equals(test));
    }
}
