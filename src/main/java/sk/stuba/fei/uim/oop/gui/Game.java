package sk.stuba.fei.uim.oop.gui;


import sk.stuba.fei.uim.oop.controls.GameLogic;

import javax.swing.*;
import java.awt.*;

public class Game {

    public Game() {
        JFrame frame = new JFrame("Water Pipes!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,890);
        frame.getContentPane().setBackground(Color.ORANGE);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.requestFocusInWindow();



        GameLogic logic = new GameLogic(frame);
        frame.addKeyListener(logic);

        JPanel downMenu = new JPanel();
        downMenu.setBackground(Color.LIGHT_GRAY);

        JPanel upMenu = new JPanel();
        downMenu.setBackground(Color.LIGHT_GRAY);

        JButton buttonRestart = new JButton("RESTART");
        buttonRestart.addActionListener(logic);
        buttonRestart.setFocusable(false);

        JButton buttonControl = new JButton("VALIDATE");
        buttonControl.addActionListener(logic);
        buttonControl.setFocusable(false);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 8, 12, 8);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(2);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(logic);

        upMenu.setLayout(new GridLayout(1,2));
        upMenu.add(logic.getLevelLabel());
        upMenu.add(logic.getBoardSizeLabel());
        frame.add(upMenu, BorderLayout.PAGE_START);

        downMenu.setLayout(new GridLayout(1, 3));
        downMenu.add(buttonRestart);
        downMenu.add(buttonControl);
        downMenu.add(slider);
        frame.add(downMenu, BorderLayout.PAGE_END);

        frame.setVisible(true);
    }
}
