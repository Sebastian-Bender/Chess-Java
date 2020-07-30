import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class StartScreen implements Runnable {
    @Override
    public void run() {
        JFrame startFrame = new JFrame("Chess");
        startFrame.setResizable(false);
        startFrame.setSize(300, 300);
        Box components = Box.createVerticalBox();
        startFrame.add(components);
        final JPanel titlePanel = new JPanel();
        components.add(titlePanel);
        final JLabel titleLabel = new JLabel("Chess");
        titlePanel.add(titleLabel);

        // Player selection
            // Black player
        final JPanel blackPiecePanel = new JPanel();
        components.add(blackPiecePanel, BorderLayout.EAST);
        final JLabel blackPiece = new JLabel();
        try {
            Image blackPlayer = ImageIO.read(getClass().getResource("/resources/black_king.png"));
            blackPiece.setIcon(new ImageIcon(blackPlayer));
            blackPiecePanel.add(blackPiece);
        } catch(IOException e) {
            System.out.println("Image not found");
        }

            // White player
        final JPanel whitePiecePanel = new JPanel();
        components.add(whitePiecePanel);
        final JLabel whitePiece = new JLabel();
        try {
            Image whitePlayer = ImageIO.read(getClass().getResource("/resources/white_king.png"));
            whitePiece.setIcon(new ImageIcon(whitePlayer));
            whitePiecePanel.add(whitePiece);
        } catch(IOException e) {
            System.out.println("Image not found");
        }

        Box buttonBox = Box.createHorizontalBox();
        


        startFrame.setVisible(true);
    }
}
