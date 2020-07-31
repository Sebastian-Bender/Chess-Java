import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameScreen {
    private JFrame gameFrame;
    private Board board;

    public GameScreen() {
        gameFrame = new JFrame("Chess");
        gameFrame.setLocation(100, 100);
        gameFrame.setLayout(new BorderLayout(20, 20));

        // Game stats (captured pieces / score)
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 2, 0, 0));
        JLabel blackIcon = new JLabel();
        JLabel blackScore = new JLabel("0");        // TODO: change later
        try {
            Image blackPlayer = ImageIO.read(getClass().getResource("/resources/black_king_icon.png"));
            blackIcon.setIcon(new ImageIcon(blackPlayer));
            statsPanel.add(blackIcon);
        } catch(IOException e) {
            System.out.println("Image not found");
        }
        statsPanel.add(blackScore);

        JLabel whiteIcon = new JLabel();
        JLabel whiteScore = new JLabel("0");        // TODO: change later
        try {
            Image whitePlayer = ImageIO.read(getClass().getResource("/resources/white_king_icon.png"));
            whiteIcon.setIcon(new ImageIcon(whitePlayer));
            statsPanel.add(whiteIcon);
        } catch(IOException e) {
            System.out.println("Image not found");
        }
        statsPanel.add(whiteScore);
        statsPanel.setPreferredSize(statsPanel.getMinimumSize());
        gameFrame.add(statsPanel, BorderLayout.NORTH);

        this.board = new Board(this);
        gameFrame.add(board, BorderLayout.CENTER);
        gameFrame.setMinimumSize(gameFrame.getPreferredSize());
        gameFrame.setSize(gameFrame.getPreferredSize());
        gameFrame.setResizable(false);
        gameFrame.pack();

        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void checkmate(int c) {
        /*if(c == 0) {
            int n = JOptionPane.showConfirmDialog(gameFrame, "White wins by checkmate!", JOptionPane.YES_NO_OPTION);
            if(n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartScreen());
                gameFrame.dispose();
            }
        }
         */
        gameFrame.dispose();
    }
}
