import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

public class Square extends JComponent {
    private Board board;
    private final int color;
    private Piece occPiece;
    private boolean displayPiece;
    private int xNum, yNum;

    public Square(Board board, int color, int xNum, int yNum) {
        this.board = board;
        this.color = color;
        this.displayPiece = true;
        this.xNum = xNum;
        this.yNum = yNum;
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public int getColor() {
        return this.color;
    }

    public boolean isOccupied() {
        return (this.occPiece != null);
    }

    public Piece getOccPiece() {
        return this.occPiece;
    }

    public int getxNum() {
        return this.xNum;
    }

    public int getyNum() {
        return this.yNum;
    }

    public void setDisplayPiece(boolean b) {
        this.displayPiece = b;
    }

    public void placePiece(Piece p) {
        this.occPiece = p;
        p.setPosition(this);
    }

    public Piece removePiece() {
        Piece p = this.occPiece;
        this.occPiece = null;
        return p;
    }

    public void capturePiece(Piece p) {
        Piece capturedPiece = getOccPiece();
        if(capturedPiece.getColor() == 0) board.blackPieces.remove(capturedPiece);
        if(capturedPiece.getColor() == 1) board.whitePieces.remove(capturedPiece);
        this.occPiece = p;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(this.color == 0) {
            g.setColor(Color.gray);
        }
        else {
            g.setColor(Color.white);
        }
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        if(occPiece != null && displayPiece) {
            occPiece.draw(g);
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + xNum;
        result = prime * result + yNum;
        return result;
    }


}
