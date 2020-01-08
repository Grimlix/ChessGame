package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

abstract class Piece {

    private Square square;
    private PieceType type;
    private PlayerColor color;

    public Piece(Square square, PlayerColor color, PieceType type){
        this.color = color;
        this.square = square;
        this.type = type;
    }

    public PlayerColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public Square getSquare() {
        return square;
    }

    public void move(Square square){
        this.square = square;
    }

    public boolean isLegalMove(Board board,Square to) {
        if(to.getPiece() != null){
            if(to.getPiece().getColor() == this.color){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }



}

