import java.util.LinkedList;
import java.util.List;

public class Rook extends Piece{
    public Rook(int color, Square initialSquare, String imgName) {
        super(color, initialSquare, imgName);
    }
    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = b.getBoard();

        int x = this.getPosition().getxNum();
        int y = this.getPosition().getyNum();
        int[] occupations = getLinearOccupations(board, x, y);

        for(int i = occupations[0]; i <= occupations[1]; i++) {
            if(i != y) {
                legalMoves.add(board[i][x]);
            }
        }

        for(int i = occupations[2]; i <= occupations[3]; i++) {
            if(i != x) {
                legalMoves.add(board[y][i]);
            }
        }

        return legalMoves;
    }
}
