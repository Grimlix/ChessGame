package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board.Board;
import chess.engine.Board.Square;
import chess.engine.utils.Moveable;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {



    public Rook(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);
    }


    @Override
    public boolean isLegalMove(Board board, Square to) {

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        Moveable horizontal_vertical = this.getArr()[1];
        List<Square> possibleSquare = new ArrayList<Square>();
        possibleSquare = horizontal_vertical.move(board, this.getSquare(), 7);

        if (possibleSquare.contains(to)) {
            setHasMoved(true);
            return true;
        }
        return false;
    }

    @Override
    public String textValue() {
        return "Rook";
    }

    @Override
    public String toString() {
        return textValue();
    }
}