package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

public class Board implements ChessController {

    private Square[][] board;
    private ChessView view;
    private PlayerColor playerTurn;
    private List<Move> moves;

    private static final int BOARD_HEIGHT = 8;
    private static final int BOARD_LENGTH = 8;
    private static final int BIG_CASTLE = 2;
    private static final int SMALL_CASTLE = -2;

    //Constructor
    public Board() {
        this.board = new Square[BOARD_LENGTH][BOARD_HEIGHT];
        this.moves = new ArrayList<>();
        this.playerTurn = PlayerColor.WHITE;
    }

    //Getter
    public int getBIG_CASTLE() {
        return BIG_CASTLE;
    }

    //Getter
    public Square[][] getBoard() {
        return board;
    }

    public int getSMALL_CASTLE(){
        return SMALL_CASTLE;
    }

    @Override
    public void newGame() {
        playerTurn = PlayerColor.WHITE;
        initPieces();
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {

        Square from = board[fromX][fromY];
        Square to = board[toX][toY];

        //if the source square is empty it won't work
        if (moveFromEmptySquare(from)) {
            return false;
        }
        //if wrong player is playing it won't work
        if (!checkPlayerTurn(from)) {
            return false;
        }

        //Checking if there is a rock situation
        boolean isRock = false;
        if (from.getPiece() instanceof King && ((King) from.getPiece()).isLegalRock(this, to)) {
            isRock = true;
        }


        //Checking if the piece allows the move, if true we change the player's turn and
        //the view. Otherwise returns false. We need to control that if there is a rock situation, it comes from the king
        //and that it does not go upwards but only sideways
        if (from.getPiece().isLegalMove(this, to) || (isRock && fromY == to.getY() && from.getPiece().getType() == PieceType.KING && fromX == 4)) {

            //Creating a move and adding it to the list
            Move move = new Move(from, to, from.getPiece());
            this.moves.add(move);

            //Making the move before checking if there is a check situation
            moveMaker(from, to);

            if (!isCheck(playerTurn)) {

                //undo last move so it can be added to the view
                moveMaker(move.getTo(), move.getFrom());


                if (isRock) {
                   if(!moveRook(from, to)){
                       view.displayMessage("Move Impossible : Une des cases du chemin vous met en echec!");
                       return false;
                   }
                }

                //Display the new move and change the squares on the board
                moveDisplay(from, to);
                moveMaker(from, to);

                if (!moves.isEmpty() && isInPromotionState(moves.get(moves.size() - 1))) {
                    Piece promotePiece = null;

                    promotePiece = view.askUser("Salut!", "En quelle piece promouvoir ?",
                            (Piece) new Bishop(to, to.getPiece().getColor(), PieceType.BISHOP),
                            (Piece) new Knight(to, to.getPiece().getColor(), PieceType.KNIGHT),
                            (Piece) new Queen(to, to.getPiece().getColor(), PieceType.QUEEN),
                            (Piece) new Rook(to, to.getPiece().getColor(), PieceType.ROOK));
                    to.getPiece().getSquare().removePiece();
                    to.setPiece(promotePiece);
                    moveDisplay(to, to);
                }

                changePlayerTurn();

                //Check if the move made a check situation
                if (isCheck(playerTurn)) {
                    this.view.displayMessage("Echec !");
                }


                return true;
            } else {
                //Undo last move
                moveMaker(move.getTo(), move.getFrom());
                moves.remove(move);
                this.view.displayMessage("Move Impossible : mise en echec.");
                return false;
            }

        } else {
            this.view.displayMessage("Move Impossible : Interdit.");
            return false;
        }


    }

    /**
     * Finds the king position, used to check if he's being checked
     *
     * @param color
     */
    private Square getKingPos(PlayerColor color) {
        for (Square[] x : board) {
            for (Square y : x) {
                if (y.getPiece() != null && y.getPiece().getType() == PieceType.KING && y.getPiece().getColor() == color) {
                    return y;
                }
            }
        }
        return null;
    }

    /**
     * Returns true if the king of one player (color) is in a check situation
     *
     * @param color
     */
    private boolean isCheck(PlayerColor color) {
        Square king = getKingPos(color);
        for (Square[] x : board) {
            for (Square y : x) {
                if (y.getPiece() != null && y.getPiece().getColor() != color && y.getPiece().isLegalMove(this, king)) {
                    System.out.println("Le salaud qui me met en echec de l'espace : " + y.getPiece() + " couleur : " + y.getPiece().getColor() + " coordonnée : " + y.getY() + ":" + y.getX());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Switch the piece from 2 squares on the board.
     *
     * @param from
     * @param to
     */
    private void moveMaker(Square from, Square to) {
        from.getPiece().move(to);
        to.setPiece(from.getPiece());
        from.removePiece();
    }

    /**
     * Switch the piece from 2 square on the view.
     *
     * @param from
     * @to
     */
    private void moveDisplay(Square from, Square to) {
        this.view.removePiece(from.getX(), from.getY());
        this.view.putPiece(from.getPiece().getType(), from.getPiece().getColor(), to.getX(), to.getY());
    }

    /**
     * Checks which player's turn it is
     *
     * @param from
     */
    private boolean checkPlayerTurn(Square from) {
        //Playing turn after turn, nobody can play more than once
        if (playerTurn == PlayerColor.WHITE) {
            if (from.getPiece().getColor() == PlayerColor.BLACK) {
                view.displayMessage("C'est aux blancs de jouer !");
                return false;
            }
        } else {
            if (from.getPiece().getColor() == PlayerColor.WHITE) {
                view.displayMessage("C'est aux noirs de jouer !");
                return false;
            }
        }
        return true;
    }

    /**
     * Change player's turn
     */
    private void changePlayerTurn() {
        //Changing the player's turn
        if (playerTurn == PlayerColor.WHITE) {
            playerTurn = PlayerColor.BLACK;
        } else {
            playerTurn = PlayerColor.WHITE;
        }
    }

    /**
     * Check if the player tries to move an empty square
     *
     * @param from
     */
    private boolean moveFromEmptySquare(Square from) {
        if (from.getPiece() == null) {
            this.view.displayMessage("La case de départ est vide...");
            return true;
        }
        return false;
    }

    /**
     * Check last move if it's a pawn on the last square in front of him
     * which would lead to a promotion state
     *
     * @param lastMove
     */
    private boolean isInPromotionState(Move lastMove) {
        if (lastMove.getTo().getPiece().getType() == PieceType.PAWN) {
            if (lastMove.getTo().getY() == 0 && lastMove.getTo().getPiece().getColor() == PlayerColor.BLACK) {
                return true;
            } else if (lastMove.getTo().getY() == 7 && lastMove.getTo().getPiece().getColor() == PlayerColor.WHITE) {
                return true;
            }
        }
        return false;
    }


    /**
     * Check if the path between the king and the rook makes a check situation, if it is return false
     * else it changes the rook sqaure on the board and on the view.
     *
     * @param from
     * @param to
     */
    private boolean moveRook(Square from, Square to) {

        int distance = from.getX() - to.getX();

        switch (distance) {
            case BIG_CASTLE:
                Move moveKingLeft = new Move(from, this.board[from.getX() - 1][from.getY()], from.getPiece());
                this.moves.add(moveKingLeft);
                moveMaker(from, this.board[from.getX() - 1][from.getY()]);
                if (!isCheck(playerTurn)) {
                    //undo
                    moveMaker(moveKingLeft.getTo(), moveKingLeft.getFrom());
                    moves.remove(moveKingLeft);

                    //Display the rook move and change the squares on the board
                    moveDisplay(this.board[0][from.getY()], this.board[3][from.getY()]);
                    moveMaker(this.board[0][from.getY()], this.board[3][from.getY()]);
                }else{
                    //undo
                    moveMaker(moveKingLeft.getTo(), moveKingLeft.getFrom());
                    moves.remove(moveKingLeft);
                    return false;
                }
                break;

            case SMALL_CASTLE:
                Move moveKingRight = new Move(from, this.board[from.getX() + 1][from.getY()], from.getPiece());
                this.moves.add(moveKingRight);

                moveMaker(from, this.board[from.getX() + 1][from.getY()]);
                if (!isCheck(playerTurn)) {
                    //undo
                    moveMaker(moveKingRight.getTo(), moveKingRight.getFrom());
                    moves.remove(moveKingRight);


                    //Display the rook move and change the squares on the board
                    moveDisplay(this.board[7][from.getY()], this.board[5][from.getY()]);
                    moveMaker(this.board[7][from.getY()], this.board[5][from.getY()]);
                }else{
                    //undo
                    moveMaker(moveKingRight.getTo(), moveKingRight.getFrom());
                    moves.remove(moveKingRight);
                    return false;
                }
                break;
        }
        return true;
    }


    /**
     * Empty the whole board and the view, creates all the pieces and the squares and places the pieces at the rigth
     * place and then changes the view.
     */
    private void initPieces() {
        //Deleting all the piece on the board and on the view
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                this.view.removePiece(i, j);
                board[i][j] = null;
            }
        }

        //CREATING ALL THE PIECES
        Piece kingW = new King(null, PlayerColor.WHITE, PieceType.KING);
        Piece kingB = new King(null, PlayerColor.BLACK, PieceType.KING);

        Piece queenW = new Queen(null, PlayerColor.WHITE, PieceType.QUEEN);
        Piece queenB = new Queen(null, PlayerColor.BLACK, PieceType.QUEEN);

        Piece bishopW1 = new Bishop(null, PlayerColor.WHITE, PieceType.BISHOP);
        Piece bishopW2 = new Bishop(null, PlayerColor.WHITE, PieceType.BISHOP);
        Piece bishopB1 = new Bishop(null, PlayerColor.BLACK, PieceType.BISHOP);
        Piece bishopB2 = new Bishop(null, PlayerColor.BLACK, PieceType.BISHOP);

        Piece rookW1 = new Rook(null, PlayerColor.WHITE, PieceType.ROOK);
        Piece rookW2 = new Rook(null, PlayerColor.WHITE, PieceType.ROOK);
        Piece rookB1 = new Rook(null, PlayerColor.BLACK, PieceType.ROOK);
        Piece rookB2 = new Rook(null, PlayerColor.BLACK, PieceType.ROOK);

        Piece knightW1 = new Knight(null, PlayerColor.WHITE, PieceType.KNIGHT);
        Piece knightW2 = new Knight(null, PlayerColor.WHITE, PieceType.KNIGHT);
        Piece knightB1 = new Knight(null, PlayerColor.BLACK, PieceType.KNIGHT);
        Piece knightB2 = new Knight(null, PlayerColor.BLACK, PieceType.KNIGHT);

        Piece pawnW1 = new Pawn(null, PlayerColor.WHITE, PieceType.PAWN);
        Piece pawnW2 = new Pawn(null, PlayerColor.WHITE, PieceType.PAWN);
        Piece pawnW3 = new Pawn(null, PlayerColor.WHITE, PieceType.PAWN);
        Piece pawnW4 = new Pawn(null, PlayerColor.WHITE, PieceType.PAWN);
        Piece pawnW5 = new Pawn(null, PlayerColor.WHITE, PieceType.PAWN);
        Piece pawnW6 = new Pawn(null, PlayerColor.WHITE, PieceType.PAWN);
        Piece pawnW7 = new Pawn(null, PlayerColor.WHITE, PieceType.PAWN);
        Piece pawnW8 = new Pawn(null, PlayerColor.WHITE, PieceType.PAWN);

        Piece pawnB1 = new Pawn(null, PlayerColor.BLACK, PieceType.PAWN);
        Piece pawnB2 = new Pawn(null, PlayerColor.BLACK, PieceType.PAWN);
        Piece pawnB3 = new Pawn(null, PlayerColor.BLACK, PieceType.PAWN);
        Piece pawnB4 = new Pawn(null, PlayerColor.BLACK, PieceType.PAWN);
        Piece pawnB5 = new Pawn(null, PlayerColor.BLACK, PieceType.PAWN);
        Piece pawnB6 = new Pawn(null, PlayerColor.BLACK, PieceType.PAWN);
        Piece pawnB7 = new Pawn(null, PlayerColor.BLACK, PieceType.PAWN);
        Piece pawnB8 = new Pawn(null, PlayerColor.BLACK, PieceType.PAWN);

        //CREATING ALL THE SQUARES WITH THE CORRESPONDING PIECE
        board[4][0] = new Square(kingW, 4, 0);
        kingW.move(board[4][0]);
        board[4][7] = new Square(kingB, 4, 7);
        kingB.move(board[4][7]);
        board[3][0] = new Square(queenW, 3, 0);
        queenW.move(board[3][0]);
        board[3][7] = new Square(queenB, 3, 7);
        queenB.move(board[3][7]);

        board[2][0] = new Square(bishopW1, 2, 0);
        bishopW1.move(board[2][0]);
        board[5][0] = new Square(bishopW2, 5, 0);
        bishopW2.move(board[5][0]);
        board[2][7] = new Square(bishopB1, 2, 7);
        bishopB1.move(board[2][7]);
        board[5][7] = new Square(bishopB2, 5, 7);
        bishopB2.move(board[5][7]);

        board[0][0] = new Square(rookW1, 0, 0);
        rookW1.move(board[0][0]);
        board[7][0] = new Square(rookW2, 7, 0);
        rookW2.move(board[7][0]);
        board[0][7] = new Square(rookB1, 0, 7);
        rookB1.move(board[0][7]);
        board[7][7] = new Square(rookB2, 7, 7);
        rookB2.move(board[7][7]);

        board[1][0] = new Square(knightW1, 1, 0);
        knightW1.move(board[1][0]);
        board[6][0] = new Square(knightW2, 6, 0);
        knightW2.move(board[6][0]);
        board[1][7] = new Square(knightB1, 1, 7);
        knightB1.move(board[1][7]);
        board[6][7] = new Square(knightB2, 6, 7);
        knightB2.move(board[6][7]);

        board[0][1] = new Square(pawnW1, 0, 1);
        pawnW1.move(board[0][1]);
        board[1][1] = new Square(pawnW2, 1, 1);
        pawnW2.move(board[1][1]);
        board[2][1] = new Square(pawnW3, 2, 1);
        pawnW3.move(board[2][1]);
        board[3][1] = new Square(pawnW4, 3, 1);
        pawnW4.move(board[3][1]);
        board[4][1] = new Square(pawnW5, 4, 1);
        pawnW5.move(board[4][1]);
        board[5][1] = new Square(pawnW6, 5, 1);
        pawnW6.move(board[5][1]);
        board[6][1] = new Square(pawnW7, 6, 1);
        pawnW7.move(board[6][1]);
        board[7][1] = new Square(pawnW8, 7, 1);
        pawnW8.move(board[7][1]);

        board[0][6] = new Square(pawnB1, 0, 6);
        pawnB1.move(board[0][6]);
        board[1][6] = new Square(pawnB2, 1, 6);
        pawnB2.move(board[1][6]);
        board[2][6] = new Square(pawnB3, 2, 6);
        pawnB3.move(board[2][6]);
        board[3][6] = new Square(pawnB4, 3, 6);
        pawnB4.move(board[3][6]);
        board[4][6] = new Square(pawnB5, 4, 6);
        pawnB5.move(board[4][6]);
        board[5][6] = new Square(pawnB6, 5, 6);
        pawnB6.move(board[5][6]);
        board[6][6] = new Square(pawnB7, 6, 6);
        pawnB7.move(board[6][6]);
        board[7][6] = new Square(pawnB8, 7, 6);
        pawnB8.move(board[7][6]);

        //CREATING ALL THE SQUARE WHERE THERE ARE NO PIECES
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 2; j < 6; j++) {
                board[i][j] = new Square(null, i, j);
            }
        }

        //PLAYING ALL THE PIECE ON THE VIEW
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if (board[i][j].getPiece() != null) {
                    this.view.putPiece(board[i][j].getPiece().getType(), board[i][j].getPiece().getColor(), i, j);
                }
            }
        }

    }

}
