package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.StrictMath.abs;

public class Knight extends Piece {

    public Knight(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);
    }

    public boolean isLegalMove(Board board, Square to) {
        if (!super.isLegalMove(board, to)) {
            return false;
        }
        int deltaX = abs(getSquare().getX() - to.getX());
        int deltaY = abs(getSquare().getY() - to.getY());

        return deltaX == 2 && deltaY == 1 || deltaX == 1 && deltaY == 2;
    }

    @Override
    public String textValue() {
        return "Knight";
    }

    @Override
    public String toString() {
        return textValue();
    }
}
