package chess.engine;

import chess.ChessView;

public class askUserKnight implements ChessView.UserChoice {
    @Override
    public String textValue() {
        return "Knight";
    }
}
