package chess.engine.utils;

import chess.engine.Board;
import chess.engine.Square;

import java.util.ArrayList;

import static java.lang.StrictMath.abs;

public class Diagonal implements Moveable{

    private Square from;

    public Diagonal(Square from) {
        this.from = from;
    }

    public ArrayList<Square> move(Board board, Square to){

        ArrayList<Square> squareList = new ArrayList<Square>();
        int counter = 0;

        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        int posXDiff = abs(fromX - toX);

        posXDiff--;
        //right diagonals
        if (toX > fromX) {
            //top right diagonal
            if (toY > fromY) {
                while (posXDiff != 0) {
                    if (board.getBoard()[fromX + posXDiff][fromY + posXDiff].getPiece() == null) {
                        squareList.set(counter++, board.getBoard()[fromX + posXDiff][fromY + posXDiff]);
                    }
                    posXDiff--;
                }
            } else {//bottom right diagonal
                while (posXDiff != 0) {
                    if (board.getBoard()[fromX + posXDiff][fromY - posXDiff].getPiece() == null) {
                        squareList.set(counter++, board.getBoard()[fromX + posXDiff][fromY - posXDiff]);
                    }
                    posXDiff--;
                }
            }
        } else { //left diagonals
            //top left diagonal
            if (toY > fromY) {
                while (posXDiff != 0) {
                    if (board.getBoard()[fromX - posXDiff][fromY + posXDiff].getPiece() == null) {
                        squareList.set(counter++, board.getBoard()[fromX - posXDiff][fromY + posXDiff]);
                    }
                    posXDiff--;
                }
            } else {//bottom left diagonal
                while (posXDiff != 0) {
                    if (board.getBoard()[fromX - posXDiff][fromY - posXDiff].getPiece() == null) {
                        squareList.set(counter++, board.getBoard()[fromX - posXDiff][fromY - posXDiff]);
                    }
                    posXDiff--;
                }
            }
        }
        return squareList;
    }





}
