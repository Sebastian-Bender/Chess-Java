import javax.swing.*;

public class Game implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(new StartScreen());
    }
}
