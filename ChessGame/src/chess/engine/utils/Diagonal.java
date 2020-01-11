package chess.engine.utils;

import chess.engine.Board;
import chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

public class Diagonal implements Moveable{

    public List<Square> move(Board board,Square from){

        List<Square> squareList = new ArrayList<Square>();

        //top right diagonal
        int counter = 1;
        for(int i = from.getX() + 1, j = from.getY() + 1; i <= 7 && j <= 7;i++,j++){
            squareList.add(board.getBoard()[from.getX() + counter][from.getY() + counter]);
            if(board.getBoard()[from.getX() + counter][from.getY() + counter].getPiece() != null){
                break;
            }
            counter++;
        }
        //bottom right diagonal
        counter = 1;
        for(int i = from.getX() + 1, j = from.getY() - 1; i <= 7 && j >= 0;i++,j--){
            squareList.add(board.getBoard()[from.getX() + counter][from.getY() - counter]);
            if(board.getBoard()[from.getX() + counter][from.getY() - counter].getPiece() != null){
                break;
            }
            counter++;
        }
        //top left diagonal
        counter = 1;
        for(int i = from.getX() - 1, j = from.getY() + 1; i >= 0 && j <= 7;i--,j++){
            squareList.add(board.getBoard()[from.getX() - counter][from.getY() + counter]);
            if(board.getBoard()[from.getX() - counter][from.getY() + counter].getPiece() != null){
                break;
            }
            counter++;
        }
        //bottom left diagonal
        counter = 1;
        for(int i = from.getX() - 1, j = from.getY() - 1; i >= 0 && j >= 7;i--,j--){
            squareList.add(board.getBoard()[from.getX() - counter][from.getY() - counter]);
            if(board.getBoard()[from.getX() - counter][from.getY() - counter].getPiece() != null){
                break;
            }
            counter++;
        }

        return squareList;
    }





}
