package be.kuleuven.candycrush.model;

public class Swap {
    Position pos1;
    Position pos2;

    Swap(Position pos1, Position pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    @Override
    public String toString() {
        return "[" + pos1.rowNr() + "," + pos1.columnNr() + "]" + "<->" + "[" + pos2.rowNr() + "," + pos2.columnNr() + "]";
    }
}
