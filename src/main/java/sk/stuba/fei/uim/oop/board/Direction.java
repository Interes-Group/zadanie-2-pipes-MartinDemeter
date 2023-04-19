package sk.stuba.fei.uim.oop.board;

import lombok.Getter;

public enum Direction {
    UP(0, 0, 1, 0, "⬆"),
    DOWN(0, 1, 1, 1, "⬇"),
    LEFT(0, 0, 0, 1, "⬅"),
    RIGHT(1, 0, 1, 1, "➡");

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    @Getter
    private String label;

    Direction(int x1, int y1, int x2, int y2, String label) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.label = label;
    }
}
