package View;

import Controller.Constants;
import Controller.GameController;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GameController gc;
    public GamePanel() {
        this.gc = new GameController(this);
        this.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(gc.keyHandler);
        this.setFocusable(true);

    }

    public GameController getGc() {
        return gc;
    }
}
