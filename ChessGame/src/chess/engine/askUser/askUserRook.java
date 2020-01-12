package chess.engine.askUser;

import chess.ChessView;
import chess.PieceType;

public class askUserRook implements ChessView.UserChoice {
    private static final PieceType type = PieceType.ROOK;

    @Override
    public String toString() {
        return textValue();
    }

    public String textValue() {
        return "Rook";
    }

    public static PieceType getType() {
        return type;
    }
}
