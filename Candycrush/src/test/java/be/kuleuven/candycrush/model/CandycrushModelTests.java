package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.CandycrushController;
import org.junit.jupiter.api.Test;

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
    public void score_wanneerGeklikt_neemtToe() {
        CandycrushModel model = new CandycrushModel("Robin");
        int score = model.getScore();
        model.candyWithIndexSelected(6);
        assert (score < model.getScore());
    }

    @Test
    public void gegevenRowEnColumn_indexIsJuist_isTrue() {
        CandycrushModel model = new CandycrushModel("Robin");
        int width = model.getWidth();
        int row = 2;
        int column = 3;
        int index = model.getIndexFromRowColumn(row, column);
        assert (index == row * width + column);
    }

    @Test
    public void spellenGegenereerd_namenZijnNietHetzelfde_isTrue() {
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
    public void outOfBoundsIndex_geenScore_isTrue() {
        CandycrushModel model = new CandycrushModel("Player");
        model.candyWithIndexSelected(-1); // Invalid index
        assert (model.getScore() == 0); // Score should not change for an invalid index
    }

    @Test
    public void rowEnColumn_indexIsJuist_isTrue() {
        CandycrushModel model = new CandycrushModel("Player");
        assert(model.getIndexFromRowColumn(4, 4) == 24);
    }

    @Test
    public void spelGestart_checkWidth_isJuist(){
        CandycrushModel model = new CandycrushModel("Robin");
        assert (model.getWidth() == 5);
    }

    @Test
    public void spelGestart_scoreIsNul_isTrue(){
        CandycrushModel model = new CandycrushModel("Robin");
        assert (model.getScore() == 0);
    }

    @Test
    void validIndex_geeftPunten_isTrue() {
        CandycrushModel model = new CandycrushModel("Player");
        model.start();
        model.candyWithIndexSelected(7);
        assert (model.getScore() >= 1);
    }
}
