package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends Piece {
    private static final PieceType type = PieceType.ROOK;

    public Rook(Square square, PlayerColor color,PieceType type){
        super(square,color, type);
    }

    public PieceType getType(){
        return type;
    }

    public boolean isLegalMove(Board board, Square to){
        if(!super.isLegalMove(board,to)){
            return false;
        }
        else if((getSquare().getX() == to.getX() && getSquare().getY() != to.getY())){
            int i = getSquare().getX();
            for(int j = getSquare().getY() + 1; j < to.getY(); j++){
                if(board.getBoard()[i][j].getPiece() != null){
                    return false;
                }
            }
            return true;
        }
        else if(getSquare().getX() != to.getX() && getSquare().getY() == to.getY()){
            int j = getSquare().getY();
            for(int i = getSquare().getY() + 1; i < to.getY(); i++){
                if(board.getBoard()[i][j].getPiece() != null){
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }

    }
}
