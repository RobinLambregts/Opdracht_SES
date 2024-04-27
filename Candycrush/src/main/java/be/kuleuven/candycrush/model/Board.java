package be.kuleuven.candycrush.model;

import java.util.*;
import java.util.function.Function;
public class Board<E> {


    private final Map<Position, E> cells;
    private final Map<E, Position> positions;
    private final BoardSize boardSize;

    public Board(BoardSize boardSize) {
        this.boardSize = boardSize;
        cells = new HashMap<>();
        positions = new HashMap<>();
    }
    public BoardSize getBoardSize() {
        return boardSize;
    }

    public Map<Position, E> getCells(){
        return cells;
    }

    public Map<E, Position> getPositions(){
        return positions;
    }

    public E getCellAt(Position position){
        return cells.get(position);
    }

    public Map<E, Position> getPositionsOfElement(E object){
        Map<E, Position> positions = getPositions();
        Map<E, Position> result = new HashMap<>();
        for (int i = 0; i < positions.size(); i++){
            if (positions.get(i).equals(object)){
                Position position = positions.get(i);
                result.put(getCellAt(position), position);
            }
        }
        return Collections.unmodifiableMap(result);
    }

    public void replaceCellAt(Position position, E newCell){
        cells.put(position, newCell);
        positions.put(newCell, position);
    }

    public void fill(Function<Position, E> cellCreator){
        for (int i = 0; i < boardSize.columns() * boardSize.rows(); i++) {
            Position position = Position.fromIndex(i, boardSize);
            E cell = cellCreator.apply(position);

            replaceCellAt(position, cell);
        }
    }

    public void copyTo(Board<E> otherBoard){
        if(!(boardSize.equals(otherBoard.getBoardSize()))){
            throw new RuntimeException("Boards not same size");
        }
        for (Position position : cells.keySet()){
            otherBoard.replaceCellAt(position, this.cells.get(position));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board<?> board = (Board<?>) o;
        return Objects.equals(cells, board.cells) && Objects.equals(boardSize, board.boardSize);
    }
}