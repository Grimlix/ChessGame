package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public class Knight extends Piece {
//    private static final PieceType type = PieceType.KNIGHT;

    public Knight(Square square, PlayerColor color,PieceType type){
        super(square,color,type);
    }

/*
    public PieceType getType() {
        return type;
    }*/

    public boolean isLegalMove(Board board, Square to){
        System.out.println("In isLegalMove Knight");
        if(!super.isLegalMove(board,to)){
            System.out.println("In super isLegalMove Rook");
            return false;
        }

        int[][] possibleMoves = new int[8][2];
        possibleMoves[0][0] = getSquare().getX() + 1;
        possibleMoves[0][1] = getSquare().getY() + 2;

        possibleMoves[1][0] = getSquare().getX() + 2;
        possibleMoves[1][1] = getSquare().getY() + 1;

        possibleMoves[2][0] = getSquare().getX() + 2;
        possibleMoves[2][1] = getSquare().getY() - 1;

        possibleMoves[3][0] = getSquare().getX() + 1;
        possibleMoves[3][1] = getSquare().getY() - 2;

        possibleMoves[4][0] = getSquare().getX() - 1;
        possibleMoves[4][1] = getSquare().getY() - 2;

        possibleMoves[5][0] = getSquare().getX() - 2;
        possibleMoves[5][1] = getSquare().getY() - 1;

        possibleMoves[6][0] = getSquare().getX() - 2;
        possibleMoves[6][1] = getSquare().getY() + 1;

        possibleMoves[7][0] = getSquare().getX() - 1;
        possibleMoves[7][1] = getSquare().getY() + 2;

        boolean isInPossibleMoves = false;

        for(int i = 0; i < 8; i++){
            if(possibleMoves[i][0] == to.getX() && possibleMoves[i][1] == to.getY()){
                isInPossibleMoves = true;
            }
        }

        return isInPossibleMoves;
    }
}
