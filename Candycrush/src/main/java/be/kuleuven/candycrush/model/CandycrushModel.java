package be.kuleuven.candycrush.model;

import javafx.scene.paint.Color;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CandycrushModel {
    private final String speler;
    private final Board<Candy> speelbord;
    public static BoardSize boardSize;
    private int score;
    private boolean gestart = false;

    public CandycrushModel(String speler) {
        this.speler = speler;
        boardSize = new BoardSize(5,5);
        speelbord = new Board<>(boardSize);
        score = 0;

        Function<Position, Candy> candyCreator = position -> randomCandy();
        speelbord.fill(candyCreator);
    }

    public void makePlayBoard(Position position, Candy candy){
        speelbord.replaceCellAt(position, candy);
    }

    public Candy randomCandy() {
        int randomNum = new Random().nextInt(10);
        Candy candy = null;

        if (randomNum < 9) {
            int colorRandomNum = new Random().nextInt(4);
            candy = switch (colorRandomNum) {
                case 0 -> new NormalCandy(Color.PINK);
                case 1 -> new NormalCandy(Color.BLUE);
                case 2 -> new NormalCandy(Color.GREEN);
                case 3 -> new NormalCandy(Color.YELLOW);
                default -> candy;
            };
        }
        else {
            int specialRandomNum = new Random().nextInt(4);
            candy = switch (specialRandomNum) {
                case 0 -> new ChainReactor(Color.BLACK);
                case 1 -> new DoublePointer(Color.GREY);
                case 2 -> new LastRowBlower(Color.LIGHTGREY);
                case 3 -> new TripleMove(Color.DARKGREY);
                default -> candy;
            };
        }
        return candy;
    }

    public static void main(String[] args) {
        CandycrushModel model = new CandycrushModel("Robin");
        int i = 1;
        while (i < boardSize.rows()*boardSize.columns()) {
            Candy candy = model.randomCandy();
            System.out.print(candy);
            if (i % model.getBoardSize().columns() == 0) {
                System.out.print("\n");
                i = 1;
            }
            i++;
        }
        System.out.print("\n");
    }

    public String getSpeler() {
        return speler;
    }

    public BoardSize getBoardSize(){
        return boardSize;
    }

    public int getScore() {
        return score;
    }

    public void candyWithPositionSelected(Position position) {
        Position invalid = new Position(-1,-1,boardSize);
        if (!Objects.equals(position, invalid)) {
            ArrayList<Position> NeigborIds = getSameNeighbourPositions(position);
            for (Position i : NeigborIds) {
                speelbord.replaceCellAt(i, randomCandy());
                score++;
            }
        } else {
            System.out.println("model:candyWithPositionSelected:positionWasMinusOne");
        }
    }

    ArrayList<Position> getSameNeighbourPositions(Position position){
        ArrayList<Position> result = new ArrayList<>();
        ArrayList<Position> neighbors = position.neighborPositions();
        result.add(position);
        for (Position i : neighbors){
            if (speelbord.getCellAt(position).equals(speelbord.getCellAt(i)) && position.validPosition(i.rowNr(), i.columnNr())){
                result.add(i);
            }
        }
        return result;
    }

    public Position getPosition(int row, int column){
        return new Position(row, column, boardSize);
    }

    public void start(){
        gestart = true;
    }

    public boolean isGestart() {
        return gestart;
    }

    public Board<Candy> getSpeelbord() {
        return speelbord;
    }

    public Set<List<Position>> findAllMatches(){
        Stream<List<Position>> horizontalMatches = horizontalStartingPositions()
                .map(this::longestMatchToRight);

        Stream<List<Position>> verticalMatches = verticalStartingPositions()
                .map(this::longestMatchDown);

        return Stream.concat(horizontalMatches, verticalMatches)
                .collect(Collectors.toSet());
    }

    public boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions){

        return positions.limit(2)
                .allMatch(p -> candy.equals(speelbord.getCellAt(p)));
    }


    public Stream<Position> horizontalStartingPositions() {
        Map<Candy, Position> allPositions = speelbord.getPositions();
        return allPositions.values().stream()
                .filter(pos -> {
                    Stream<Position> rightNeighbors = pos.walkRight();
                    return firstTwoHaveCandy(speelbord.getCellAt(pos), rightNeighbors);
                });
    }

    public Stream<Position> verticalStartingPositions() {
        Map<Candy, Position> allPositions = speelbord.getPositions();
        return allPositions.values().stream()
                .filter(pos -> {
                    Stream<Position> bottomNeighbors = pos.walkDown();
                    return firstTwoHaveCandy(speelbord.getCellAt(pos), bottomNeighbors);
                });
    }

    public List<Position> longestMatchToRight(Position pos) {
        Stream<Position> allPositions = pos.walkRight();

        return allPositions
                .takeWhile(p -> speelbord.getCellAt(p) == speelbord.getCellAt(pos))
                .collect(Collectors.toList());
    }

    public List<Position> longestMatchDown(Position pos) {
        Stream<Position> allPositions = pos.walkDown();

        return allPositions
                .takeWhile(p -> speelbord.getCellAt(p) == speelbord.getCellAt(pos))
                .collect(Collectors.toList());
    }
}
