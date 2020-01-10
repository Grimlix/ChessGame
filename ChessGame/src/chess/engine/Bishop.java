package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.getExponent;

public class Bishop extends Piece {

    public Bishop(Square square, PlayerColor color,PieceType type){
        super(square,color,type);
    }

    public boolean isLegalMove(Board board, Square to) {
        if(!super.isLegalMove(board,to)){
            return false;
        }

        int fromX = getSquare().getX();
        int fromY = getSquare().getY();
        int toX = to.getX();
        int toY = to.getY();

        int posXDiff = abs(fromX - toX);

        //Check if to is on same diagonal than Bishop
        if(fromY > toY){
            if(fromY - toY != posXDiff){
                return false;
            }
        }else{
            if(toY - fromY != posXDiff){
                return false;
            }
        }

        posXDiff--;
        //Check if there is a Piece in squares between Bishop and to
        //right diagonals
        if(toX > fromX){
            //top right diagonal
            if(toY > fromY){
                while(posXDiff != 0){
                    if(board.getBoard()[fromX + posXDiff][fromY + posXDiff].getPiece() != null){
                        return false;
                    }
                    posXDiff--;
                }
            }else{//bottom right diagonal
                while(posXDiff != 0){
                    if(board.getBoard()[fromX + posXDiff][fromY - posXDiff].getPiece() != null){
                        return false;
                    }
                    posXDiff--;
                }
            }
        }else{ //left diagonals
            //top left diagonal
            if(toY > fromY){
                while(posXDiff != 0){
                    if(board.getBoard()[fromX - posXDiff][fromY + posXDiff].getPiece() != null){
                        return false;
                    }
                    posXDiff--;
                }
            }else{//bottom left diagonal
                while(posXDiff != 0){
                    if(board.getBoard()[fromX - posXDiff][fromY - posXDiff].getPiece() != null){
                        return false;
                    }
                    posXDiff--;
                }
            }

        }



        return true;
    }
}
