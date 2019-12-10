package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece {

    public Bishop(int x, int y, PlayerColor color){
        super(x,y,color, PieceType.BISHOP);
    }

}
