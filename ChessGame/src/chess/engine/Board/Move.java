package chess.engine.Board;

import chess.engine.Pieces.Piece;

public class Move {

    private Square from;
    private Square to;
    private Piece piece;

    Move(Square from, Square to, Piece piece){
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    Square getFrom() {
        return from;
    }

    Square getTo() {
        return to;
    }

    Piece getPiece() {
        return piece;
    }
}
