package be.kuleuven.candycrush.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class BoardSizeTests {
    @Test
    public void invalidSizeGegeven_Error_isTrue(){
        assertThrows(IllegalArgumentException.class, () -> new BoardSize(0, 5));
    }

    @Test
    void testPositions_Correct_isTrue() {
        BoardSize boardSize = new BoardSize(3, 3);
        Iterable<Position> positions = boardSize.positions();

        int count = 0;
        for (Position position : positions) {
            assertNotNull(position);
            count++;
        }

        assert (count == 9);
    }
}
