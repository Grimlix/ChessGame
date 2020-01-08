package chess.engine;

public class Move {

    private Square from;
    private Square to;
    private Piece piece;

    public Move(Square from, Square to, Piece piece){
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    public Piece getPiece() {
        return piece;
    }
}
