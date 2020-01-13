package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board.Board;
import chess.engine.Board.Square;

import static java.lang.StrictMath.abs;

public class Pawn extends Piece {

    private boolean usedFirstMove;
    private int lastMovePos;

    public Pawn(Square square, PlayerColor color, PieceType type) {
        super(square, color, type);
        this.usedFirstMove = false;
    }

    public boolean isUsedFirstMove() {
        return usedFirstMove;
    }

    @Override
    public boolean isLegalMove(Board board, Square to) {

        int maxDistance = 1;

        if (!super.isLegalMove(board, to)) {
            return false;
        }

        //If the pawn is still at his initial position, he can move up to
        //2 squares.
        if(!isHasMoved()){
            maxDistance = 2;
        }

        //Pawn cannot go backward
        if (getColor() == PlayerColor.WHITE) {
            if (to.getY() <= getSquare().getY()) {
                return false;
            }
        } else {
            if (to.getY() >= getSquare().getY()) {
                return false;
            }
        }

        //Y distance is less than maxDistance
        if (abs(to.getY() - getSquare().getY()) > maxDistance) {
            return false;
        } else {
            //if move vertically
            if (to.getX() == getSquare().getX()) {
                if (to.getPiece() != null) {
                    return false;
                }
            } else {//if diagonal
                if ((abs(to.getY() - getSquare().getY()) > 1) || (abs(to.getX() - getSquare().getX()) > 1) || to.getPiece() == null) {

                    //Checking if is "prise en passant" when X is 7 we on the right side of the board so we cannot control rigth side
                    //or it will create a nullpointer. Same with x = 0.
                    if (getSquare().getX() == 0) {
                        Piece rightPiece = board.getBoard()[getSquare().getX() + 1][getSquare().getY()].getPiece();
                        if (isEnPassant(rightPiece, to, board, 1)) {
                            setHasMoved(true);
                            return true;
                        }
                    } else if (getSquare().getX() == 7) {
                        Piece leftPiece = board.getBoard()[getSquare().getX() - 1][getSquare().getY()].getPiece();
                        if (isEnPassant(leftPiece, to, board, 1)) {
                            setHasMoved(true);
                            return true;
                        }
                    } else {
                        Piece rightPiece = board.getBoard()[getSquare().getX() + 1][getSquare().getY()].getPiece();
                        if (isEnPassant(rightPiece, to, board, 1)) {
                            setHasMoved(true);
                            return true;
                        }
                        Piece leftPiece = board.getBoard()[getSquare().getX() - 1][getSquare().getY()].getPiece();
                        if (isEnPassant(leftPiece, to, board, 1)) {
                            setHasMoved(true);
                            return true;
                        }
                    }

                    return false;
                }
            }
        }


        if(maxDistance == 2 && abs(to.getY() - this.getSquare().getY()) == 2) {
            this.lastMovePos = board.getMoves().size();
            this.usedFirstMove = true;
        }
        return true;

    }

    /**
     * Check if there is a "en passant" situation.
     *
     * @param sidePiece : the piece on the left or the right of the actual Piece (it must be a PAWN)
     * @param to
     * @param board
     * @param n : we want to check if the sidePiece's last move was done jsut before this move. We need a paramter because we use it
     *          in the Board class where the move is already done so he has to add one more move.
     */
    public boolean isEnPassant(Piece sidePiece, Square to, Board board, int n){
        //All this shit for prise en passant
        if(sidePiece != null && sidePiece.getType() == PieceType.PAWN &&
                ((Pawn)sidePiece).isUsedFirstMove() && sidePiece.getColor() != this.getColor() && to.getPiece() == null &&
                to.getX() == sidePiece.getSquare().getX() && ((Pawn) sidePiece).lastMovePos + n == board.getMoves().size() && ((Pawn) sidePiece).isUsedFirstMove() == true){
            return true;
        }
        return false;
    }


    @Override
    public String textValue() {
        return "Pawn";
    }

    @Override
    public String toString() {
        return textValue();
    }
}
