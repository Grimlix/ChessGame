package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece {

    public Pawn(int x, int y, PlayerColor color){
        super(x,y,color, PieceType.PAWN);
    }

}

