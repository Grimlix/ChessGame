package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board.Board;
import chess.engine.Board.Square;
import chess.engine.utils.Moveable;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);
    }



    public boolean isLegalMove(Board board, Square to) {

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        Moveable diag = this.getArr()[0];
        List<Square> possibleSquare = new ArrayList<Square>();
        possibleSquare = diag.move(board, this.getSquare(),7);

        if (possibleSquare.contains(to)) {
            return true;
        }

        return false;
    }

    @Override
    public String textValue() {
        return "Bishop";
    }

    @Override
    public String toString() {
        return textValue();
    }


}