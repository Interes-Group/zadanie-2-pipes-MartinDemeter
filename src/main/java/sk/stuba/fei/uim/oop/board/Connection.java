package sk.stuba.fei.uim.oop.board;

import lombok.Data;

@Data
public class Connection {

    private Tile tile;

    private boolean connected;

    public Connection(Tile tile) {
        this.tile = tile;
        this.connected = false;
    }
}
