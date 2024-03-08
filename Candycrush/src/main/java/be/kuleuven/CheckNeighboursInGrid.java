package be.kuleuven;

import java.util.ArrayList;
public class CheckNeighboursInGrid {

    /*  public static void main(String[] args) {
        ArrayList<Integer> grid = new ArrayList<>();
        grid.add(0);
        grid.add(0);
        grid.add(1);
        grid.add(0);
        grid.add(1);
        grid.add(1);
        grid.add(0);
        grid.add(2);
        grid.add(2);
        grid.add(1);
        grid.add(1);
        grid.add(3);
        grid.add(0);
        grid.add(1);
        grid.add(1);
        grid.add(1);

        getSameNeighboursIds(grid, 4, 4, 5);
    }*/
    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid, int width, int height, int indexToCheck) {

        ArrayList<Integer> omgezetteLijst = new ArrayList<>();
        for (Integer num : grid){
            omgezetteLijst.add(num);
        }
        ArrayList<Integer> result = new ArrayList<>();

        //  for (int i = 0; i < height; i++) {
            //  for (int j = 0; j < width; j++) {
                // result.add(00000000000000000);
                //  int currentIndex = i * width + j;
                int currentIndex = indexToCheck;
                int indexLinks = currentIndex - 1;
                int indexRechts = currentIndex + 1;
                int indexBoven = currentIndex - width;
                int indexOnder = currentIndex + width;

                int currentgetal = omgezetteLijst.get(currentIndex);
                int getalBoven = omgezetteLijst.get(indexBoven);
                int getalOnder = omgezetteLijst.get(indexOnder);
                int getalLinks = omgezetteLijst.get(indexLinks);
                int getalRechts = omgezetteLijst.get(indexRechts);

                if (indexBoven >= 0 && currentgetal == getalBoven) {
                    result.add(indexBoven);
                }

                if (indexOnder < width * height && currentgetal == getalOnder) {
                    result.add(indexOnder);
                }

                if (indexLinks % width != width - 1 && currentgetal == getalLinks) {
                    result.add(indexLinks);
                }

                if (indexRechts % width != 0 && currentgetal == getalRechts) {
                    result.add(indexRechts);
                }
            //}
        //}
        return result;
    }
}