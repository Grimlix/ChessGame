package chess.engine;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.utils.Moveable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.StrictMath.abs;

public class Queen extends Piece{

    public Queen(Square square, PlayerColor color,PieceType type){
        super(square,color,type);

    }

    public boolean isLegalMove(Board board, Square to){

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        System.out.println("JE suis dans la rein");

        Moveable diag = this.getArr()[0];
        Moveable horizontal_vertical = this.getArr()[1];

        List<Square> possibleSquare_1 =  new ArrayList<Square>();
        List<Square> possibleSquare_2 =  new ArrayList<Square>();

        possibleSquare_1 = horizontal_vertical.move(board, to);
        possibleSquare_2 = diag.move(board, to);

        possibleSquare_1.addAll(possibleSquare_2);

        for(int i = 0; i < possibleSquare_1.size(); i++){
            if(possibleSquare_1.get(i) == null){
                System.out.println("Je suis vide");
            }
            System.out.println("quel move : "  + possibleSquare_1.get(i));
        }

        if(possibleSquare_1.isEmpty()){
            return false;
        }else {
            return true;
        }



    }



}
