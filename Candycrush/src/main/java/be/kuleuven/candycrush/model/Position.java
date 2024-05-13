package be.kuleuven.candycrush.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

public record Position(int rowNr, int columnNr, BoardSize boardsize) {
    public Position {
        if (rowNr < 0 && rowNr > boardsize.rows()) throw new IllegalArgumentException("Row out of bounds");
        if (columnNr < 0 && columnNr > boardsize.columns()) throw new IllegalArgumentException("Column out of bounds");
    }

    public int getIndex() {
        return columnNr + rowNr * boardsize.columns();
    }

    public static Position fromIndex(int index, BoardSize size){
        if (index <= 0 && index > size.rows()* size.columns()) throw new IllegalArgumentException("Index out of bounds");

        int rowNr = index/ size.columns();
        int columnNr = index% size.columns();

        return new Position(rowNr, columnNr, size);
    }

    public boolean validPosition(int row, int column){
        return (row < boardsize().rows() && row >=0 && column < boardsize.columns() && column >=0);
    }

    public ArrayList<Position> neighborPositions(){
        ArrayList<Position> result = new ArrayList<>();

        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1},{1,1},{1,-1},{-1,1},{-1,-1}};
        for (int[] dir : directions){
            int newRow = rowNr + dir[0];
            int newColumn = columnNr + dir[1];

            if (validPosition(newRow, newColumn)){
                Position neighborPosition = new Position(newRow, newColumn, boardsize);
                result.add(neighborPosition);
            }
        }

        return result;
    }

    public boolean isLastColumn(){return (columnNr == boardsize().columns());}

    public Stream<Position> walkRight() {
        Collection<Position> positions = boardsize.positions();
        int currentRow = this.rowNr();
        int currentColumn = this.columnNr();

        return positions.stream()
                .filter(position -> position.rowNr() == currentRow && position.columnNr() >= currentColumn)
                .sorted(Comparator.comparingInt(Position::columnNr));
    }

    public Stream<Position> walkLeft() {
        Collection<Position> positions = boardsize.positions();
        int currentRow = this.rowNr();
        int currentColumn = this.columnNr();

        return positions.stream()
                .filter(position -> position.rowNr() == currentRow && position.columnNr() <= currentColumn)
                .sorted(Comparator.comparingInt(Position::columnNr).reversed());
    }


    public Stream<Position> walkUp() {
        Collection<Position> positions = boardsize.positions();
        int currentRow = this.rowNr();
        int currentColumn = this.columnNr();

        return positions.stream()
                .filter(position -> position.rowNr() <= currentRow && position.columnNr() == currentColumn)
                .sorted(Comparator.comparingInt(Position::rowNr).reversed());
    }

    public Stream<Position> walkDown() {
        Collection<Position> positions = boardsize.positions();
        int currentRow = this.rowNr();
        int currentColumn = this.columnNr();

        return positions.stream()
                .filter(position -> position.rowNr() <= currentRow && position.columnNr() == currentColumn)
                .sorted(Comparator.comparingInt(Position::columnNr));
    }
}

