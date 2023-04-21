package sk.stuba.fei.uim.oop.board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Connection {

    @Getter
    private TileI tile;
@Setter
@Getter
    private boolean connected;

    public Connection(TileI tile) {
        this.tile = tile;
        this.connected = false;
    }
}
