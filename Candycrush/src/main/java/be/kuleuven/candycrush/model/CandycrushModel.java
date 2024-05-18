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

    public CandycrushModel(String speler, int width, int height) {
        this.speler = speler;
        boardSize = new BoardSize(width,height);
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
        maximizeScore();

        //updateBoard();

//        Position invalid = new Position(-1,-1,boardSize);
//        if (!Objects.equals(position, invalid)) {
//            ArrayList<Position> NeigborIds = getSameNeighbourPositions(position);
//            for (Position i : NeigborIds) {
//                speelbord.replaceCellAt(i, randomCandy());
//                score++;
//            }
//            boolean geupdate = updateBoard();
//            System.out.println("geupdate: " + geupdate);
//        } else {
//            System.out.println("model:candyWithPositionSelected:positionWasMinusOne");
//        }
    }

//    ArrayList<Position> getSameNeighbourPositions(Position position){
//        ArrayList<Position> result = new ArrayList<>();
//        ArrayList<Position> neighbors = position.neighborPositions();
//        result.add(position);
//        for (Position i : neighbors){
//            if (speelbord.getCellAt(position).equals(speelbord.getCellAt(i)) && position.validPosition(i.rowNr(), i.columnNr())){
//                result.add(i);
//            }
//        }
//        return result;
//    }

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
        List<List<Position>> allMatches = Stream.concat(horizontalStartingPositions(), verticalStartingPositions())
                .flatMap(p -> {
                    List<Position> horizontalMatch = longestMatchToRight(p);
                    List<Position> verticalMatch = longestMatchDown(p);
                    return Stream.of(horizontalMatch, verticalMatch);
                })
                .filter(m -> m.size() > 2)
                .sorted((match1, match2) -> match2.size() - match1.size())
                .toList();

        return allMatches.stream()
                .filter(match -> allMatches.stream()
                        .noneMatch(longerMatch -> longerMatch.size() > match.size() && new HashSet<>(longerMatch).containsAll(match)))
                .collect(Collectors.toSet());
    }

    public boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions){
        return positions.limit(2)
                .allMatch(p -> candy.equals(speelbord.getCellAt(p)));
    }

    public Stream<Position> horizontalStartingPositions() {
        Collection<Position> allPositions = boardSize.positions();
        return allPositions.stream()
                .filter(pos -> !speelbord.getCellAt(pos).equals(new EmptyCandy(Color.TRANSPARENT)))
                .filter(pos -> {
                    Stream<Position> leftNeighbor = pos.walkLeft();
                    return !firstTwoHaveCandy(speelbord.getCellAt(pos), leftNeighbor);
                });
    }

    public Stream<Position> verticalStartingPositions() {
        Collection<Position> allPositions = boardSize.positions();
        return allPositions.stream()
                .filter(pos -> !speelbord.getCellAt(pos).equals(new EmptyCandy(Color.TRANSPARENT)))
                .filter(pos -> {
                    Stream<Position> upNeighbor = pos.walkUp();
                    return !firstTwoHaveCandy(speelbord.getCellAt(pos), upNeighbor);
                });
    }

    public List<Position> longestMatchToRight(Position pos) {
        return pos.walkRight()
                .takeWhile(p -> speelbord.getCellAt(p).equals(speelbord.getCellAt(pos)))
                .collect(Collectors.toList());
    }

    public List<Position> longestMatchDown(Position pos) {
        return pos.walkDown()
                .takeWhile(p -> speelbord.getCellAt(p).equals(speelbord.getCellAt(pos)))
                .collect(Collectors.toList());
    }

    public void clearMatch(List<Position> match){
        List<Position> copy = new ArrayList<>(match);

        if(copy.isEmpty()) return;
        Position first = copy.getFirst();
        speelbord.replaceCellAt(first, new EmptyCandy(Color.TRANSPARENT));
        System.out.println(copy);
        copy.removeFirst();
        clearMatch(copy);
    }

    public void fallDownTo(Position pos){
        if (pos.rowNr() == 0) {return;}

        Position positionUp = new Position(pos.rowNr() -1, pos.columnNr(), boardSize);
        speelbord.replaceCellAt(pos, speelbord.getCellAt(positionUp));
        speelbord.replaceCellAt(positionUp, new EmptyCandy(Color.TRANSPARENT));
        fallDownTo(positionUp);
    }

    public boolean updateBoard() {
        Set<List<Position>> matches = findAllMatches();
        if (matches.isEmpty()) {
            return false;
        } else {
            for (List<Position> match : matches) {
                clearMatch(match);
                for (Position i : match) {
                    score++;
                    fallDownTo(i);
                }
            }
            updateBoard();
            return true;
        }
    }

    public boolean matchAfterSwitch(Position pos1, Position pos2){
        Candy candy1 = speelbord.getCellAt(pos1);
        Candy candy2 = speelbord.getCellAt(pos2);
        speelbord.replaceCellAt(pos1, candy2);
        speelbord.replaceCellAt(pos2, candy1);

        long matchAfterSwitch = findAllMatches().stream().count();

        speelbord.replaceCellAt(pos1, candy1);
        speelbord.replaceCellAt(pos2, candy2);

        return matchAfterSwitch > 0;
    }

    public void switchCandys(Position pos1, Position pos2, Board<Candy> board){
        Candy tussenCandy = board.getCellAt(pos1);
        board.replaceCellAt(pos1, board.getCellAt(pos2));
        board.replaceCellAt(pos2, tussenCandy);
    }

    public List<Swap> maximizeScore() {
        List<Swap> bestSequence = new ArrayList<>();
        maximizeScore(new ArrayList<>(), 0, bestSequence);
        System.out.println(score);
        System.out.println(bestSequence);
        return bestSequence;
    }

    private void maximizeScore(List<Swap> currentSequence, int currentScore, List<Swap> bestSequence) {
        Set<List<Position>> matches = findAllMatches();
        boolean noMatches = false;

        if (matches.isEmpty()) {
            noMatches = true;
            for (Position pos1 : boardSize.positions()) {
                for (Position pos2 : pos1.neighborPositions()) {
                    if (matchAfterSwitch(pos1, pos2)) {
                        switchCandys(pos1, pos2, speelbord);
                        int scoreGain = applyMatches();
                        currentSequence.add(new Swap(pos1, pos2));
                        maximizeScore(currentSequence, currentScore + scoreGain, bestSequence);
                        currentSequence.removeLast();
                        switchCandys(pos1, pos2, speelbord);
                    }
                }
            }
        }

        if(!noMatches){
            updateBoard();
            maximizeScore(currentSequence, currentScore, bestSequence);
        }

        if (currentScore > score) {
            score = currentScore;
            bestSequence.clear();
            bestSequence.addAll(currentSequence);
        } else if (currentScore == score && (bestSequence.isEmpty() || currentSequence.size() < bestSequence.size())) {
            bestSequence.clear();
            bestSequence.addAll(currentSequence);
        }
        return;
    }

    private int applyMatches() {
        int totalRemoved = 0;
        boolean updated = true;
        while (updated) {
            updated = false;
            Set<List<Position>> matches = findAllMatches();
            for (List<Position> match : matches) {
                totalRemoved += match.size();
                clearMatch(match);
                match.forEach(this::fallDownTo);
                updated = true;
            }
        }
        return totalRemoved;
    }
}

