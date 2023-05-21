package GUI;

import Main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Frame extends JFrame{

    public void Create(JPanel _gamePanel, KeyListener _keyInput) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setBackground(Color.black);

        this.setSize(160 * 2, 90 * 2);
        _gamePanel.setSize(this.getSize());
        this.add(_gamePanel);
        this.addKeyListener(_keyInput);

        this.setLayout(new CardLayout());

        this.setVisible(true);
    }
}
