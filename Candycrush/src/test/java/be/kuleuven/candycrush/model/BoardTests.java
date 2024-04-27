package be.kuleuven.candycrush.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    @Test
    void gestart_bordGevuld_isTrue() {
        BoardSize boardSize = new BoardSize(3, 3);
        Board<Integer> board = new Board<>(boardSize);

        board.fill(Position::getIndex);

        for (int i = 0; i < boardSize.rows(); i++) {
            for (int j = 0; j < boardSize.columns(); j++) {
                assertEquals(i * boardSize.columns() + j, board.getCellAt(new Position(i, j, boardSize)));
            }
        }
    }

    @Test
    void replaceCell_isReplaced_isTrue() {
        BoardSize boardSize = new BoardSize(3, 3);
        Board<Integer> board = new Board<>(boardSize);
        board.fill(Position::getIndex);

        Position position = new Position(1, 1, boardSize);
        int newValue = 99;
        board.replaceCellAt(position, newValue);

        assertEquals(newValue, board.getCellAt(position));
    }

    @Test
    void lijst_kopieerdJuist_isTrue() {
        BoardSize boardSize = new BoardSize(3, 3);
        Board<Integer> board1 = new Board<>(boardSize);
        board1.fill(Position::getIndex);
        Board<Integer> board2 = new Board<>(boardSize);

        board1.copyTo(board2);

        assertEquals(board1, board2);
    }
}
