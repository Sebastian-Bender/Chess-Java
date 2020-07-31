import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece{
    private boolean wasMoved;
    public Pawn(int color, Square initialSquare, String imgName) {
        super(color, initialSquare, imgName);
    }

    @Override
    public boolean move(Square moveTo) {
        boolean b = super.move(moveTo);
        wasMoved = true;
        return b;
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = b.getBoard();

        int x = this.getPosition().getxNum();
        int y = this.getPosition().getyNum();
        int color = this.getColor();

        if(color == 0) {
            if(!wasMoved) {
                if(!board[y+2][x].isOccupied()) {
                    legalMoves.add(board[y+2][x]);
                }
            }

            if(y+1 < 8) {
                if(!board[y+1][x].isOccupied()) {
                    legalMoves.add(board[y+1][x]);
                }
            }

            if(x-1 >= 0 && y+1 < 8) {
                if(board[y+1][x-1].isOccupied()) {
                    legalMoves.add(board[y+1][x-1]);
                }
            }

            if(x+1 < 8 && y+1 < 8) {
                if(board[y+1][x+1].isOccupied()) {
                    legalMoves.add(board[y+1][x+1]);
                }
            }
        }

        if(color == 1) {
            if(!wasMoved) {
                if(!board[y-2][x].isOccupied()) {
                    legalMoves.add(board[y-2][x]);
                }
            }

            if(y-1 >= 0) {
                if(!board[y-1][x].isOccupied()) {
                    legalMoves.add(board[y-1][x]);
                }
            }

            if(x+1 < 8 && y-1 >= 0) {
                if(board[y-1][x+1].isOccupied()) {
                    legalMoves.add(board[y-1][x+1]);
                }
            }

            if(x-1 >= 0 && y-1 >=0) {
                if(board[y-1][x-1].isOccupied()) {
                    legalMoves.add(board[y-1][x-1]);
                }
            }
        }

        return legalMoves;
    }
}
