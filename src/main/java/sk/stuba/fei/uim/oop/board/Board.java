package sk.stuba.fei.uim.oop.board;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Board extends JPanel {

    private Tile[][] board;
    @Getter
    private Tile startTile;

    public Board(int dimension) {
        this.initializeBoard(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(Color.YELLOW);

        ArrayList<Tile> path = new ArrayList<>();

        startTile = this.board[0][randomRange(dimension-1)];
        startTile.setStart(true);
        Tile finish = this.board[dimension-1][randomRange(dimension-1)];
        finish.setFinish(true);

        dfsRandom(startTile, finish, path);

        for (Tile tile : path) {
            tile.removeNoPlayable();
            tile.setBackground(Color.green);
            tile.printNeighbour2();
            System.out.println("------- " + tile.getTileType());
        }

//        this.setValue(path);

    }
    private void setValue(ArrayList<Tile> path) {
        for (Tile tile : path) {
            int x = tile.getX();
            int y = tile.getY();

            for (Tile neighbour : tile.getAllNeighbour()) {
                int dx = neighbour.getX() - x;
                int dy = neighbour.getY() - y;

                if (neighbour.getTileType() == 0) {
                    if ((dx == 0 && Math.abs(dy) == 1) || (dy == 0 && Math.abs(dx) == 1)) {
                        neighbour.setTileType(2);
                    } else if ((dx == 0 && Math.abs(dy) == 2) || (dy == 0 && Math.abs(dx) == 2)) {
                        neighbour.setTileType(1);
                    }
                }
            }
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
                    current.setBackground(Color.red);
                    neighbor.setPrevious(current);
                    stack.push(neighbor);
                }
            }
        }

        // Construct the path from end to start
        Tile current = finish;
        do {
            current.setPlayable(true);
            path.add(current);
//            current.connectWith(current.getPrevious());
////            current.removeNoConnected();
//            current.setPlayable(true);
////            System.out.println(current.getNeighbours());
//            current.printNeighbour();
//           current.printNeighbour2();
//            current.setTileTypeBasedOnConnections();
//            current.removeNoConnected();
//            System.out.println(current.getTileType());
//
//            System.out.println(current.getDirectionOne()+" " +current.getDirectionTwo());
////            current.setTileOrientation(randomRange(3));
            this.updateTile(current);
            current = current.getPrevious();

        } while (current != null);
        Collections.reverse(path);
    }

    private void updateTile(Tile tile) {
        tile.connectWith(tile.getPrevious());
//            current.removeNoConnected();
//        tileI.setPlayable(true);
//            System.out.println(current.getNeighbours());
        tile.printNeighbour();
//        tileI.printNeighbour2();
        tile.setTileTypeBasedOnConnections();


        tile.pairDirectionAndTubes();
        System.out.println(tile.getTileType());

        System.out.println(tile.getDirectionOne()+" " + tile.getDirectionTwo());
            tile.multipleRotate(randomRange(3));

    }

    public String getPathString(ArrayList<Tile> path) {
        StringBuilder sb = new StringBuilder();
        sb.append("Path: ");
        for (Tile tile : path) {
            sb.append(tile.toString());
            sb.append(" -> ");
        }
        sb.delete(sb.length() - 4, sb.length());
        return sb.toString();
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
//        System.out.println(number);
        return number;
    }
}