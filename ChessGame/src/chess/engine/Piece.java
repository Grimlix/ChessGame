package chess.engine;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.utils.Diagonal;
import chess.engine.utils.Horizontal_Vertical;
import chess.engine.utils.Moveable;

import java.util.ArrayList;
import java.util.List;

abstract class Piece {

    private Square square;
    private PieceType type;
    private PlayerColor color;

    private Moveable arr[];

    public Piece(Square square, PlayerColor color, PieceType type){
        this.color = color;
        this.square = square;
        this.type = type;

        this.arr = new Moveable[2];

        Moveable diagonal = new Diagonal();
        Moveable horizontal_vertical = new Horizontal_Vertical();

        this.arr[0] = diagonal;
        this.arr[1]= horizontal_vertical;
    }

    public Moveable[] getArr() {
        return arr;
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

