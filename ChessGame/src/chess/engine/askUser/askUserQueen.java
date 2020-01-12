package chess.engine.askUser;

import chess.ChessView;
import chess.PieceType;

public class askUserQueen implements ChessView.UserChoice {

    private static final PieceType type = PieceType.QUEEN;
    @Override
    public String toString() {
        return textValue();
    }

    public String textValue() {
        return "Queen";
    }

    public static PieceType getType() {
        return type;
    }
}
