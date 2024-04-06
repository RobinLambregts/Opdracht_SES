package be.kuleuven.candycrush.model;

import java.util.function.Function;

class Board<E> {

    private E[] cells;
    private BoardSize boardSize;

    private CandycrushModel model;

    public E getCellAt(Position position){
        int index = position.getIndex();
        return cells[index];
    }

    public void replaceCellAt(Position position, E newCell){
        int index = position.getIndex();
        cells[index] = newCell;
    }

    public void fill(Function<Position, E> cellCreator){
        for (int i = 0; i < boardSize.rows()* boardSize.columns(); i++) {
            cells[i] = cellCreator.apply(Position.fromIndex(i, boardSize));
        }
    }

    public void copyTo(Board otherBoard){
        if (otherBoard.boardSize != this.boardSize){
            throw new RuntimeException("boards are not same size");
        }
        else{
            for (int i = 0; i < boardSize.rows()* boardSize.columns(); i++){
                otherBoard.cells[i] = this.cells[i];
            }
        }
    }
}
