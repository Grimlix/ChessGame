package chess.engine;

import chess.ChessView;
import chess.PieceType;

public class askUserBishop implements ChessView.UserChoice {
    private static final PieceType type = PieceType.BISHOP;

    @Override
    public String toString() {
        return textValue();
    }

    public String textValue() {
        return "Bishop";
    }

    public static PieceType getType() {
        return type;
    }
}
