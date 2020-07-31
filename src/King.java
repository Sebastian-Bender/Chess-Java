import java.util.LinkedList;
import java.util.List;

public class King extends Piece {
    public King(int color, Square initialSquare, String imgName) {
        super(color, initialSquare, imgName);
    }
    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = b.getBoard();

        int x = this.getPosition().getxNum();
        int y = this.getPosition().getyNum();
        for(int i = 1; i > -2; i++) {
            for(int j = 1; j > -2; j++) {
                if(!(i == 0 && j == 0)) {
                    try {
                        if(!board[y+j][x+i].isOccupied() || board[y+j][x+i].getOccPiece().getColor() != this.getColor()) {
                            legalMoves.add(board[y+j][x+i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }

        return legalMoves;
    }
}
