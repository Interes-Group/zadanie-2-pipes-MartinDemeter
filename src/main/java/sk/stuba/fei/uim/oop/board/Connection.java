package sk.stuba.fei.uim.oop.board;

import lombok.Data;
import lombok.Setter;

@Data
public class Connection {

    private TileI tile;
@Setter
    private boolean connected;

    public Connection(TileI tile) {
        this.tile = tile;
        this.connected = false;
    }
}
