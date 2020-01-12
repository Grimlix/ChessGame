import chess.ChessController;
import chess.ChessView;
import chess.engine.Board.Board;
import chess.views.gui.GUIView;

public class Main {

    public static void main(String[] args) {

        ChessController board = new Board();
        ChessView view = new GUIView(board);
        //ChessView view = new ConsoleView(board);
        board.start(view);

    }

}
