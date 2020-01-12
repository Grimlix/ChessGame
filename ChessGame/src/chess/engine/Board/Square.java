package chess.engine.Board;

import chess.engine.Pieces.Piece;

public class Square{

    private Piece piece;
    private int x;
    private int y;

    public Square(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void setPiece(Piece piece) {
        this.piece = piece;
    }

    protected void removePiece() {
        this.piece = null;
    }
}
