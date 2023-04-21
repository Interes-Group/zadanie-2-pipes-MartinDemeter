package sk.stuba.fei.uim.oop.board;

public enum Direction {
    UP(0, 0, 1, 0),
    DOWN(0, 1, 1, 1),
    LEFT(0, 0, 0, 1),
    RIGHT(1, 0, 1, 1);

    private int x1;
    private int y1;
    private int x2;
    private int y2;



    Direction(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

    }

    public Direction opposite() {
        if (this == UP) {
            return DOWN;
        } else if (this == DOWN) {
            return UP;
        } else if (this == LEFT) {
            return RIGHT;
        } else { // this == RIGHT
            return LEFT;
        }
    }

    public Direction next() {
        if (this == UP) {
            return RIGHT;
        } else if (this == DOWN) {
            return LEFT;
        } else if (this == LEFT) {
            return UP;
        } else { // this == RIGHT
            return DOWN;
        }
    }
}
