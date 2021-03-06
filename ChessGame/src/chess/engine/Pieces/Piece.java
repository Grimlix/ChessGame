package chess.engine.Pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board.Board;
import chess.engine.Board.Square;
import chess.engine.utils.Diagonal;
import chess.engine.utils.Horizontal_Vertical;
import chess.engine.utils.Moveable;

public abstract class Piece implements ChessView.UserChoice {

    private Square square;
    private PieceType type;
    private PlayerColor color;
    private Moveable arr[];
    private boolean hasMoved;

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }



    public Piece(Square square, PlayerColor color, PieceType type) {
        this.color = color;
        this.square = square;
        this.type = type;
        this.hasMoved = false;
        this.arr = new Moveable[2];

        Moveable diagonal = new Diagonal();
        Moveable horizontal_vertical = new Horizontal_Vertical();

        this.arr = new Moveable[2];
        this.arr[0] = diagonal;
        this.arr[1] = horizontal_vertical;
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

    public void move(Square square) {
        this.square = square;
    }

    public boolean isLegalMove(Board board, Square to) {
        if (to.getPiece() != null) {
            if (to.getPiece().getColor() == this.color) {
                return false;
            }
        }
        return true;
    }
}

