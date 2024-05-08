package be.kuleuven.candycrush.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    void validPosition_noError_isTrue() {
        // Test valid position within board size
        assertDoesNotThrow(() -> new Position(1, 1, new BoardSize(3, 3)));
    }

    @Test
    void positionGegeven_indexJuist_isTrue() {
        BoardSize size = new BoardSize(3, 3);
        Position position = new Position(1, 1, size);
        assert (position.getIndex() == 4);
    }

    @Test
    void indexGegeven_positionJuist_isTrue() {
        BoardSize size = new BoardSize(3, 3);

        assert(new Position(1, 1, size).equals(Position.fromIndex(4, size)));
    }

    @Test
    void testValidPosition_correct_isTrue() {
        Position position = new Position(1, 1, new BoardSize(3, 3));
        assertTrue(position.validPosition(1, 1));
    }

    @Test
    void testNeighborPositions_correct_isTrue() {
        BoardSize size = new BoardSize(3, 3);
        Position position = new Position(1, 1, size);
        assert(position.neighborPositions().size() == 8);
    }

    @Test
    void testIsLastColumn_correct_isTrue() {
        Position position = new Position(3, 3, new BoardSize(3, 3));
        assertTrue(position.isLastColumn());
    }

    @Test
    void testWalk_isCorrect_isTrue() {
        BoardSize boardSize = new BoardSize(4, 4);
        Position position = new Position(2, 3, boardSize);
        List<Position> left = position.walkLeft().toList();
        Position position2 = new Position(2, 2, boardSize);
        Position position3 = new Position(2, 1, boardSize);
        Position position4 = new Position(2, 0, boardSize);
        Position[] leftCheck = new Position[]{position2, position3, position4};
        assertArrayEquals(leftCheck, position.walkLeft().toArray());
    }
}
