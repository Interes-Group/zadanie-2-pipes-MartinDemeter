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

//    public void setPrevious(TileI previous) {
//        this.previous = previous;
//    }
//
//    public TileI getPrevious() {
//        return this.previous;
//    }

    public Step getPreviousStep() {
        return this.previousStep;
    }

    public void setPreviousStep(Step step) {
        this.previousStep = step;
    }
    public void printNeighbour() {
        for (Direction direction : Direction.values()) {
            Connection connection = this.neighbours.get(direction);
            if (connection != null) {
                System.out.println(direction + ": " + connection);
                if (this.directionOne == null){
                    this.directionOne = direction;
                } else {
                    this.directionTwo = direction;
                }
            } else {
                System.out.println(direction + ": no connection");
            }
        }
    }

    public TileI getNeighbour(Direction dir) {
        Connection conn = neighbours.get(dir);
        if (conn != null && conn.isConnected()) {
            return neighbours.get(dir).getTile();
        }
        return null;
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
//    public TileI getNeighbour(Direction dir) {
//        Connection conn = neighbours.get(dir);
//        if (conn != null && conn.isConnected()) {
//            return conn.getNeighbour(this);
//        }
//        return null;
//    }

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

//


    public void removeNoConnected() {
        Iterator<Map.Entry<Direction, Connection>> iterator = this.neighbours.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Direction, Connection> entry = iterator.next();
            if (!entry.getValue().isConnected()) {
                iterator.remove();
            }
        }
    }

    public void fintTileType() {
        if ((neighbours.containsKey(Direction.UP) && neighbours.containsKey(Direction.DOWN)) || (neighbours.containsKey(Direction.LEFT) && neighbours.containsKey(Direction.RIGHT))){
            this.tileType = 1;
        } else {
            this.tileType = 2;
        }
    }


    public void rotate() {
        orientation = (orientation + 90) % 360;
        repaint();
    }

    public void setTileOrientation(int number) {

        number +=1;
//        System.out.println(number);
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
//    private void calculateDirection() {
//        if ((neighbours.containsKey(Direction.UP) && neighbours.containsKey(Direction.DOWN))) {
//            this.neighbours.containsKey(Direction.UP).
//
//        }
//    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();

        g2d.rotate(Math.toRadians(orientation), getWidth() / 2, getHeight() / 2);

        if (this.highlight) {

            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect((int) (0 + this.getWidth() * 0.04), (int) (0 + this.getHeight() * 0.04),
                    (int) (this.getWidth() * 0.94), (int) (this.getHeight() * 0.94));
            this.setHighlight(false);
         }

        if (playable){
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
//            for (Map.Entry<Direction, Connection> entry : this.neighbours.entrySet()) {
//                System.out.println(entry.getValue().isConnected());
//
//            }
//            g2d.setStroke(new BasicStroke(4));
//            g2d.setColor(Color.blue);
//            g2d.fillRect((int) (0 + this.getWidth() * 0.4), 0,
//                    (int) (this.getWidth() * 0.2), this.getHeight());


        }

        if (isFinish()){
            setBackground(Color.CYAN);
        }
        if(isStart()) {
            setBackground(Color.magenta);
        }




//        g.setColor(Color.orange);

//        ((Graphics2D) g).setStroke(new BasicStroke(1));
    }
}
