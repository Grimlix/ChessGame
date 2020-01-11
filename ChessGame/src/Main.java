import chess.ChessController;
import chess.ChessView;
import chess.engine.*;
import chess.views.gui.GUIView;

public class Main {

    public static void main(String[] args) {

        ChessController board = new Board();
        ChessView view = new GUIView(board);
        board.start(view);

    }

}
