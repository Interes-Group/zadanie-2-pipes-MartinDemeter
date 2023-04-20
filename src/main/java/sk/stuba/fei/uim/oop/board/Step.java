package sk.stuba.fei.uim.oop.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Step {
    private TileI current;
    private TileI previous;
}
