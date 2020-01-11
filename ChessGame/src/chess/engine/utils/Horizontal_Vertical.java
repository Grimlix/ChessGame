package chess.engine.utils;

import chess.engine.Board;
import chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

public class Horizontal_Vertical implements Moveable {

    public List<Square> move(Board board, Square from, int maxDist) {

        List<Square> squareList = new ArrayList<Square>();

        //vertical top
        for (int i = from.getY() + 1,j = 0; i <= 7 && j < maxDist; i++,j++) {
            //add the square to the list
            squareList.add(board.getBoard()[from.getX()][i]);
            //if the square has a piece we leave this way
            if (board.getBoard()[from.getX()][i].getPiece() != null) {
                break;
            }
        }

        //vertical down
        for (int i = from.getY() - 1, j = 0; i >= 0 && j < maxDist; i--,j++) {
            //add the square to the list
            squareList.add(board.getBoard()[from.getX()][i]);
            //if the square has a piece we leave this way
            if (board.getBoard()[from.getX()][i].getPiece() != null) {
                break;
            }
        }

        //horizontal left
        for (int i = from.getX() - 1,j = 0; i >= 0 && j < maxDist; i--,j++) {
            //add the square to the list
            squareList.add(board.getBoard()[i][from.getY()]);
            //if the square has a piece we leave this way
            if (board.getBoard()[i][from.getY()].getPiece() != null) {
                break;
            }
        }

        //horizontal right
        for (int i = from.getX() + 1, j = 0; i <= 7 && j < maxDist; i++,j++) {
            //add the square to the list
            squareList.add(board.getBoard()[i][from.getY()]);
            //if the square has a piece we leave this way
            if (board.getBoard()[i][from.getY()].getPiece() != null) {
                break;
            }
        }

        return squareList;
    }

}
