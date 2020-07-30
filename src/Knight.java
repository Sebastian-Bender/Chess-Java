import java.util.List;

public class Knight extends Piece {
    public Knight(int color, Square initialSquare, String imgName) {
        super(color, initialSquare, imgName);
    }
    @Override
    public List<Square> getLegalMoves(Board b) {
        return null;
    }
}
