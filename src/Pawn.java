import java.util.List;

public class Pawn extends Piece{
    public Pawn(int color, Square initialSquare, String imgName) {
        super(color, initialSquare, imgName);
    }
    @Override
    public List<Square> getLegalMoves(Board b) {
        return null;
    }
}
