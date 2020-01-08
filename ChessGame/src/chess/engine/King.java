package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class King extends Piece{

    public King(Square square, PlayerColor color,PieceType type){
        super(square,color,type);
    }

    public boolean isLegalMove(Board board, Square to){
        return false;
    }
}
