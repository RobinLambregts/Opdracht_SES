package be.kuleuven;

import java.util.ArrayList;

public class CheckNeighboursInGrid {

    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid, int width, int height, int indexToCheck) {

        ArrayList<Integer> convertedList = new ArrayList<>();
        for (Integer num : grid) {
            convertedList.add(num);
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(indexToCheck);

        int currentIndex = indexToCheck;
        int row = currentIndex / width;
        int col = currentIndex % width;

        int[][] offsets = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1},  {1, 0},  {1, 1}
        };

        for (int[] offset : offsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];

            if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width) {
                int newIndex = newRow * width + newCol;
                int currentNum = convertedList.get(currentIndex);
                int neighborNum = convertedList.get(newIndex);

                if (currentNum == neighborNum) {
                    result.add(newIndex);
                }
            }
        }

        return result;
    }
}
