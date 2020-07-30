import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public abstract class Piece {
    private final int color;
    private Square currentSquare;
    private BufferedImage img;

    public Piece(int color, Square initialSquare, String imgName) {
        this.color = color;
        this.currentSquare = initialSquare;

        try {
            if(this.img == null) {
                this.img = ImageIO.read(getClass().getResource(imgName));
            }
        } catch(IOException e) {
            System.out.println("Image not found: " + e.getMessage());
        }
    }

    /***
     * Move the piece to the Square the player chose
     *
     * @return true if the piece was moved
     */
    public boolean move(Square moveTo) {
        return true;
    }


    public abstract List<Square> getLegalMoves(Board b);
    
}
