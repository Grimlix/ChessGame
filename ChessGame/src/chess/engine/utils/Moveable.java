package chess.engine.utils;

import chess.engine.Board;
import chess.engine.Square;
import java.util.List;

public interface Moveable {

    List<Square> move(Board board, Square from, int maxDist);



}
