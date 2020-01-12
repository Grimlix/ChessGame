package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board.Board;
import chess.engine.Board.Square;

public class Pawn extends Piece {

    public Pawn(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);
    }


    @Override
    public boolean isLegalMove(Board board, Square to) {

        int maxDistance = 1;

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        //If the pawn is still at his initial position, he can move up to
        //2 squares.
        if ((this.getSquare().getY() == 1 && getColor() == PlayerColor.WHITE) || (this.getSquare().getY() == 6 && getColor() == PlayerColor.BLACK)) {
            maxDistance = 2;
        }

        //Pawn cannot go backward
        if (getColor() == PlayerColor.WHITE) {
            if (to.getY() <= getSquare().getY()) {
                return false;
            }
        } else {
            if (to.getY() >= getSquare().getY()) {
                return false;
            }
        }

        //Y distance is less than maxDistance
        if (StrictMath.abs(to.getY() - getSquare().getY()) > maxDistance) {
            return false;
        } else {
            //if move vertically
            if (to.getX() == getSquare().getX()) {
                if (to.getPiece() != null) {
                    return false;
                }
            } else {//if eat
                if ((StrictMath.abs(to.getY() - getSquare().getY()) > 1) || (StrictMath.abs(to.getX() - getSquare().getX()) > 1) || to.getPiece() == null) {
                    return false;
                }
            }
        }

        return true;

    }

    @Override
    public String textValue() {
        return "Pawn";
    }

    @Override
    public String toString() {
        return textValue();
    }
}
