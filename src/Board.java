import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

public class Board extends JPanel implements MouseListener, MouseMotionListener {
    private final Square[][] board;
    private final GameScreen gameScreen;

    // list of pieces and whether they're movable
    public final LinkedList<Piece> blackPieces;
    public final LinkedList<Piece> whitePieces;
    public List<Square> movable;

    private boolean whiteTurn;

    private Piece currentPiece;
    private int currentX;
    private int currentY;

    // TODO: add later
    //private CheckmateDetector cmd;

    public Board(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.board = new Square[8][8];
        this.blackPieces = new LinkedList<Piece>();
        this.whitePieces = new LinkedList<Piece>();
        setLayout(new GridLayout(8, 8, 0, 0));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++)
            {
                if((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                    this.add(board[x][y]);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                    this.add(board[x][y]);
                }
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    private void initializePieces() {
        // Pawn
        for(int x = 0; x < 8; x++) {
            board[1][x].placePiece(new Pawn(0, board[1][x], "black_pawn.png"));
            board[6][x].placePiece(new Pawn(1, board[6][x], "white_pawn.png"));
        }

        // Queens
        board[0][3].placePiece(new Pawn(0, board[0][3], "black_queen.png"));
        board[7][3].placePiece(new Pawn(1, board[7][3], "white_queen.png"));
        
        // Kings
        King blackKing = new King(0, board[0][4], "black_king.png");
        King whiteKing = new King(1, board[7][4], "white_king.png");
        board[0][4].placePiece(blackKing);
        board[7][4].placePiece(whiteKing);

        // Rooks
        board[0][0].placePiece(new Pawn(0, board[0][0], "black_rook.png"));
        board[0][7].placePiece(new Pawn(0, board[0][7], "black_rook.png"));
        board[7][0].placePiece(new Pawn(1, board[7][0], "white_rook.png"));
        board[7][7].placePiece(new Pawn(1, board[7][7], "white_rook.png"));
        
        // Knights
        board[0][1].placePiece(new Pawn(0, board[0][1], "black_knight.png"));
        board[0][6].placePiece(new Pawn(0, board[0][6], "black_knight.png"));
        board[7][1].placePiece(new Pawn(1, board[7][1], "white_knight.png"));
        board[7][6].placePiece(new Pawn(1, board[7][6], "white_knight.png"));
        
        // Bishops
        board[0][2].placePiece(new Pawn(0, board[0][2], "black_bishop.png"));
        board[0][5].placePiece(new Pawn(0, board[0][5], "black_bishop.png"));
        board[7][2].placePiece(new Pawn(1, board[7][2], "white_bishop.png"));
        board[7][5].placePiece(new Pawn(1, board[7][5], "white_bishop.png"));

        for(int y = 0; y < 2; y++) {
            for(int x = 0; x < 8; x++) {
                blackPieces.add(board[y][x].getOccPiece());
                whitePieces.add(board[7-y][x].getOccPiece());
            }
        }

        // TODO: add later
        // cmd = new CheckmateDetector(this, whitePieces, blackPieces, whiteKing, blackKing);
    }

    public Square[][] getBoard() {
        return this.board;
    }

    public boolean getTurn() {
        return this.whiteTurn;
    }

    public Piece getCurrentPiece() {
        return this.currentPiece;
    }

    public void setCurrentPiece(Piece p) {
        this.currentPiece = p;
    }

    @Override
    protected void paintComponent(Graphics g) {
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                Square s = board[y][x];
                s.paintComponent(g);
            }
        }

        if(currentPiece != null) {
            if((currentPiece.getColor() == 1 && whiteTurn) || (currentPiece.getColor() == 0 && !whiteTurn)) {
                final Image img = currentPiece.getImage();
                g.drawImage(img, currentX, currentY, null);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
