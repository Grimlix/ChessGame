package chess.engine.askUser;

import chess.ChessView;
import chess.PieceType;

public class askUserKnight implements ChessView.UserChoice {
    private static final PieceType type = PieceType.KNIGHT;

    @Override
    public String toString() {
        return textValue();
    }

    @Override
    public String textValue() {
        return "Knight";
    }

    public static PieceType getType() {
        return type;
    }
}
