package sk.stuba.fei.uim.oop.controls;

import lombok.Getter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.board.Tile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameLogic extends UniversalAdapter {

    public static final int INITIAL_BOARD_SIZE = 8;
    private JFrame mainGame;

    private Board currentBoard;

    private int counter;

    @Getter
    private JLabel levelLabel;

    @Getter
    private JLabel boardSizeLabel;

    private int currentBoardSize;

    public GameLogic(JFrame mainGame) {
        this.mainGame = mainGame;
        this.currentBoardSize = INITIAL_BOARD_SIZE;
        this.initializeNewBoard(this.currentBoardSize);
        this.mainGame.add(this.currentBoard);
        this.counter = 0;
        this.levelLabel = new JLabel();
        this.boardSizeLabel = new JLabel();
        this.updateLevelLabel();
        this.updateBoardSizeLabel();
    }

    private void updateLevelLabel() {
        this.levelLabel.setText("LEVEL: " + this.counter);
    }

    private void updateBoardSizeLabel() {
        if (this.currentBoardSize % 2 == 0) {
            this.boardSizeLabel.setText("CURRENT BOARD SIZE: " + this.currentBoardSize);
            this.mainGame.revalidate();
            this.mainGame.repaint();

        }
    }

    private void initializeNewBoard(int dimension) {
        this.currentBoard = new Board(dimension);
        this.currentBoard.addMouseMotionListener(this);
        this.currentBoard.addMouseListener(this);
    }

    private void gameRestart() {
        if (this.currentBoardSize % 2 == 0) {
            this.mainGame.remove(this.currentBoard);
            this.initializeNewBoard(this.currentBoardSize);
            this.mainGame.add(this.currentBoard);
            this.updateLevelLabel();
            this.mainGame.revalidate();
            this.mainGame.repaint();
            this.mainGame.setFocusable(true);
            this.mainGame.requestFocus();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gameRestart();
//        this.mainGame.revalidate();
//        this.mainGame.repaint();
//        this.mainGame.setFocusable(true);
//        this.mainGame.requestFocus();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        } else {
            ((Tile) current).rotate();
        }
        this.currentBoard.repaint();
    }



    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        } else {
            ((Tile) current).setHighlight(true);
        }

        this.currentBoard.repaint();
    }




    @Override
    public void stateChanged(ChangeEvent e) {
        int newSize = ((JSlider) e.getSource()).getValue();
        if (newSize != this.currentBoardSize) {
            this.currentBoardSize = newSize;
            this.gameRestart();
        }
        this.updateBoardSizeLabel();


//        this.mainGame.revalidate();
//        this.mainGame.repaint();
//        this.mainGame.setFocusable(true);
//        this.mainGame.requestFocus();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.gameRestart();
                break;
            case KeyEvent.VK_ESCAPE:
                this.mainGame.dispose();
        }
    }
}
