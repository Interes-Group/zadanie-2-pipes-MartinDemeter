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
    private JButton buttonRestart;

    @Getter
    private JButton buttonControl;

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
        this.counter = 1;

        this.buttonRestart = new JButton("RESTART");
        this.buttonControl = new JButton("VALIDATE");
        this.buttonRestart.addActionListener(this);
        this.buttonControl.addActionListener(this);
        this.buttonRestart.setFocusable(false);
        this.buttonControl.setFocusable(false);

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

    private void validateConnections() {
        System.out.println("kokot");
        this.currentBoard.getStartTile().validate();
        this.currentBoard.getStartTile().setHighlight(true);
        if (this.currentBoard.getFinishTile().isHighlight()) {
            this.counter++;
            this.gameRestart();
        }
        this.currentBoard.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonRestart){
            this.counter = 1;
            this.gameRestart();
        } else if (e.getSource() == buttonControl){
            this.validateConnections();
        }

//        this.mainGame.revalidate();
//        this.mainGame.repaint();
//        this.mainGame.setFocusable(true);
//        this.mainGame.requestFocus();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        } else {
            ((Tile) current).setHighlightMouse(true);
        }
        this.currentBoard.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Tile)) {
            return;
        } else {

            ((Tile) current).rotate();
            ((Tile) current).setHighlightMouse(true);
            System.out.println(((Tile) current).getDirectionOne() + " " + ((Tile) current).getDirectionTwo());
        }
        this.currentBoard.repaint();

    }


//    @Override
//    public void mouseExited(MouseEvent e) {
//        Component current = this.currentBoard.getComponentAt(e.getX(), e.getY());
//        if (!(current instanceof TileI)) {
//            return;
//        } else {
//            ((TileI) current).setHighlight(false );
//            System.out.println("keket");
//            ((TileI) current).repaint();
//
//        }
//
//    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int newSize = ((JSlider) e.getSource()).getValue();
        if (newSize != this.currentBoardSize) {
            this.currentBoardSize = newSize;
            this.counter = 1;
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.counter = 1;
                this.gameRestart();
                break;
            case KeyEvent.VK_ENTER:
                this.validateConnections();

                break;
            case KeyEvent.VK_ESCAPE:
                this.mainGame.dispose();
                System.exit(0);
        }
    }
}
