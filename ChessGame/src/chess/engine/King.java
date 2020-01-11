package chess.engine;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.utils.Moveable;
import java.util.List;

public class King extends Piece {

    private static final int DISTANCE_MAX = 2;
    private boolean hasMoved;

    public King(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);
        this.hasMoved = false;
    }

    private boolean checkRook(Board board, int x, int i_start, int i_end) {
        if (!this.hasMoved) {
            if (!((Rook) board.getBoard()[x][getSquare().getY()].getPiece()).getHasMoved()) {
                //check if square are empty
                for (int i = i_start; i < i_end; i++) {
                    if (board.getBoard()[i][getSquare().getY()].getPiece() != null) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    //verifier que les cases sur lesquelles le roi se deplace ne sont pas en echecs
    public boolean isLegalRock(Board board, Square to) {

        int distance = this.getSquare().getX() - to.getX();
        if (distance == board.getBIG_CASTLE()) {
            return checkRook(board, 0, 1, 4);
        } else if(distance == board.getSmallCastle()){
            return checkRook(board, 7, 5, 7);
        }else{
            return false;
        }
    }

    //factoriser avec deltaX detlaY
    public boolean isLegalMove(Board board, Square to) {

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        Moveable diag = this.getArr()[0];
        Moveable horizontal_vertical = this.getArr()[1];

        List<Square> possibleSquare_1 = horizontal_vertical.move(board, this.getSquare(), 1);
        List<Square> possibleSquare_2 = diag.move(board, this.getSquare(), 1);

        possibleSquare_1.addAll(possibleSquare_2);

        if (possibleSquare_1.contains(to)) {
            this.hasMoved = true;
            return true;
        }

        return false;
    }

    @Override
    public String textValue() {
        return "King";
    }

    @Override
    public String toString() {
        return textValue();
    }
}
