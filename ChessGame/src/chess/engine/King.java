package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class King extends Piece{
    public King(int x, int y, PlayerColor color){
        super(x,y,color, PieceType.KING);
    }

}
