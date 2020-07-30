import java.util.List;

public class Bishop extends Piece{
    public Bishop(int color, Square initialSquare, String imgName) {
        super(color, initialSquare, imgName);
    }
    @Override
    public List<Square> getLegalMoves(Board b) {
        return null;
    }
}
