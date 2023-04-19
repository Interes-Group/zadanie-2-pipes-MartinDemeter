package sk.stuba.fei.uim.oop.board;

import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {

    @Setter
    private boolean highlight;
    public Tile() {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.ORANGE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.highlight) {
            g.setColor(Color.GREEN);
            this.highlight = false;
        } else {
            g.setColor(Color.ORANGE);
        }
        ((Graphics2D) g).setStroke(new BasicStroke(4));
        g.drawRect((int) (0 + this.getWidth() * 0.05), (int) (0 + this.getHeight() * 0.05),
                (int) (this.getWidth() * 0.92), (int) (this.getHeight() * 0.92));


        g.setColor(Color.orange);
//        ((Graphics2D) g).setStroke(new BasicStroke(1));
    }
}
