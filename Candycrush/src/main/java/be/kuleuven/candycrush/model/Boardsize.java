package be.kuleuven.candycrush.model;

import java.util.ArrayList;
import java.util.Collection;

public record BoardSize(int rows, int columns) {
    public BoardSize {
        if (rows <= 0) throw new IllegalArgumentException("Rows must be larger than 0");
        if (columns <= 0) throw new IllegalArgumentException("Columns must be larger than 0");
    }

    public Collection<Position> positions(){
        ArrayList<Position> result = new ArrayList<>();
        BoardSize size = new BoardSize(rows, columns);
        for(int i = 0; i < rows*columns; i++){
            result.add(Position.fromIndex(i, size));
        }
        return result;
    }
}
