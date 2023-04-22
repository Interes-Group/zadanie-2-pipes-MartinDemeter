package sk.stuba.fei.uim.oop.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Step {
    private Tile current;
    private Tile previous;
}
