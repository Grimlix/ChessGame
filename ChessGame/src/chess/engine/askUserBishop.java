package chess.engine;

import chess.ChessView;

public class askUserBishop implements ChessView.UserChoice {
    public String textValue() {
        return "Bishop";
    }
}
