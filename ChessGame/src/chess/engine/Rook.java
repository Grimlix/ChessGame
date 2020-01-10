package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends Piece {

    private boolean hasMoved = false;

    public Rook(Square square, PlayerColor color,PieceType type){
        super(square,color, type);
    }

    public boolean getHasMoved(){
        return this.hasMoved;
    }

    //check if to is either in an Horizontal or Vertical Square from Rook
    private boolean isToInHorizontalVerticalSquare(Square to) {
        if (getSquare().getX() == to.getX() && getSquare().getY() != to.getY()
                || getSquare().getX() != to.getX() && getSquare().getY() == to.getY()) {
            return true;
        } else {
            return false;
        }
    }

    //check if there is a Piece between Rook and to
    private boolean isThereAPieceBetween(Board board, Square to){

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

    public boolean isLegalMove(Board board, Square to) {
        if(super.isLegalMove(board, to) && isToInHorizontalVerticalSquare(to) && isThereAPieceBetween(board,to)){
            return true;

        }else{
            return false;
        }
    }
}