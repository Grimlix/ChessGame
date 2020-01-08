package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece {
    private static final PieceType type = PieceType.BISHOP;

    public Bishop(Square square, PlayerColor color,PieceType type){
        super(square,color,type);
    }


    public PieceType getType(){
        return type;
    }

    public boolean isLegalMove(Board board, Square to){
        return false;
    }
}
