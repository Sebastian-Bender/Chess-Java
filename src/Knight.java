import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {
    public Knight(int color, Square initialSquare, String imgName) {
        super(color, initialSquare, imgName);
    }
    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = b.getBoard();

        int x = this.getPosition().getxNum();
        int y = this.getPosition().getyNum();

        for(int i = 2; i > -3; i--) {
            for(int j = 2; j > -3; j--) {
                if(Math.abs(i) == 2 ^ Math.abs(j) == 2) {
                    try {
                        legalMoves.add(board[y+j][x+i]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }
        return legalMoves;
    }
}
