package chess.engine.Board;

import chess.engine.Pieces.Piece;

class Move {

    private Square from;
    private Square to;
    private Piece piece;
    Move(Square from, Square to, Piece attacker, Piece defender){
        this.from = from;
        this.to = to;
        this.piece = attacker;
    }

    protected Square getFrom() {
        return from;
    }

    protected Square getTo() {
        return to;
    }

    public Piece getPiece() {
        return piece;
    }

}
