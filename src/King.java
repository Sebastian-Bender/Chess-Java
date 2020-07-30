import java.util.List;

public class King extends Piece {
    public King(int color, Square initialSquare, String imgName) {
        super(color, initialSquare, imgName);
    }
    @Override
    public List<Square> getLegalMoves(Board b) {
        return null;
    }
}
