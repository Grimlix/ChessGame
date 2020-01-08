package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends Piece {

    public Rook(Square square, PlayerColor color,PieceType type){
        super(square,color, type);
    }

    public boolean isLegalMove(Board board, Square to){
        System.out.println("In isLegalMove Rook");
        if(!super.isLegalMove(board,to)){
            System.out.println("In super isLegalMove Rook");
            return false;
        }
        else if((getSquare().getX() == to.getX() && getSquare().getY() != to.getY())){
            int i = getSquare().getX();
            if(getSquare().getY() > to.getY()){
                for(int j = getSquare().getY() - 1; j > to.getY(); j--){
                    if(board.getBoard()[i][j].getPiece() != null){
                        return false;
                    }
                }
                return true;
            }else{
                for(int j = getSquare().getY() + 1; j < to.getY(); j++){
                    if(board.getBoard()[i][j].getPiece() != null){
                        return false;
                    }
                }
                return true;
            }
        }
        else if(getSquare().getX() != to.getX() && getSquare().getY() == to.getY()){
            int j = getSquare().getY();
            if(getSquare().getX() > to.getX()){
                for(int i = getSquare().getX() -1; i > to.getX(); i--){
                    if(board.getBoard()[i][j].getPiece() != null){
                        return false;
                    }
                }
                return true;
            }else{
                for(int i = getSquare().getX() + 1; i < to.getX(); i++){
                    if(board.getBoard()[i][j].getPiece() != null){
                        return false;
                    }
                }
                return true;
            }

        }else{
            return false;
        }
    }
}
