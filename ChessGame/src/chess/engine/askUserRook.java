package chess.engine;

import chess.ChessView;

public class askUserRook implements ChessView.UserChoice {
    public String textValue() {
        return "Rook";
    }
}
