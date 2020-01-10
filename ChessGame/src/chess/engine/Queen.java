package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.StrictMath.abs;

public class Queen extends Piece{

    public Queen(Square square, PlayerColor color,PieceType type){
        super(square,color,type);
    }

    public boolean isLegalMove(Board board, Square to){
        if(super.isLegalMove(board,to)){
            if(isToInHorizontalVerticalSquare(to) && HoritzontalVerticalIsThereAPieceBetween(board,to)){
                return true;
            }else if(isToInDiagonalSquare(to) && DiagonalsIsThereAPieceBetween(board,to)){ //in diagonal square
                    return true;
            }
        }
        return false;
    }

    //check if to is either in an Horizontal or Vertical Square from Queen
    private boolean isToInHorizontalVerticalSquare(Square to) {
        if (getSquare().getX() == to.getX() && getSquare().getY() != to.getY()
                || getSquare().getX() != to.getX() && getSquare().getY() == to.getY()) {
            return true;
        } else {
            return false;
        }
    }

    //check if there is a Piece between Queen and to
    private boolean HoritzontalVerticalIsThereAPieceBetween(Board board, Square to){

        if(getSquare().getX() == to.getX()){
            int i = getSquare().getX();
            if(getSquare().getY() > to.getY()){
                for(int j = getSquare().getY() - 1; j > to.getY(); j--){
                    if(board.getBoard()[i][j].getPiece() != null){
                        return false;
                    }
                }
            }else{
                for(int j = getSquare().getY() + 1; j < to.getY(); j++){
                    if(board.getBoard()[i][j].getPiece() != null){
                        return false;
                    }
                }
            }
        }else{
            int j = getSquare().getY();
            if(getSquare().getX() > to.getX()){
                for(int i = getSquare().getX() -1; i > to.getX(); i--){
                    if(board.getBoard()[i][j].getPiece() != null){
                        return false;
                    }
                }
            }else{
                for(int i = getSquare().getX() + 1; i < to.getX(); i++){
                    if(board.getBoard()[i][j].getPiece() != null){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //check if to is in Diagonal Square from Queen
    private boolean isToInDiagonalSquare(Square to){
        int fromX = getSquare().getX();
        int fromY = getSquare().getY();
        int toX = to.getX();
        int toY = to.getY();

        int posXDiff = abs(fromX - toX);

        //Check if to is on same diagonal than Queen
        if(fromY > toY){
            if(fromY - toY != posXDiff){
                return false;
            }
        }else{
            if(toY - fromY != posXDiff){
                return false;
            }
        }
        return true;
    }

    //Check if there is a Piece in squares between Queen and to
    private boolean DiagonalsIsThereAPieceBetween(Board board,Square to) {
        int fromX = getSquare().getX();
        int fromY = getSquare().getY();
        int toX = to.getX();
        int toY = to.getY();

        int posXDiff = abs(fromX - toX);

        posXDiff--;
        //right diagonals
        if (toX > fromX) {
            //top right diagonal
            if (toY > fromY) {
                while (posXDiff != 0) {
                    if (board.getBoard()[fromX + posXDiff][fromY + posXDiff].getPiece() != null) {
                        return false;
                    }
                    posXDiff--;
                }
            } else {//bottom right diagonal
                while (posXDiff != 0) {
                    if (board.getBoard()[fromX + posXDiff][fromY - posXDiff].getPiece() != null) {
                        return false;
                    }
                    posXDiff--;
                }
            }
        } else { //left diagonals
            //top left diagonal
            if (toY > fromY) {
                while (posXDiff != 0) {
                    if (board.getBoard()[fromX - posXDiff][fromY + posXDiff].getPiece() != null) {
                        return false;
                    }
                    posXDiff--;
                }
            } else {//bottom left diagonal
                while (posXDiff != 0) {
                    if (board.getBoard()[fromX - posXDiff][fromY - posXDiff].getPiece() != null) {
                        return false;
                    }
                    posXDiff--;
                }
            }
        }
        return true;
    }


}
