package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends Piece {

    public Rook(int x, int y, PlayerColor color){
        super(x,y,color, PieceType.ROOK);
    }

}
