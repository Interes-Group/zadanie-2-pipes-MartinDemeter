package sk.stuba.fei.uim.oop.board;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Direction opposite() {
        if (this == UP) {
            return DOWN;
        } else if (this == DOWN) {
            return UP;
        } else if (this == LEFT) {
            return RIGHT;
        } else {
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
        } else {
            return DOWN;
        }
    }
}
