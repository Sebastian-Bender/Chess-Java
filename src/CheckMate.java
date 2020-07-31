import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class CheckMate {
    private Board board;
    private King whiteKing, blackKing;
    private final LinkedList<Square> squares;
    private LinkedList<Square> movableSquares;
    private LinkedList<Piece> whitePieces;
    private LinkedList<Piece> blackPieces;
    private HashMap<Square, List<Piece>> whiteMoves;
    private HashMap<Square, List<Piece>> blackMoves;

    public CheckMate(Board board, King whiteKing, King blackKing, LinkedList<Piece> whitePieces, LinkedList<Piece> blackPieces) {
        this.board = board;
        this.whiteKing = whiteKing;
        this.blackKing = blackKing;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;

        squares = new LinkedList<Square>();
        movableSquares = new LinkedList<Square>();
        whiteMoves = new LinkedHashMap<Square, List<Piece>>();
        blackMoves = new LinkedHashMap<Square, List<Piece>>();

        Square[][] b = board.getBoard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                squares.add(b[y][x]);
                whiteMoves.put(b[y][x], new LinkedList<Piece>());
                blackMoves.put(b[y][x], new LinkedList<Piece>());
            }
        }

        update();
    }

    public void update() {
        Iterator<Piece> whiteIter = whitePieces.iterator();
        Iterator<Piece> blackIter = blackPieces.iterator();

        // empty moves and movable squares
        for(List<Piece> pieces: whiteMoves.values()) {
            pieces.removeAll(pieces);
        }
        for(List<Piece> pieces: blackMoves.values()) {
            pieces.removeAll(pieces);
        }

        movableSquares.removeAll(movableSquares);

        // add moves to white and black
            // white
        while(whiteIter.hasNext()) {
            Piece p = whiteIter.next();

            if(!p.getClass().equals(King.class)) {
                if(p.getPosition() == null) {
                    whiteIter.remove();
                    continue;
                }

                List<Square> moves = p.getLegalMoves(board);
                Iterator<Square> iter = moves.iterator();
                while(iter.hasNext()) {
                    List<Piece> pieces = whiteMoves.get(iter.next());
                    pieces.add(p);
                }
            }
        }
            // black
        while(blackIter.hasNext()) {
            Piece p = blackIter.next();

            if(!p.getClass().equals(King.class)) {
                if(p.getPosition() == null) {
                    blackIter.remove();
                    continue;
                }

                List<Square> moves = p.getLegalMoves(board);
                Iterator<Square> iter = moves.iterator();
                while(iter.hasNext()) {
                    List<Piece> pieces = blackMoves.get(iter.next());
                    pieces.add(p);
                }
            }
        }

    }

    /**
     * Check if white king is in check
     */
    public boolean whiteKingInCheck() {
        update();
        Square s = whiteKing.getPosition();
        if(blackMoves.get(s).isEmpty()) {
            movableSquares.addAll(squares);
            return false;
        }
        return true;
    }

    /**
     * Check if black king is in check
     */
    public boolean blackKingInCheck() {
        update();
        Square s = blackKing.getPosition();
        if(whiteMoves.get(s).isEmpty()) {
            movableSquares.addAll(squares);
            return false;
        }
        return true;
    }

    /**
     * Check if white is in checkmate
     */
    public boolean whiteCheckmated() {
        boolean checkmate = true;
        // white king not in check
        if(!this.whiteKingInCheck()) {
            return false;
        }

        // white king in check
            // can evade
        if(canEvade(blackMoves, whiteKing)) {
            checkmate = false;
        }
        List<Piece> threats = blackMoves.get(whiteKing.getPosition());
            // can capture
        if(canCapture(whiteMoves, threats, whiteKing)) {
            checkmate = false;
        }
            // can be blocked
        if(canBlock(threats, whiteMoves, whiteKing)) {
            checkmate = false;
        }

        // returns true if there is no way to stop checkmate
        return checkmate;
    }

    /**
     * Check if black is in checkmate
     */
    public boolean blackCheckmated() {
        boolean checkmate = true;
        // white king not in check
        if(!this.blackKingInCheck()) {
            return false;
        }

        // white king in check
        // can evade
        if(canEvade(whiteMoves, blackKing)) {
            checkmate = false;
        }
        List<Piece> threats = whiteMoves.get(blackKing.getPosition());
        // can capture
        if(canCapture(blackMoves, threats, blackKing)) {
            checkmate = false;
        }
        // can be blocked
        if(canBlock(threats, blackMoves, blackKing)) {
            checkmate = false;
        }

        // returns true if there is no way to stop checkmate
        return checkmate;
    }

    private boolean canEvade(Map<Square, List<Piece>> moves, King king) {
        boolean evade = false;
        List<Square> kingMoves = king.getLegalMoves(board);
        Iterator<Square> iter = kingMoves.iterator();

        while(iter.hasNext()) {
            Square s = iter.next();
            if(!testMove(king, s)) {
                continue;
            }
            if(moves.get(s).isEmpty()) {
                movableSquares.add(s);
                evade = true;
            }
        }
        return evade;
    }

    private boolean canCapture(Map<Square, List<Piece>> poss, List<Piece> threats, King king) {
        boolean capture = false;
        if(threats.size() == 1) {
            Square s = threats.get(0).getPosition();

            if(king.getLegalMoves(board).contains(s)) {
                movableSquares.add(s);
                if(testMove(king, s)) {
                    capture = true;
                }
            }

            List<Piece> caps = poss.get(s);
            ConcurrentLinkedDeque<Piece> capturers = new ConcurrentLinkedDeque<Piece>();
            capturers.addAll(caps);

            if(!capturers.isEmpty()) {
                movableSquares.add(s);
                for(Piece p: capturers) {
                    if(testMove(p, s)) {
                        capture = true;
                    }
                }
            }
        }

        return capture;
    }

    private boolean canBlock(List<Piece> threats, Map<Square, List<Piece>> blockMoves, King king) {
        boolean block = false;

        if(threats.size() == 1) {
            Square threatSquare = threats.get(0).getPosition();
            Square kingSquare = king.getPosition();
            Square[][] boardArray = board.getBoard();

            if(kingSquare.getxNum() == threatSquare.getxNum()) {
                int max = Math.max(kingSquare.getyNum(), threatSquare.getyNum());
                int min = Math.min(kingSquare.getyNum(), threatSquare.getyNum());

                for(int i = min+1; i < max; i++) {
                    List<Piece> blocks = blockMoves.get(boardArray[i][kingSquare.getxNum()]);
                    ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<Piece>();
                    blockers.addAll(blocks);

                    if(!blockers.isEmpty()) {
                        movableSquares.add(boardArray[i][kingSquare.getxNum()]);

                        for(Piece p : blockers) {
                            if(testMove(p, boardArray[i][kingSquare.getxNum()])) {
                                block = true;
                            }
                        }

                    }
                }
            }

            if(kingSquare.getyNum() == threatSquare.getyNum()) {
                int max = Math.max(kingSquare.getxNum(), threatSquare.getxNum());
                int min = Math.min(kingSquare.getxNum(), threatSquare.getxNum());

                for(int i = min+1; i < max; i++) {
                    List<Piece> blocks = blockMoves.get(boardArray[kingSquare.getyNum()][i]);
                    ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<Piece>();
                    blockers.addAll(blocks);

                    if(!blockers.isEmpty()) {
                        movableSquares.add(boardArray[kingSquare.getyNum()][i]);

                        for(Piece p : blockers) {
                            if(testMove(p, boardArray[kingSquare.getyNum()][i])) {
                                block = true;
                            }
                        }

                    }
                }
            }

            Class<? extends Piece> threatClass = threats.get(0).getClass();

            if(threatClass.equals(Queen.class) || threatClass.equals(Bishop.class)) {
                int kingX = kingSquare.getxNum();
                int kingY = kingSquare.getyNum();
                int threatX = threatSquare.getxNum();
                int threatY = threatSquare.getyNum();

                if(kingX > threatX && kingY > threatY) {
                    for(int i = threatX + 1; i < kingX; i++) {
                        threatY++;
                        List<Piece> blocks = blockMoves.get(boardArray[threatY][i]);
                        ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<Piece>();
                        blockers.addAll(blocks);

                        if(!blockers.isEmpty()) {
                            movableSquares.add(boardArray[threatY][i]);

                            for(Piece p : blockers) {
                                if(testMove(p, boardArray[threatY][i])) {
                                    block = true;
                                }
                            }
                        }
                    }
                }

                if(kingX > threatX && kingY < threatY) {
                    for(int i = threatX + 1; i < kingX; i++) {
                        threatY--;
                        List<Piece> blocks = blockMoves.get(boardArray[threatY][i]);
                        ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<Piece>();
                        blockers.addAll(blocks);

                        if(!blockers.isEmpty()) {
                            movableSquares.add(boardArray[threatY][i]);

                            for(Piece p : blockers) {
                                if(testMove(p, boardArray[threatY][i])) {
                                    block = true;
                                }
                            }
                        }
                    }
                }

                if(kingX < threatX && kingY > threatY) {
                    for(int i = threatX - 1; i > kingX; i--) {
                        threatY++;
                        List<Piece> blocks = blockMoves.get(boardArray[threatY][i]);
                        ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<Piece>();
                        blockers.addAll(blocks);

                        if(!blockers.isEmpty()) {
                            movableSquares.add(boardArray[threatY][i]);

                            for(Piece p : blockers) {
                                if(testMove(p, boardArray[threatY][i])) {
                                    block = true;
                                }
                            }
                        }
                    }
                }

                if(kingX < threatX && kingY < threatY) {
                    for(int i = threatX - 1; i > kingX; i--) {
                        threatY--;
                        List<Piece> blocks = blockMoves.get(boardArray[threatY][i]);
                        ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<Piece>();
                        blockers.addAll(blocks);

                        if(!blockers.isEmpty()) {
                            movableSquares.add(boardArray[threatY][i]);

                            for(Piece p : blockers) {
                                if(testMove(p, boardArray[threatY][i])) {
                                    block = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return block;
    }

    public boolean testMove(Piece p, Square s) {
        boolean moveTest = true;
        Piece c = s.getOccPiece();
        Square init = p.getPosition();

        p.move(s);
        update();

        if(p.getColor() == 0 && blackKingInCheck()) {
            moveTest = false;
        } else if(p.getColor() == 1 && whiteKingInCheck()) {
            moveTest = false;
        }

        p.move(init);
        if(c != null) {
            s.placePiece(p);
        }

        update();
        movableSquares.addAll(squares);
        return moveTest;
    }

    public List<Square> getAllowedSquares(boolean b) {
        movableSquares.removeAll(movableSquares);
        if(whiteKingInCheck()) {
            whiteCheckmated();
        } else if(blackKingInCheck()) {
            blackCheckmated();
        }
        return movableSquares;
    }
}
