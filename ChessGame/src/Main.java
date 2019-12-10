import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.views.console.ConsoleView;
import chess.views.gui.GUIView;

import chess.engine.ChessBoard;

import chess.engine.Piece;

public class Main {

    public static void main(String[] args){

        ChessController controller = new ChessController() {
            @Override
            public void start(ChessView view) {

                view.startView();

                ChessBoard newBoard = new ChessBoard();

                Piece[][] board = newBoard.getBoard();

                for(int i = 0; i < newBoard.getBOARD_HEIGHT(); i++){
                    for(int j = 0; j < newBoard.getBOARD_LENGTH(); j++){
                        if(board[i][j] != null){
                            view.putPiece(board[i][j].getType(), board[i][j].getColor(), i, j);
                        }
                    }
                }
            }

            @Override
            public boolean move(int fromX, int fromY, int toX, int toY) {
                return false;
            }

            @Override
            public void newGame() {

            }
        };

        //ChessView view = new ConsoleView(controller);
        ChessView view = new GUIView(controller);

        controller.start(view);

    }

}
