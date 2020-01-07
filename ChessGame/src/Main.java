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

        ChessController board = new ChessBoard();

        //ChessView view = new ConsoleView(controller);
        ChessView view = new GUIView(board);
        board.start(view);

    }











}
