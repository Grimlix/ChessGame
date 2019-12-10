package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends Piece{
    public Queen(int x, int y, PlayerColor color){
        super(x,y,color, PieceType.QUEEN);
    }

}
