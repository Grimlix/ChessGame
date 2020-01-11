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

    private boolean isRock = false;

    private final int BOARD_HEIGHT = 8;
    private final int BOARD_LENGTH = 8;
    private final int BIG_CASTLING = 2;
    private final int SMALL_CASTLING = -2;

    //Constructor
    public Board() {
        this.board = new Square[BOARD_LENGTH][BOARD_HEIGHT];
        this.moves = new ArrayList<>();
        this.playerTurn = PlayerColor.WHITE;
    }

    //Getter
    public Square[][] getBoard() {
        return board;
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

        while (true) {
        }
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {

        Square from = board[fromX][fromY];
        Square to = board[toX][toY];

        //if the source square is empty it won't work
        if (from.getPiece() == null) {
            view.displayMessage("La case de départ est vide...");
            return false;
        }

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

        //Checking if there is a rock situation
        if (from.getPiece() instanceof King && ((King) from.getPiece()).isLegalRock(this, to)) {
            isRock = true;
        }

        //Checking if the piece allows the move, if true we change the player's turn and
        //the view. Otherwise returns false.
        if (from.getPiece().isLegalMove(this, to) || isRock) {

            //Creating a move and adding it to the list
            Move move = new Move(from, to, from.getPiece());
            moves.add(move);

            //Making the move before checking if there is a check situation
            moveMaker(from, to);

            if (!isCheck(playerTurn)) {

                //undo last move so it can be added to the view
                moveMaker(move.getTo(), move.getFrom());

                if (isRock) {
                    int distance = from.getX() - to.getX();
                    isRock = false;
                    switch (distance) {
                        case BIG_CASTLING:
                            //Display the rook move and change the squares on the board
                            moveDisplay(this.board[0][fromY], this.board[3][fromY]);
                            moveMaker(this.board[0][fromY], this.board[3][fromY]);
                            break;

                        case SMALL_CASTLING:
                            //Display the rook move and change the squares on the board
                            moveDisplay(this.board[7][fromY], this.board[5][fromY]);
                            moveMaker(this.board[7][fromY], this.board[5][fromY]);
                            break;
                    }
                }

                //Display the new move and change the squares on the board
                moveDisplay(from, to);
                moveMaker(from, to);

                //Changing the player's turn
                if (playerTurn == PlayerColor.WHITE) {
                    playerTurn = PlayerColor.BLACK;
                } else {
                    playerTurn = PlayerColor.WHITE;
                }

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
