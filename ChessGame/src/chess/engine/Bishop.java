package chess.engine;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.utils.Moveable;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

public class Bishop extends Piece {

    public Bishop(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);
    }

    public boolean isLegalMove(Board board, Square to) {

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        System.out.println("JE suis dans le fou");

        Moveable diag = this.getArr()[0];
        List<Square> possibleSquare = new ArrayList<Square>();
        possibleSquare = diag.move(board, this.getSquare());

        if (possibleSquare.contains(to)) {
            return true;
        }

        return false;
    }


}