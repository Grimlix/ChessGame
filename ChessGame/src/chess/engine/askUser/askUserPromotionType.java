package chess.engine.askUser;

import chess.ChessView;
import chess.PieceType;

public class askUserPromotionType implements ChessView.UserChoice {
    private PieceType type;

    public String textValue() {
        return "User";
    }

    public void setType(PieceType type) {
        this.type = type;
    }
}
