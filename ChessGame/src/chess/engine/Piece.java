package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece {
    private int x;
    private int y;
    private PlayerColor color;
    private PieceType type;

    public Piece(int x, int y, PlayerColor color, PieceType type){
        this.x = x;
        this.y = y;
        this.color = color;
        this.type = type;
    }

    public boolean move(int fromX, int fromY, int toX, int toY){
        return true;
    }

    public PlayerColor getColor(){
        return this.color;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public PieceType getType(){
        return this.type;
    }

    public int fonctionDeTestGit(){
        return null;
    }


}
