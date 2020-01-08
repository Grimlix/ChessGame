package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

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
        if(this.getColor() == PlayerColor.WHITE) {

            //If the pawn is still at his initial position, he can move up to
            //2 squares.
            if(this.getSquare().getY() == 1){
                maxDistance = 2;
            }

            //The pawn cannot go backwards or sideways
            if (to.getY() <= this.getSquare().getY()) {
                return false;
            } else if (to.getY() - this.getSquare().getY() > maxDistance) {
                return false;
            }

            //The pawn can eat diagonaly if there is someone
            if(to.getX() != this.getSquare().getX()){
                if(board.getBoard()[to.getX()][to.getY()].getPiece() == null){
                    return false;
                }else{
                    return true;
                }
            }

            //Control if someone is in front of him
            for(int i = 1; i <= maxDistance; i++){
                if(board.getBoard()[this.getSquare().getX()][this.getSquare().getY() + i].getPiece() != null){
                    return false;
                }
            }


        }else{

            //If the pawn is still at his initial position, he can move up to
            //2 squares.
            if(this.getSquare().getY() == 6){
                maxDistance = 2;
            }

            //The pawn cannot go backwards or sideways
            if (to.getY() >= this.getSquare().getY()) {
                return false;
            }else if (this.getSquare().getY() - to.getY() > maxDistance) {
                return false;
            }

            //The pawn can eat diagonaly if there is someone
            if(to.getX() != this.getSquare().getX()){
                if(board.getBoard()[to.getX()][to.getY()].getPiece() == null){
                    return false;
                }else{
                    return true;
                }
            }

            //Control if someone is in front of him
            for(int i = 1; i <= maxDistance; i++) {
                if (board.getBoard()[this.getSquare().getX()][this.getSquare().getY() - i].getPiece() != null) {
                    return false;
                }
            }
        }




        return true;
    }


}

