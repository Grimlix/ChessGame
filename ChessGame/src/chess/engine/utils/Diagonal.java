package chess.engine.utils;

import chess.engine.Board;
import chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.max;

public class Diagonal implements Moveable{

    public List<Square> move(Board board,Square from, int maxDist){

        List<Square> squareList = new ArrayList<Square>();

        //top right diagonal
        int counter = 1;
        for(int i = from.getX() + 1, j = from.getY() + 1, k = 0; i <= 7 && j <= 7 && k < maxDist;i++,j++,k++){
            squareList.add(board.getBoard()[from.getX() + counter][from.getY() + counter]);
            if(board.getBoard()[from.getX() + counter][from.getY() + counter].getPiece() != null){
                break;
            }
            counter++;
        }
        //bottom right diagonal
        counter = 1;
        for(int i = from.getX() + 1, j = from.getY() - 1,k = 0; i <= 7 && j >= 0 && k < maxDist;i++,j--,k++){
            squareList.add(board.getBoard()[from.getX() + counter][from.getY() - counter]);
            if(board.getBoard()[from.getX() + counter][from.getY() - counter].getPiece() != null){
                break;
            }
            counter++;
        }
        //top left diagonal
        counter = 1;
        for(int i = from.getX() - 1, j = from.getY() + 1, k = 0; i >= 0 && j <= 7 && k < maxDist;i--,j++,k++){
            squareList.add(board.getBoard()[from.getX() - counter][from.getY() + counter]);
            if(board.getBoard()[from.getX() - counter][from.getY() + counter].getPiece() != null){
                break;
            }
            counter++;
        }
        //bottom left diagonal
        counter = 1;
        for(int i = from.getX() - 1, j = from.getY() - 1, k = 0; i >= 0 && j >= 0 && k < maxDist;i--,j--,k++){
            squareList.add(board.getBoard()[from.getX() - counter][from.getY() - counter]);
            if(board.getBoard()[from.getX() - counter][from.getY() - counter].getPiece() != null){
                break;
            }
            counter++;
        }

        return squareList;
    }





}
