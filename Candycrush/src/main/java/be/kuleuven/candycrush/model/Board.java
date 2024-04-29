package be.kuleuven.candycrush.model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Board<E> {

    private final Map<Position, E> cells;
    private final Map<E, Position> positions;
    private final BoardSize boardSize;

    public Board(BoardSize boardSize) {
        this.boardSize = boardSize;
        cells = new ConcurrentHashMap<>();
        positions = new ConcurrentHashMap<>();
    }

    public BoardSize getBoardSize() {
        return boardSize;
    }

    public Map<Position, E> getCells() {
        return Collections.unmodifiableMap(cells);
    }

    public Map<E, Position> getPositions() {
        return Collections.unmodifiableMap(positions);
    }

    public synchronized E getCellAt(Position position) {
        return cells.get(position);
    }

    public synchronized Map<E, Position> getPositionsOfElement(E object) {
        Map<E, Position> result = new HashMap<>();
        for (Map.Entry<E, Position> entry : positions.entrySet()) {
            if (entry.getKey().equals(object)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return Collections.unmodifiableMap(result);
    }

    public synchronized void replaceCellAt(Position position, E newCell) {
        cells.put(position, newCell);
        positions.put(newCell, position);
    }

    public synchronized void fill(Function<Position, E> cellCreator) {
        for (int i = 0; i < boardSize.columns() * boardSize.rows(); i++) {
            Position position = Position.fromIndex(i, boardSize);
            E cell = cellCreator.apply(position);
            replaceCellAt(position, cell);
        }
    }

    public synchronized void copyTo(Board<E> otherBoard) {
        if (!(boardSize.equals(otherBoard.getBoardSize()))) {
            throw new RuntimeException("Boards not same size");
        }
        for (Position position : cells.keySet()) {
            otherBoard.replaceCellAt(position, this.cells.get(position));
        }
    }

    @Override
    public synchronized boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board<?> board = (Board<?>) o;
        return Objects.equals(cells, board.cells) && Objects.equals(boardSize, board.boardSize);
    }
}
