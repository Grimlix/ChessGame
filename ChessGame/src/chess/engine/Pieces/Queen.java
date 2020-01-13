package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board.Board;
import chess.engine.Board.Square;
import chess.engine.utils.Moveable;

import java.util.List;

public class    Queen extends Piece {

    public Queen(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);

    }

    @Override
    public boolean isLegalMove(Board board, Square to) {

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        Moveable diag = this.getArr()[0];
        Moveable horizontal_vertical = this.getArr()[1];

        List<Square> possibleSquare_1 = horizontal_vertical.move(board, this.getSquare(), 7);
        List<Square> possibleSquare_2 = diag.move(board, this.getSquare(), 7);

        possibleSquare_1.addAll(possibleSquare_2);

        if (possibleSquare_1.contains(to)) {
            return true;
        }

        return false;
    }

    @Override
    public String textValue() {
        return "Queen";
    }

    @Override
    public String toString() {
        return textValue();
    }


}
