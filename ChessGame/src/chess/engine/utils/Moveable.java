package chess.engine.utils;

import chess.engine.Board.Board;
import chess.engine.Board.Square;
import java.util.List;

public interface Moveable {

    List<Square> move(Board board, Square from, int maxDist);



}
