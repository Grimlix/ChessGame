package chess.engine.utils;

import chess.engine.Board;
import chess.engine.Square;

import java.util.ArrayList;

public class Horizontal_Vertical implements Moveable {

    private Square from;

    public Horizontal_Vertical(Square from) {
        this.from = from;
    }

    public ArrayList<Square> move(Board board, Square to) {

        ArrayList<Square> squareList = new ArrayList<Square>();
        int counter = 0;

        if (from.getX() == to.getX()) {
            int i = from.getX();
            if (from.getY() > to.getY()) {
                for (int j = from.getY() - 1; j > to.getY(); j--) {
                    if (board.getBoard()[i][j].getPiece() == null) {
                        squareList.set(counter++, board.getBoard()[i][j]);
                    }
                }
            } else {
                for (int j = from.getY() + 1; j < to.getY(); j++) {
                    if (board.getBoard()[i][j].getPiece() == null) {
                        squareList.set(counter++, board.getBoard()[i][j]);
                    }
                }
            }
        } else {
            int j = from.getY();
            if (from.getX() > to.getX()) {
                for (int i = from.getX() - 1; i > to.getX(); i--) {
                    if (board.getBoard()[i][j].getPiece() == null) {
                        squareList.set(counter++, board.getBoard()[i][j]);
                    }
                }
            } else {
                for (int i = from.getX() + 1; i < to.getX(); i++) {
                    if (board.getBoard()[i][j].getPiece() == null) {
                        squareList.set(counter++, board.getBoard()[i][j]);
                    }
                }
            }
        }
        return squareList;
    }




}
