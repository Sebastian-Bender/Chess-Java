import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartScreen implements Runnable {
    @Override
    public void run() {
        JFrame startFrame = new JFrame("Chess");
        startFrame.setResizable(false);
        startFrame.setLocation(200, 200);
        startFrame.setSize(260, 240);
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
        final JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startFrame.dispose();
            }
        });

        final JButton start = new JButton("start game");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameScreen();
                startFrame.dispose();
            }
        });

        // add buttons
        buttonBox.add(start);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(quit);
        components.add(buttonBox);

        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setVisible(true);
    }
}
