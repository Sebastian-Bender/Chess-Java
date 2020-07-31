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
        return null;
    }
}
