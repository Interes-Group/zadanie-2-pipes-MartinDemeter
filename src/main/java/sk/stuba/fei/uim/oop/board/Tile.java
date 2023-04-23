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

    @Getter
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


    public void setNeighbourConnection() {
        for (Direction direction : Direction.values()) {
            Connection connection = this.neighbours.get(direction);
            if (connection != null && connection.isConnected()) {
                if (this.directionOne == null){
                    this.directionOne = direction;
                } else {
                    this.directionTwo = direction;
                }
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

    public void multipleRotate(int number) {
        for (int i =0; i < number; i++){
            this.rotate();
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

    private void connectNeighbours(Direction direction, Tile neighbour) {
        this.neighbours.get(direction).setConnected(true);
        neighbour.neighbours.get(direction.opposite()).setConnected(true);
    }
    private boolean shouldConnect(Tile tile, Direction direction) {
        return tile.directionOne == direction.opposite() || tile.directionTwo == direction.opposite();
    }

    private void tryConnect(Tile tile, Direction direction) {
        if (shouldConnect(tile, direction)) {
            connectNeighbours(direction, tile);
            tile.setHighlight(true);
            tile.validate();
        }
    }

    public void validate() {
        if (this.neighbours.containsKey(directionOne) && !this.neighbours.get(directionOne).isConnected()) {
            Tile tile = this.neighbours.get(directionOne).getTile();
            tryConnect(tile, directionOne);
        }
        if (this.neighbours.containsKey(directionTwo) && !this.neighbours.get(directionTwo).isConnected()) {
            Tile tile = this.neighbours.get(directionTwo).getTile();
            tryConnect(tile, directionTwo);
        }
        repaint();
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
    }

    public void rotate() {
        if (this.playable) {
            if (this.directionOne != null && this.directionTwo != null) {
                this.directionOne = this.directionOne.next();
                this.directionTwo = this.directionTwo.next();
            }else if (this.isStart() || this.isFinish()){
                this.directionOne = this.directionOne.next();
            }
            orientation = (orientation + 90) % 360;
            repaint();
        }
    }

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
                if (this.directionOne == Direction.DOWN) {
                    orientation += 180;
                } else if (this.directionOne == Direction.LEFT) {
                    orientation += 270;
                } else if (this.directionOne == Direction.RIGHT) {
                    orientation += 90;
                }
                break;
        }
    }

    public void resetConnections() {
        for (Direction direction : Direction.values()) {
            if (this.neighbours.get(direction) != null){
                Connection connection = this.neighbours.get(direction);
                connection.setConnected(false);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();

        if (playable){
            g2d.rotate(Math.toRadians(orientation), this.getWidth() / 2, this.getHeight() / 2);
            g2d.setColor(Color.black);

            if (this.highlight){
                g2d.setColor(Color.blue);
            }
            if (tileType == 1) {
                g2d.fillRect((int) (0 + this.getWidth() * 0.41), 0,
                        (int) (this.getWidth() * 0.2), this.getHeight());
            } else if (tileType == 2){
                g2d.fillRect((int) (0 + this.getWidth() * 0.4), 0,
                        (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.59));
                g2d.fillRect(0, (int) (0 + this.getWidth() * 0.4),
                        (int) (this.getWidth() * 0.59), (int) (this.getWidth() * 0.2));
            } else {
                g2d.fillRect((int) (0 + this.getWidth() * 0.4), 0,
                        (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.59));
            }
        }

        if (this.highlightMouse) {
            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(6));
            g2d.drawRect((int) (0 + this.getWidth() * 0.04), (int) (0 + this.getHeight() * 0.04),
                    (int) (this.getWidth() * 0.92), (int) (this.getHeight() * 0.92));
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
