package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Knight extends Piece {
    private static final PieceType type = PieceType.KNIGHT;

    public Knight(Square square, PlayerColor color,PieceType type){
        super(square,color,type);
    }


    public PieceType getType() {
        return type;
    }

    public boolean isLegalMove(Board board, Square to){
        return false;
    }
}
