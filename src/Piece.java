import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class Piece {
    private final int color;
    private Square currentSquare;
    private BufferedImage img;

    public Piece(int color, Square initialSquare, String imgName) {
        this.color = color;
        this.currentSquare = initialSquare;

        try {
            if(this.img == null) {
                this.img = ImageIO.read(getClass().getResource("/resources/" + imgName));
            }
        } catch(IOException e) {
            System.out.println("Image not found: " + e.getMessage());
        }
    }

    /***
     * Move the piece to the Square the player chose
     *
     * @return true if the piece was moved
     */
    public boolean move(Square moveTo) {
        Piece occPiece = moveTo.getOccPiece();
        if(occPiece != null) {
            if(occPiece.getColor() == this.getColor()) {
                return false;
            } else {
                moveTo.capturePiece(this);
            }
        }
        currentSquare.removePiece();
        this.currentSquare = moveTo;
        currentSquare.placePiece(this);
        return true;
    }

    public Image getImage() {
        return this.img;
    }
    public int getColor() {
        return this.color;
    }

    public Square getPosition() {
        return this.currentSquare;
    }

    public void setPosition(Square s) {
        this.currentSquare = s;
    }

    public void draw(Graphics g) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();

        g.drawImage(this.img, x, y, null);
    }

    public int[] getLinearOccupations(Square[][] board, int x, int y) {
        // {last y above, last x right, last y below, last x left}
        int[] occupations = {0, 7, 7, 0};

        for(int i = 0; i < y; i++) {
            if(board[i][x].isOccupied()) {
                if(board[i][x].getOccPiece().getColor() != this.color) {
                    occupations[0] = i;
                } else {
                    occupations[0] = i + 1;
                }
            }
        }

        for(int i = 7; i > x; i--) {
            if(board[y][i].isOccupied()) {
                if(board[y][i].getOccPiece().getColor() != this.color) {
                    occupations[1] = i;
                } else {
                    occupations[1] = i - 1;
                }
            }
        }


        for(int i = 7; i > y; i--) {
            if(board[i][x].isOccupied()) {
                if(board[i][x].getOccPiece().getColor() != this.color) {
                    occupations[2] = i;
                } else {
                    occupations[2] = i - 1;
                }
            }
        }

        for(int i = 0; i < x; i++) {
            if(board[y][i].isOccupied()) {
                if(board[y][i].getOccPiece().getColor() != this.color) {
                    occupations[3] = i;
                } else {
                    occupations[3] = i + 1;
                }
            }
        }

        return occupations;
    }

    public List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
        LinkedList<Square> occupations = new LinkedList<Square>();

        int xNW = x - 1;
        int xSW = x - 1;
        int xSE = x + 1;
        int xNE = x + 1;

        int yNW = y - 1;
        int ySW = y + 1;
        int ySE = y + 1;
        int yNE = y - 1;

        while(xNW >= 0 && yNW >= 0) {
            if(board[yNW][xNW].isOccupied()) {
                if(board[yNW][xNW].getOccPiece().getColor() == this.color) {
                    break;
                } else {
                    occupations.add(board[yNW][xNW]);
                    break;
                }
            } else {
                occupations.add(board[yNW][xNW]);
                yNW--;
                xNW--;
            }
        }

        while(xSW >= 0 && ySW < 8) {
            if(board[ySW][xSW].isOccupied()) {
                if(board[ySW][xSW].getOccPiece().getColor() == this.color) {
                    break;
                } else {
                    occupations.add(board[ySW][xSW]);
                    break;
                }
            } else {
                occupations.add(board[ySW][xSW]);
                ySW++;
                xSW--;
            }
        }

        while(xSE < 8 && ySE < 8) {
            if(board[ySE][xSE].isOccupied()) {
                if(board[ySE][xSE].getOccPiece().getColor() == this.color) {
                    break;
                } else {
                    occupations.add(board[ySE][xSE]);
                    break;
                }
            } else {
                occupations.add(board[ySE][xSE]);
                ySE++;
                xSE++;
            }
        }

        while(xNE < 8 && yNE >= 0) {
            if(board[yNE][xNE].isOccupied()) {
                if(board[yNE][xNE].getOccPiece().getColor() == this.color) {
                    break;
                } else {
                    occupations.add(board[yNE][xNE]);
                    break;
                }
            } else {
                occupations.add(board[yNE][xNE]);
                yNE--;
                xNE++;
            }
        }

        return occupations;
    }

    public abstract List<Square> getLegalMoves(Board b);
    
}
