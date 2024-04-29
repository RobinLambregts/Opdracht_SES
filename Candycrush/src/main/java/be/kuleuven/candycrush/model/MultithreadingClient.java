package be.kuleuven.candycrush.model;

import java.util.Random;

public class MultithreadingClient {

    public static void main(String[] args) {
        BoardSize boardSize = new BoardSize(5,5);
        Board<String> board = new Board<>(boardSize);

        Runnable task = () -> {
            Random random = new Random();
            while (true) {
                int row = random.nextInt(board.getBoardSize().rows());
                int col = random.nextInt(board.getBoardSize().columns());
                String candy = "Candy" + random.nextInt(10);
                Position position = new Position(row, col, boardSize);
                board.replaceCellAt(position, candy);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Candy swapped");
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();
    }
}

