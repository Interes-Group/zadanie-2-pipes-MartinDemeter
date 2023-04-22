package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tile extends JPanel {

    @Setter
    private boolean highlightMouse;

    @Getter
    private Map<Direction, Connection> neighbours;
    @Getter
    private Direction directionOne;

    @Getter
    private Direction directionTwo;

    @Setter
    @Getter
    private int tileType;

    @Getter
    @Setter
    private Tile previous;

    @Getter
    @Setter
    private boolean finish;

    @Getter
    @Setter
    private boolean start;

    @Getter
    @Setter
    private boolean playable;

    private int orientation;
    @Setter
    private boolean highlight;

    public Tile() {
        this.finish = false;
        this.start = false;
        this.playable = false;
        this.highlight = false;

        this.directionOne = null;
        this.directionTwo = null;
        this.tileType = 0;
        this.orientation = 0;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.ORANGE);
        this.neighbours = new HashMap<>();
    }


    public void printNeighbour() {
        for (Direction direction : Direction.values()) {
            Connection connection = this.neighbours.get(direction);
            if (connection != null && connection.isConnected() ) {

                if (this.directionOne == null){
                    this.directionOne = direction;
                } else {
                    this.directionTwo = direction;
                }
            }
        }
    }
    public void printNeighbour2() {
        for (Direction direction : Direction.values()) {
            Connection connection = this.neighbours.get(direction);
            if (connection != null ) {
                System.out.println(direction + ": " + connection);
            } else {
                System.out.println(direction + ": no connection");
            }
        }
    }

    public void setTileTypeBasedOnConnections() {
        if(!this.isFinish() && !this.isStart()) {
            if (this.directionOne.opposite() == this.directionTwo){
                this.tileType = 1;
            } else {
                this.tileType = 2;
            }
        }

    }

    public void addNeighbour(Direction direction, Tile tile) {
        this.neighbours.put(direction, new Connection(tile));
    }

    public ArrayList<Tile> getAllNeighbour() {
        ArrayList<Tile> all = new ArrayList<>();
        this.neighbours.values().forEach(connection -> all.add(connection.getTile()));
        return all;
    }

    public void connectWith(Tile node) {
        for (Map.Entry<Direction, Connection> entry : this.neighbours.entrySet()) {
            if (entry.getValue().getTile() != node) {
                continue;
            }
            if (!entry.getValue().isConnected()) {
                entry.getValue().setConnected(true);
                node.connectWith(this);
            }
            return;
        }
    }

    private boolean isNeighbourConnected(Direction direction) {
        if (this.neighbours.containsKey(direction)) {
            return this.neighbours.get(direction).isConnected();
        }
        return false;
    }

    private void connectNeighbours(Direction direction, Tile neighbour) {
        this.neighbours.get(direction).setConnected(true);
        neighbour.neighbours.get(direction.opposite()).setConnected(true);
    }
    public void validate() {


            if (this.neighbours.containsKey(directionOne) && !this.neighbours.get(directionOne).isConnected()) {
                if (this.neighbours.get(directionOne).isConnected()) {
                    System.out.println("1");
                }
                System.out.println("direction 1");

                Tile tile = this.neighbours.get(directionOne).getTile();
                if (tile.directionOne  == this.directionOne.opposite() || tile.directionTwo  == this.directionOne.opposite() ) {
//                    this.neighbours.get(directionOne).setConnected(true);
//
//                    tile.neighbours.get(directionOne.opposite()).setConnected(true);

                    connectNeighbours(directionOne, tile);
                    tile.setHighlight(true);
                    tile.validate();



                }
//                this.highlight = true;

                repaint();
//                this.setBackground(Color.blue);
//                this.setPlayable(false);





            }else if (this.neighbours.containsKey(directionTwo) && !this.neighbours.get(directionTwo).isConnected()) {
                if (this.neighbours.get(directionOne).isConnected()) {
                    System.out.println("2");
                }
                System.out.println("direction 2");
                Tile tile = this.neighbours.get(directionTwo).getTile();
                if (tile.directionOne  == this.directionTwo.opposite() || tile.directionTwo  == this.directionTwo.opposite()) {
//                    this.neighbours.get(directionTwo).setConnected(true);
//                    tile.neighbours.get(directionTwo.opposite()).setConnected(true);

                    connectNeighbours(directionTwo, tile);
                    tile.setHighlight(true);
                    tile.validate();


                }
//                this.highlight = true;
                //                this.setBackground(Color.blue);
                this.repaint();


            }

        System.out.println(directionOne +" " + directionTwo);
        this.printNeighbour();
//        for (Map.Entry<Direction, Connection> entry : this.neighbours.entrySet()) {
////            entry.getValue().setConnected(false);
//            if (!entry.getValue().isConnected()){
//                Tile tile = entry.getValue().getTile();
//
////            Direction neighbourDirection = entry.getKey();
////            Connection neighbourConnection = entry.getValue();
//
////
////            tile.directionOne.opposite() == this.directionOne
//
//            // Check if neighbourDirection's direction1 or direction2 is opposite to myDirection1 or myDirection2
//                Direction direction = entry.getKey().opposite();
//            if (this.directionOne == direction) {
//                Direction direction1 = entry.getKey();
//            }
//
//                if (direction == this.directionOne || direction == this.directionTwo) {
//                    this.setBackground(Color.blue);
//                    entry.getValue().setConnected(true);
//
//                    tile.getNeighbours().get(entry.getKey().opposite()).setConnected(true);
//
//                    System.out.println("adgsagdsagd");
//                    repaint();
//                    this.printNeighbour2();
//                    tile.printNeighbour2();
//
//                    // The neighbour's direction1 or direction2 is opposite to one of my directions
//                    // Do something with the neighbourConnection
//                }
//            }
//
//        }
    }

    public void removeNoPlayable() {
        Iterator<Map.Entry<Direction, Connection>> iterator = this.neighbours.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Direction, Connection> entry = iterator.next();
            entry.getValue().setConnected(false);
            if (!entry.getValue().getTile().isPlayable()) {
                iterator.remove();
            }
        }
//        this.neighbours.entrySet().removeIf(entry -> !entry.getValue().getTile().isPlayable());
    }

    public void rotate() {
        if (this.playable) {
            if (this.directionOne != null && this.directionTwo != null) {
                this.directionOne = this.directionOne.next();
                this.directionTwo = this.directionTwo.next();
            }else {
                this.directionOne = this.directionOne.next();
            }
            orientation = (orientation + 90) % 360;
            System.out.println(orientation);
            repaint();
        }

    }

    public void multipleRotate(int number) {
        for (int i =0; i < number; i++){
            this.rotate();
        }
    }

//    public void pairDircetionAndTubes() {
//        if (this.tileType == 1){
//            if (this.directionOne == Direction.RIGHT || this.directionTwo == Direction.RIGHT){
//                orientation += 90;
//            }
//        } else if (this.tileType == 2) {
//            if ((this.directionOne == Direction.RIGHT && this.directionTwo == Direction.DOWN) || (this.directionOne == Direction.DOWN && this.directionTwo == Direction.RIGHT)) {
//                orientation += 180;
//            } else if ((this.directionOne == Direction.LEFT && this.directionTwo == Direction.DOWN) || (this.directionOne == Direction.DOWN && this.directionTwo == Direction.LEFT)) {
//                orientation += 270;
//            } else if ((this.directionOne == Direction.RIGHT && this.directionTwo == Direction.UP) || (this.directionOne == Direction.UP && this.directionTwo == Direction.RIGHT)) {
//                orientation += 90;
//            }
//        } else {
//            if (this.directionOne == Direction.DOWN) {
//                orientation += 180;
//            } else if (this.directionOne == Direction.LEFT) {
//                orientation += 270;
//            } else if (this.directionOne == Direction.RIGHT) {
//                orientation += 90;
//            }
//        }
//    }

    public void pairDirectionAndTubes() {
        switch (this.tileType) {
            case 1:
                if (this.directionOne == Direction.RIGHT || this.directionTwo == Direction.RIGHT) {
                    orientation += 90;
                }
                break;
            case 2:
                if ((this.directionOne == Direction.RIGHT && this.directionTwo == Direction.DOWN) || (this.directionOne == Direction.DOWN && this.directionTwo == Direction.RIGHT)) {
                    orientation += 180;
                } else if ((this.directionOne == Direction.LEFT && this.directionTwo == Direction.DOWN) || (this.directionOne == Direction.DOWN && this.directionTwo == Direction.LEFT)) {
                    orientation += 270;
                } else if ((this.directionOne == Direction.RIGHT && this.directionTwo == Direction.UP) || (this.directionOne == Direction.UP && this.directionTwo == Direction.RIGHT)) {
                    orientation += 90;
                }
                break;
            default:
                switch (this.directionOne) {
                    case DOWN:
                        orientation += 180;
                        break;
                    case LEFT:
                        orientation += 270;
                        break;
                    case RIGHT:
                        orientation += 90;
                        break;
                }
                break;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();



        if (playable){
            g2d.rotate(Math.toRadians(orientation), this.getWidth() / 2, this.getHeight() / 2);
            g2d.setColor(Color.blue);

            if (this.highlight){
                g2d.setColor(Color.pink);
            }
            if (tileType == 1) {
                g2d.setStroke(new BasicStroke(4));
                g2d.fillRect((int) (0 + this.getWidth() * 0.4), 0,
                        (int) (this.getWidth() * 0.2), this.getHeight());
            } else if (tileType == 2){
                g2d.setStroke(new BasicStroke(4));
                g2d.fillRect((int) (0 + this.getWidth() * 0.4), 0,
                        (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.6));
                g2d.fillRect(0, (int) (0 + this.getWidth() * 0.4),
                        (int) (this.getWidth() * 0.6), (int) (this.getWidth() * 0.2));
            } else {
                g2d.setStroke(new BasicStroke(4));
                g2d.fillRect((int) (0 + this.getWidth() * 0.4), 0,
                        (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.6));
            }
        }

        if (this.highlightMouse) {
            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect((int) (0 + this.getWidth() * 0.04), (int) (0 + this.getHeight() * 0.04),
                    (int) (this.getWidth() * 0.94), (int) (this.getHeight() * 0.94));
            this.highlightMouse = false;
        }
        if (isFinish()){
            setBackground(Color.CYAN);
        }
        if(isStart()) {
            setBackground(Color.magenta);
        }
    }
}
