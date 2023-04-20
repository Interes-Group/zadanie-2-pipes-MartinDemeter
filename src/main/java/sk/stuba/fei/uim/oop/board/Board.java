package sk.stuba.fei.uim.oop.board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class Board extends JPanel {

    private TileI[][] board;

    public Board(int dimension) {
        this.initializeBoard(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(Color.YELLOW);

        HashSet<TileI> visitedNodes = new HashSet<>();
        ArrayList<Step> stack = new ArrayList<>();
        int randomNumber = randomRange(dimension-1);
        this.board[0][randomNumber].setStart(true);
        stack.add(new Step(this.board[0][randomNumber], null));

        HashSet<TileI> correctPath = new HashSet<>();

        while (!stack.isEmpty()) {
            Step step = stack.remove(0);
            TileI currentTile = step.getCurrent();
            currentTile.setPreviousStep(step);
            currentTile.setBackground(Color.green);
            if (visitedNodes.contains(currentTile)) {
                continue;
            }
            if (step.getPrevious() != null) {

                currentTile.connectWith(step.getPrevious());

            }
            ArrayList<TileI> allNeighbours = currentTile.getAllNeighbour();
            Collections.shuffle(allNeighbours);
            allNeighbours.forEach(neighbour -> {
                if (!visitedNodes.contains(neighbour)) {

                    stack.add(0, new Step(neighbour, currentTile));
                }
            });
            visitedNodes.add(currentTile);
            if (currentTile.isFinish()) {
                TileI current = currentTile;;
                while (current != null) {
                    current.removeNoConnected ();

                    correctPath.add(current);

                    current.setBackground(Color.red);
                    current.setPlayable(true);
                    current.setTileOrientation(randomRange(3));



                    System.out.println(current.getNeighbours());
                    current = current.getPreviousStep().getPrevious();


                }
                for (TileI tile : correctPath) {
                    System.out.println(tile.isPlayable());
                }
                break;
            }
        }



    }

    private void initializeBoard(int dimension) {
        this.board = new TileI[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.board[i][j] = new TileI();
                this.add(this.board[i][j]);
            }
        }
        this.board[dimension-1][randomRange(dimension-1)].setFinish(true);
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                if (i != 0) {
                    this.board[i][j].addNeighbour(Direction.UP, this.board[i-1][j]);
                }
                if (i != dimension - 1) {
                    this.board[i][j].addNeighbour(Direction.DOWN, this.board[i+1][j]);
                }
                if (j != 0) {
                    this.board[i][j].addNeighbour(Direction.LEFT, this.board[i][j-1]);
                }
                if (j != dimension - 1) {
                    this.board[i][j].addNeighbour(Direction.RIGHT, this.board[i][j+1]);
                }
            }
        }
    }



    private int randomRange(int end) {
        int start = 0;
        Random random = new Random();
        int number = random.nextInt((end - start) + 1) + start; // see explanation below
        System.out.println(number);
        return number;
    }
}