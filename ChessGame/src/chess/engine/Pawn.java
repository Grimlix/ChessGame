package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.StrictMath.abs;

public class Pawn extends Piece {

    public Pawn(Square square, PlayerColor color, PieceType type){
        super(square,color, type);
    }

    public boolean isLegalMove(Board board, Square to) {


        /* TODO : Saut en passant (utiliser la liste des moves pour faire ça)*/


        int maxDistance = 1;

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        //If the pawn is still at his initial position, he can move up to
        //2 squares.
        if ( (this.getSquare().getY() == 1 && getColor() == PlayerColor.WHITE ) ||(this.getSquare().getY() == 6 && getColor() == PlayerColor.BLACK) ) {
            maxDistance = 2;
        }

        //Pawn cannot go backward
        if(getColor() == PlayerColor.WHITE){
            if(to.getY() <= getSquare().getY()){
                return false;
            }
        }else{
            if(to.getY() >= getSquare().getY()){
                return false;
            }
        }

        //Y distance is less than maxDistance
        if(abs(to.getY() - getSquare().getY()) > maxDistance){
            return false;
        }else{
            //if move vertically
            if(to.getX() == getSquare().getX()) {
                if(to.getPiece() != null){
                    return false;
                }
            }else {//if eat
                if((abs(to.getY() - getSquare().getY()) > 1) || (abs(to.getX() - getSquare().getX()) > 1) || to.getPiece() == null) {
                    return false;
                }
            }
        }

        return true;

    }


}

