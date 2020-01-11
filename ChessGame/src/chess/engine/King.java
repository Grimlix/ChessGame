package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.StrictMath.abs;

public class King extends Piece {

    private static final int DISTANCE_MAX = 2;
    private boolean hasMoved;


    public King(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);
        this.hasMoved = false;
    }

    private boolean getHasMoved() {
        return this.hasMoved;
    }

    public boolean isLegalRock(Board board, Square to) {
        //if King didnt move yet
        if (!this.hasMoved) {
            if (abs(to.getX() - getSquare().getX()) == 2) {
                if (to.getX() < getSquare().getX()) { //big castling
                    if (board.getBoard()[0][getSquare().getY()].getPiece() instanceof Rook) {//check if tower is at it place
                        //check if tower has moved
                        if (!((Rook) board.getBoard()[0][getSquare().getY()].getPiece()).getHasMoved()) {
                            //check if square are empty
                            for (int i = 1; i < 4; i++) {
                                if (board.getBoard()[i][getSquare().getY()].getPiece() != null) {
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
                } else { //small castling
                    if (board.getBoard()[7][getSquare().getY()].getPiece() instanceof Rook) {//chef if tower is at it place
                        //check if tower has moved
                        if (!((Rook) board.getBoard()[7][getSquare().getY()].getPiece()).getHasMoved()) {
                            //check if square are empty
                            for (int i = 5; i < 7; i++) {
                                if (board.getBoard()[i][getSquare().getY()].getPiece() != null) {
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public boolean isLegalMove(Board board, Square to) {

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        int distX = this.getSquare().getX() + DISTANCE_MAX;
        int distX_neg = this.getSquare().getX() - DISTANCE_MAX;
        int distY = this.getSquare().getY() + DISTANCE_MAX;
        int distY_neg = this.getSquare().getY() - DISTANCE_MAX;

        if (to.getX() < distX && to.getX() > distX_neg && to.getY() < distY && to.getY() > distY_neg) {
            return true;
        }

        return false;
    }
}
