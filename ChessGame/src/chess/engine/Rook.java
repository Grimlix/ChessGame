package chess.engine;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.utils.Moveable;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    private boolean hasMoved;

    public Rook(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);
        this.hasMoved = false;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    public boolean isLegalMove(Board board, Square to) {

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        System.out.println("JE suis dans la tour");

        Moveable horizontal_vertical = this.getArr()[1];
        List<Square> possibleSquare = new ArrayList<Square>();
        possibleSquare = horizontal_vertical.move(board, this.getSquare());

        if (possibleSquare.contains(to)) {
            return true;
        }

        return false;
    }


}