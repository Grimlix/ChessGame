import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;
import chess.views.console.ConsoleView;
import chess.views.gui.GUIView;

public class Main {

    public static void main(String[] args) {
        /*
        //king
        King kingW = new King(4,0, PlayerColor.WHITE);
        King kingB = new King(4,7, PlayerColor.BLACK);

        Queen queenW = new Queen(3,0, PlayerColor.WHITE);
        Queen queenB = new Queen(3,7, PlayerColor.BLACK);

        Bishop bishopW1 = new Bishop(2,0, PlayerColor.WHITE);
        Bishop bishopW2 = new Bishop(5,0, PlayerColor.WHITE);
        Bishop bishopB1 = new Bishop(2,7, PlayerColor.BLACK);
        Bishop bishopB2 = new Bishop(5,7, PlayerColor.BLACK);

        Rook rookW1 = new Rook(0,0, PlayerColor.WHITE);
        Rook rookW2 = new Rook(7,0, PlayerColor.WHITE);
        Rook rookB1 = new Rook(0,7, PlayerColor.BLACK);
        Rook rookB2 = new Rook(7,7, PlayerColor.BLACK);

        Knight knightW1 = new Knight(1,0, PlayerColor.WHITE);
        Knight knightW2 = new Knight(6,0, PlayerColor.WHITE);
        Knight knightB1 = new Knight(1,7, PlayerColor.BLACK);
        Knight knightB2 = new Knight(6,7, PlayerColor.BLACK);

        Pawn pawnW1 = new Pawn(0,1, PlayerColor.WHITE);
        Pawn pawnW2 = new Pawn(1,1, PlayerColor.WHITE);
        Pawn pawnW3 = new Pawn(2,1, PlayerColor.WHITE);
        Pawn pawnW4 = new Pawn(3,1, PlayerColor.WHITE);
        Pawn pawnW5 = new Pawn(4,1, PlayerColor.WHITE);
        Pawn pawnW6 = new Pawn(5,1, PlayerColor.WHITE);
        Pawn pawnW7 = new Pawn(6,1, PlayerColor.WHITE);
        Pawn pawnW8 = new Pawn(7,1, PlayerColor.WHITE);

        Pawn pawnB1 = new Pawn(0,6, PlayerColor.BLACK);
        Pawn pawnB2 = new Pawn(1,6, PlayerColor.BLACK);
        Pawn pawnB3 = new Pawn(2,6, PlayerColor.BLACK);
        Pawn pawnB4 = new Pawn(3,6, PlayerColor.BLACK);
        Pawn pawnB5 = new Pawn(4,6, PlayerColor.BLACK);
        Pawn pawnB6 = new Pawn(5,6, PlayerColor.BLACK);
        Pawn pawnB7 = new Pawn(6,6, PlayerColor.BLACK);
        Pawn pawnB8 = new Pawn(7,6, PlayerColor.BLACK);


        ChessController controller = new ChessController() {
            @Override
            public void start(ChessView view) {
                view.startView();

                view.putPiece(kingW.getType(),kingW.getColor(),kingW.getX(), kingW.getY());
                view.putPiece(kingB.getType(),kingB.getColor(),kingB.getX(), kingB.getY());

                view.putPiece(queenW.getType(),queenW.getColor(),queenW.getX(), queenW.getY());
                view.putPiece(queenB.getType(),queenB.getColor(),queenB.getX(), queenB.getY());

                view.putPiece(bishopW1.getType(),bishopW1.getColor(),bishopW1.getX(), bishopW1.getY());
                view.putPiece(bishopW2.getType(),bishopW2.getColor(),bishopW2.getX(), bishopW2.getY());
                view.putPiece(bishopB1.getType(),bishopB1.getColor(),bishopB1.getX(), bishopB1.getY());
                view.putPiece(bishopB2.getType(),bishopB2.getColor(),bishopB2.getX(), bishopB2.getY());

                view.putPiece(rookW1.getType(),rookW1.getColor(),rookW1.getX(), rookW1.getY());
                view.putPiece(rookW2.getType(),rookW2.getColor(),rookW2.getX(), rookW2.getY());
                view.putPiece(rookB1.getType(),rookB1.getColor(),rookB1.getX(), rookB1.getY());
                view.putPiece(rookB2.getType(),rookB2.getColor(),rookB2.getX(), rookB2.getY());

                view.putPiece(knightW1.getType(),knightW1.getColor(),knightW1.getX(), knightW1.getY());
                view.putPiece(knightW2.getType(),knightW2.getColor(),knightW2.getX(), knightW2.getY());
                view.putPiece(knightB1.getType(),knightB1.getColor(),knightB1.getX(), knightB1.getY());
                view.putPiece(knightB2.getType(),knightB2.getColor(),knightB2.getX(), knightB2.getY());

                view.putPiece(pawnW1.getType(),pawnW1.getColor(),pawnW1.getX(), pawnW1.getY());
                view.putPiece(pawnW2.getType(),pawnW2.getColor(),pawnW2.getX(), pawnW2.getY());
                view.putPiece(pawnW3.getType(),pawnW3.getColor(),pawnW3.getX(), pawnW3.getY());
                view.putPiece(pawnW4.getType(),pawnW4.getColor(),pawnW4.getX(), pawnW4.getY());
                view.putPiece(pawnW5.getType(),pawnW5.getColor(),pawnW5.getX(), pawnW5.getY());
                view.putPiece(pawnW6.getType(),pawnW6.getColor(),pawnW6.getX(), pawnW6.getY());
                view.putPiece(pawnW7.getType(),pawnW7.getColor(),pawnW7.getX(), pawnW7.getY());
                view.putPiece(pawnW8.getType(),pawnW8.getColor(),pawnW8.getX(), pawnW8.getY());

                view.putPiece(pawnB1.getType(),pawnB1.getColor(),pawnB1.getX(), pawnB1.getY());
                view.putPiece(pawnB2.getType(),pawnB2.getColor(),pawnB2.getX(), pawnB2.getY());
                view.putPiece(pawnB3.getType(),pawnB3.getColor(),pawnB3.getX(), pawnB3.getY());
                view.putPiece(pawnB4.getType(),pawnB4.getColor(),pawnB4.getX(), pawnB4.getY());
                view.putPiece(pawnB5.getType(),pawnB5.getColor(),pawnB5.getX(), pawnB5.getY());
                view.putPiece(pawnB6.getType(),pawnB6.getColor(),pawnB6.getX(), pawnB6.getY());
                view.putPiece(pawnB7.getType(),pawnB7.getColor(),pawnB7.getX(), pawnB7.getY());
                view.putPiece(pawnB8.getType(),pawnB8.getColor(),pawnB8.getX(), pawnB8.getY());









            }

            @Override
            public boolean move(int fromX, int fromY, int toX, int toY) {
                return false;
            }

            @Override
            public void newGame() {
            }
        };

       // ChessView view = new ConsoleView(controller);


        ChessView view = new GUIView(controller);
        //view.putPiece(PieceType.BISHOP, WHITE, 2, 3);
        controller.start(view);
    */


        ChessController board = new Board();
        ChessView view = new GUIView(board);
        board.start(view);
    }

}
