package chess.engine.Board;

import chess.engine.Pieces.Piece;

class Move {

    private Square from;
    private Square to;
    private Piece attacker;
    private Piece defender;
    Move(Square from, Square to, Piece attacker, Piece defender){
        this.from = from;
        this.to = to;
        this.attacker = attacker;
        this.defender = defender;
    }

    protected Square getFrom() {
        return from;
    }

    protected Square getTo() {
        return to;
    }

    public Piece getAttacker() {
        return attacker;
    }

    public Piece getDefender(){
        return defender;
    }
}
