package chess.engine;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.utils.Moveable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.StrictMath.abs;

public class Queen extends Piece {

    public Queen(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);

    }

    public boolean isLegalMove(Board board, Square to) {

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        System.out.println("JE suis dans la rein");

        Moveable diag = this.getArr()[0];
        Moveable horizontal_vertical = this.getArr()[1];

        List<Square> possibleSquare_1 = horizontal_vertical.move(board, this.getSquare(),7);
        List<Square> possibleSquare_2 = diag.move(board, this.getSquare(),7);

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
