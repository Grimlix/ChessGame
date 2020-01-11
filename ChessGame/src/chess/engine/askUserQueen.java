package chess.engine;

import chess.ChessView;

public class askUserQueen implements ChessView.UserChoice {
    public String textValue() {
        return "Queen";
    }
}
