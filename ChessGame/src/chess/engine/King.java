package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class King extends Piece{
    private static final PieceType type = PieceType.KING;

    public King(Square square, PlayerColor color,PieceType type){
        super(square,color,type);
    }

    public PieceType getType(){
        return type;
    }

    public boolean isLegalMove(Board board, Square to){
        return false;
    }
}
