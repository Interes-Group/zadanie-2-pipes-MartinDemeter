package sk.stuba.fei.uim.oop.board;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Board extends JPanel {

    private TileI[][] board;

    public Board(int dimension) {
        this.initializeBoard(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(Color.YELLOW);

        ArrayList<TileI> path = new ArrayList<>();
        int randomNumber = randomRange(dimension-1);
        TileI start = this.board[0][randomNumber];
        start.setStart(true);
        TileI finish = this.board[dimension-1][randomRange(dimension-1)];
        finish.setFinish(true);

        dfsRandom(start, finish, path);

        for (TileI tile : path) {
            tile.removeNoConnected();
            tile.setBackground(Color.green);
            tile.printNeighbour2();
            System.out.println("-------");
        }

        this.setValue(path);

    }
    public void setValue(ArrayList<TileI> path) {
        for (TileI tile : path) {
            int x = tile.getX();
            int y = tile.getY();

            for (TileI neighbour : tile.getAllNeighbour()) {
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
    public ArrayList<TileI> dfsRandom(TileI start, TileI finish, ArrayList<TileI> path) {
        HashSet<TileI> visited = new HashSet<>();

        Stack<TileI> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            TileI current = stack.pop();
            visited.add(current);

            if (current.isFinish()) {
                path.add(current);
                break;
            }

            ArrayList<TileI> neighbors = current.getAllNeighbour();
            Collections.shuffle(neighbors);
            for (TileI neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    current.setBackground(Color.red);
                    neighbor.setPrevious(current);
                    stack.push(neighbor);
                }
            }
        }

        // Construct the path from end to start
        TileI current = finish;
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
        return path;
    }

    private void updateTile(TileI tileI) {
        tileI.connectWith(tileI.getPrevious());
//            current.removeNoConnected();
//        tileI.setPlayable(true);
//            System.out.println(current.getNeighbours());
        tileI.printNeighbour();
//        tileI.printNeighbour2();
        tileI.setTileTypeBasedOnConnections();


        tileI.pairDirectionAndTubes();
        System.out.println(tileI.getTileType());

        System.out.println(tileI.getDirectionOne()+" " +tileI.getDirectionTwo());
            tileI.multipleRotate(randomRange(3));

    }

    public String getPathString(ArrayList<TileI> path) {
        StringBuilder sb = new StringBuilder();
        sb.append("Path: ");
        for (TileI tile : path) {
            sb.append(tile.toString());
            sb.append(" -> ");
        }
        sb.delete(sb.length() - 4, sb.length());
        return sb.toString();
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