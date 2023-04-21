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




        HashSet<TileI> visitedNodes = new HashSet<>();
        ArrayList<TileI> path = new ArrayList<>();
        int randomNumber = randomRange(dimension-1);
        TileI start = this.board[0][randomNumber];
        start.setStart(true);
        TileI finish = this.board[dimension-1][randomRange(dimension-1)];
        finish.setFinish(true);

        dfsRandom(start, finish, path);


//        System.out.println("Path: ");
        for (TileI tile : path) {
            tile.setBackground(Color.green);
        }
//        System.out.println("END");

        this.setValue(path);

//        System.out.println("Path: ");
//        for (TileI tile : path) {
//            System.out.println(tile.getAllNeighbour());
//        }
//        System.out.println("END");
//        HashSet<TileI> visitedNodes = new HashSet<>();
//        ArrayList<Step> stack = new ArrayList<>();
//        int randomNumber = randomRange(dimension-1);
//        this.board[0][randomNumber].setStart(true);
//        stack.add(new Step(this.board[0][randomNumber], null));
//
//        HashSet<TileI> correctPath = new HashSet<>();
//
//        while (!stack.isEmpty()) {
//            Step step = stack.remove(0);
//            TileI currentTile = step.getCurrent();
//            currentTile.setPreviousStep(step);
//
//            if (visitedNodes.contains(currentTile)) {
//                continue;
//            }
//            if (step.getPrevious() != null) {
//
//                currentTile.connectWith(step.getPrevious());
//
//            }
//            ArrayList<TileI> allNeighbours = currentTile.getAllNeighbour();
//            Collections.shuffle(allNeighbours);
//            allNeighbours.forEach(neighbour -> {
//                if (!visitedNodes.contains(neighbour)) {
//
//                    stack.add(0, new Step(neighbour, currentTile));
//
//                }
//            });
//            visitedNodes.add(currentTile);
//            if (currentTile.isFinish()) {
//                TileI current = currentTile;;
//                while (current != null) {
//                    current.removeNoConnected ();
//
//                    correctPath.add(current);
//
////                    current.setBackground(Color.red);
//                    current.setPlayable(true);
//                    current.fintTileType();
////                    current.setTileOrientation(randomRange(3));
//
//
//
//                    System.out.println(current.getNeighbours());
//                    current = current.getPreviousStep().getPrevious();
//
//
//                }
//
//                break;
//            }
//        }



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
        while (current != null) {
            path.add(current);
            current.connectWith(current.getPrevious());
            current.removeNoConnected();
            current.setPlayable(true);
//            System.out.println(current.getNeighbours());
            current.printNeighbour();
            current.setTileTypeBasedOnConnections();
            System.out.println(current.getTileType());

            System.out.println(current.getDirectionOne()+" " +current.getDirectionTwo());
//            current.setTileOrientation(randomRange(3));
            current = current.getPrevious();

        }
        Collections.reverse(path);
        return path;
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

    private boolean dfs(TileI current, HashSet<TileI> visitedNodes, ArrayList<TileI> path) {
        visitedNodes.add(current);
        path.add(current);

        if (current.isFinish()) {
            return true;
        }

        for (TileI neighbour : current.getAllNeighbour()) {
            current.setBackground(Color.red);
            if (!visitedNodes.contains(neighbour)) {

                if (dfs(neighbour, visitedNodes, path)) {

                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        return false;
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