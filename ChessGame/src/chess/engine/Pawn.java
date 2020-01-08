package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece {
    private static final PieceType type = PieceType.PAWN;

    public Pawn(Square square, PlayerColor color, PieceType type){
        super(square,color, type);
    }

    public PieceType getType(){
        return type;
    }

    public boolean isLegalMove(Board board, Square from, Square to){

      /*  if(!super.isLegalMove(board,to)){

            return false;
        }else{
            if(to.getY() - from.getY() != 1 && to.getX() != from.getX()){
                return false;
            }else{
                return true;
            }
        }
*/
        return false;
    }


}

