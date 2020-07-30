import java.util.List;

public class Rook extends Piece{
    public Rook(int color, Square initialSquare, String imgName) {
        super(color, initialSquare, imgName);
    }
    @Override
    public List<Square> getLegalMoves(Board b) {
        return null;
    }
}
