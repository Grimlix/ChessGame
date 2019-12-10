package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Knight extends Piece {

    public Knight(int x, int y, PlayerColor color) {
        super(x, y, color, PieceType.KNIGHT);
    }

}
