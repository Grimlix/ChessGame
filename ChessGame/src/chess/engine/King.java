package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class King extends Piece{

    private static final int DISTANCE_MAX = 2;

    public King(Square square, PlayerColor color,PieceType type){
        super(square,color,type);
    }

    public boolean isLegalMove(Board board, Square to){

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        System.out.println("Je suis dans le King");

        int distX = this.getSquare().getX() + DISTANCE_MAX;
        int distX_neg = this.getSquare().getX() - DISTANCE_MAX;
        int distY = this.getSquare().getY() + DISTANCE_MAX;
        int distY_neg = this.getSquare().getY() - DISTANCE_MAX;

        if(to.getX() < distX && to.getX() > distX_neg  && to.getY() < distY && to.getY() > distY_neg){
            return true;
        }


        return false;

    }
}
