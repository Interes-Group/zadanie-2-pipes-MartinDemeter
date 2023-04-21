package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TileI extends JPanel {

    @Setter
    private boolean highlight;

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
    private TileI previous;

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

    private Step previousStep;

    public TileI() {
        this.finish = false;
        this.playable = false;

        this.directionOne = null;
        this.directionTwo = null;
        this.tileType = 0;
        this.orientation = 0;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.ORANGE);
        this.neighbours = new HashMap<>();

        this.previousStep = null;
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
            } else {

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

    public void addNeighbour(Direction direction, TileI tile) {
        this.neighbours.put(direction, new Connection(tile));
    }

    public ArrayList<TileI> getAllNeighbour() {
        ArrayList<TileI> all = new ArrayList<>();
        this.neighbours.values().forEach(connection -> all.add(connection.getTile()));
        return all;
    }

    public void connectWith(TileI node) {
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

    public void removeNoConnected() {
        Iterator<Map.Entry<Direction, Connection>> iterator = this.neighbours.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Direction, Connection> entry = iterator.next();
            if (!entry.getValue().getTile().isPlayable()) {
                iterator.remove();
            }
        }
    }

    public void rotate() {
        if (this.directionOne != null && this.directionTwo != null) {
            this.directionOne = this.directionOne.next();;
            this.directionTwo = this.directionTwo.next();
        }else {
            this.directionOne = this.directionOne.next();;
        }
        orientation = (orientation + 90) % 360;
        System.out.println(orientation);
        repaint();
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



    public void setTileOrientation(int number) {
        number +=1;
        switch (number) {
            case 1: this.orientation = 0;
                break;
            case 2: this.orientation = 90;
                break;
            case 3: this.orientation = 180;
                break;
            case 4: this.orientation = 270;
                break;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();


        if (this.highlight) {
            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect((int) (0 + this.getWidth() * 0.04), (int) (0 + this.getHeight() * 0.04),
                    (int) (this.getWidth() * 0.94), (int) (this.getHeight() * 0.94));
            this.highlight = false;
        }

        if (playable){
            g2d.rotate(Math.toRadians(orientation), getWidth() / 2, getHeight() / 2);
            if (tileType == 1) {
                g2d.setStroke(new BasicStroke(4));
                g2d.setColor(Color.blue);
                g2d.fillRect((int) (0 + this.getWidth() * 0.4), 0,
                        (int) (this.getWidth() * 0.2), this.getHeight());
            } else if (tileType == 2){
                g2d.setStroke(new BasicStroke(4));
                g2d.setColor(Color.blue);
                g2d.fillRect((int) (0 + this.getWidth() * 0.4), 0,
                        (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.6));
                g2d.fillRect(0, (int) (0 + this.getWidth() * 0.4),
                        (int) (this.getWidth() * 0.6), (int) (this.getWidth() * 0.2));
            } else {
                g2d.setStroke(new BasicStroke(4));
                g2d.setColor(Color.blue);
                g2d.fillRect((int) (0 + this.getWidth() * 0.4), 0,
                        (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.6));
            }
        }

        if (isFinish()){
            setBackground(Color.CYAN);
        }
        if(isStart()) {
            setBackground(Color.magenta);
        }
    }
}
