package View;

import javax.swing.*;

public class GameFrame  extends JFrame {
    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Paradise adventure");
        GamePanel gp = new GamePanel();
        add(gp);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);

        gp.getGc().setUpGame();
        gp.getGc().startGameThread();
    }
}
