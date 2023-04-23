package sk.stuba.fei.uim.oop.board;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Board extends JPanel {

    private Tile[][] board;
    @Getter
    private Tile startTile;

    @Getter
    private Tile finishTile;

    private ArrayList<Tile> path;

    public Board(int dimension) {
        this.initializeBoard(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(Color.YELLOW);

        path = new ArrayList<>();

        startTile = this.board[0][randomRange(dimension - 1)];
        startTile.setStart(true);
        finishTile = this.board[dimension - 1][randomRange(dimension - 1)];
        finishTile.setFinish(true);

        dfsRandom(startTile, finishTile, path);

        for (Tile tile : path) {
            tile.removeNoPlayable();
        }
    }

    private void dfsRandom(Tile start, Tile finish, ArrayList<Tile> path) {
        HashSet<Tile> visited = new HashSet<>();

        Stack<Tile> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Tile current = stack.pop();
            visited.add(current);

            if (current.isFinish()) {
                break;
            }

            ArrayList<Tile> neighbors = current.getAllNeighbour();
            Collections.shuffle(neighbors);
            for (Tile neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    neighbor.setPrevious(current);
                    stack.push(neighbor);
                }
            }
        }
        Tile current = finish;
        do {
            current.setPlayable(true);
            path.add(current);
            this.updateTile(current);
            current = current.getPrevious();

        } while (current != null);
        Collections.reverse(path);
    }

    private void updateTile(Tile tile) {
        tile.connectWith(tile.getPrevious());
        tile.setNeighbourConnection();
        tile.setTileTypeBasedOnConnections();
        tile.pairDirectionAndTubes();
        tile.multipleRotate(randomRange(3));
    }

    private void initializeBoard(int dimension) {
        this.board = new Tile[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.board[i][j] = new Tile();
                this.add(this.board[i][j]);
            }
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i != 0) {
                    this.board[i][j].addNeighbour(Direction.UP, this.board[i - 1][j]);
                }
                if (i != dimension - 1) {
                    this.board[i][j].addNeighbour(Direction.DOWN, this.board[i + 1][j]);
                }
                if (j != 0) {
                    this.board[i][j].addNeighbour(Direction.LEFT, this.board[i][j - 1]);
                }
                if (j != dimension - 1) {
                    this.board[i][j].addNeighbour(Direction.RIGHT, this.board[i][j + 1]);
                }
            }
        }
    }

    public void resetValidation() {
        for (Tile tile : path) {
            tile.resetConnections();
            tile.setHighlight(false);
        }
    }

    private int randomRange(int end) {
        int start = 0;
        Random random = new Random();
        return random.nextInt((end - start) + 1) + start;
    }
}