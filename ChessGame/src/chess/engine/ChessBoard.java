package chess.engine;


import chess.PlayerColor;

public class ChessBoard {


     private Piece[][] board;
     private final  int BOARD_HEIGHT = 8;
     private final int BOARD_LENGTH = 8;


     public ChessBoard(){

          this.board = new Piece[BOARD_HEIGHT][BOARD_LENGTH];

          board[4][0] = new King(4,0, PlayerColor.WHITE);
          board[4][7] = new King(4,7, PlayerColor.BLACK);

          board[3][0] = new Queen(3,0, PlayerColor.WHITE);
          board[3][7] = new Queen(3,7, PlayerColor.BLACK);

          board[2][0] = new Bishop(2,0, PlayerColor.WHITE);
          board[5][0] = new Bishop(5,0, PlayerColor.WHITE);
          board[2][7] = new Bishop(2,7, PlayerColor.BLACK);
          board[5][7] = new Bishop(5,7, PlayerColor.BLACK);

          board[0][0] = new Rook(0,0, PlayerColor.WHITE);
          board[7][0] = new Rook(7,0, PlayerColor.WHITE);
          board[0][7] = new Rook(0,7, PlayerColor.BLACK);
          board[7][7] = new Rook(7,7, PlayerColor.BLACK);

          board[1][0] = new Knight(1,0, PlayerColor.WHITE);
          board[6][0] = new Knight(6,0, PlayerColor.WHITE);
          board[1][7] = new Knight(1,7, PlayerColor.BLACK);
          board[6][7] = new Knight(6,7, PlayerColor.BLACK);

          board[0][1] = new Pawn(0,1, PlayerColor.WHITE);
          board[1][1] = new Pawn(1,1, PlayerColor.WHITE);
          board[2][1] = new Pawn(2,1, PlayerColor.WHITE);
          board[3][1] = new Pawn(3,1, PlayerColor.WHITE);
          board[4][1] = new Pawn(4,1, PlayerColor.WHITE);
          board[5][1] = new Pawn(5,1, PlayerColor.WHITE);
          board[6][1] = new Pawn(6,1, PlayerColor.WHITE);
          board[7][1] = new Pawn(7,1, PlayerColor.WHITE);

          board[0][6] = new Pawn(0,6, PlayerColor.BLACK);
          board[1][6] = new Pawn(1,6, PlayerColor.BLACK);
          board[2][6] = new Pawn(2,6, PlayerColor.BLACK);
          board[3][6] = new Pawn(3,6, PlayerColor.BLACK);
          board[4][6] = new Pawn(4,6, PlayerColor.BLACK);
          board[5][6] = new Pawn(5,6, PlayerColor.BLACK);
          board[6][6] = new Pawn(6,6, PlayerColor.BLACK);
          board[7][6] = new Pawn(7,6, PlayerColor.BLACK);
     }

     public Piece[][] getBoard(){
          return this.board;
     }

     public int getBOARD_HEIGHT(){
          return this.BOARD_HEIGHT;
     }

     public int getBOARD_LENGTH(){
          return this.BOARD_LENGTH;
     }







}
