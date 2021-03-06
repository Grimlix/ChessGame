package chess.engine.Board;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Pieces.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

public class Board implements ChessController {
    private Square[][] board;
    private ChessView view;
    private PlayerColor playerTurn;
    private List<Move> moves;
    private List<Move> movesTargetSquare;

    private boolean hasStarted;

    private static final int BOARD_HEIGHT = 8;
    private static final int BOARD_LENGTH = 8;
    private static final int BIG_CASTLE = 2;
    private static final int SMALL_CASTLE = -2;

    //Constructor
    public Board() {
        this.board = new Square[BOARD_LENGTH][BOARD_HEIGHT];
        this.moves = new ArrayList<>();
        this.movesTargetSquare = new ArrayList<>();
        this.playerTurn = PlayerColor.WHITE;
        this.hasStarted = false;
    }

    /**
     * undo last move
     */
    public void undo(){
        //if a move has been made
        if(!moves.isEmpty()){
            Move lastMove = moves.get(moves.size()-1);
            Move lastTargetMove = movesTargetSquare.get(movesTargetSquare.size()-1);


            if(lastTargetMove.getPiece() != null){
                //Set le Square de la piece du dernier move à son ancien Square
                lastTargetMove.getPiece().move(lastTargetMove.getFrom());
                //Set la Piece du Square du dernier move à son ancienne Piece
                lastTargetMove.getFrom().setPiece(lastTargetMove.getPiece());


                lastMove.getPiece().move(lastMove.getFrom());
                lastMove.getFrom().setPiece(lastMove.getPiece());

                view.putPiece(lastMove.getPiece().getType(), lastMove.getPiece().getColor(), lastMove.getFrom().getX(), lastMove.getFrom().getY());
                view.removePiece(lastMove.getTo().getX(),lastMove.getTo().getY());

                //Remet la pièce à son ancien emplacement sur la vue
                view.putPiece(lastTargetMove.getPiece().getType(), lastTargetMove.getPiece().getColor(), lastTargetMove.getFrom().getX(), lastTargetMove.getFrom().getY());






                //upgradeView(lastTargetMove.getFrom())







       /*         upgradeBoard(lastTargetMove.getAttacker().getSquare(),lastTargetMove.getFrom());
                upgradeBoard(lastMove.getAttacker().getSquare(),lastMove.getFrom());

                view.putPiece(lastTargetMove.getAttacker().getType(), lastTargetMove.getAttacker().getColor(), lastTargetMove.getFrom().getX(), lastTargetMove.getFrom().getY());
                view.removePiece(lastTargetMove.getTo().getX(),lastTargetMove.getTo().getY());
                view.putPiece(lastMove.getAttacker().getType(), lastMove.getAttacker().getColor(), lastMove.getFrom().getX(), lastMove.getFrom().getY());
                view.removePiece(lastMove.getTo().getX(),lastMove.getTo().getY());*/

                //si le dernier move etait un roi depuis son square de depart
                if(lastMove.getPiece() instanceof King){
                    //et qu'il a bougé qu'une seule fois
                    if(!hasPieceSeveralMoves(lastMove.getPiece())){

                        if(lastMove.getPiece() instanceof King && abs(lastMove.getFrom().getX() - lastMove.getTo().getX()) == 2){
                            lastMove.getTo().removePiece();
                            lastTargetMove.getTo().removePiece();
                            view.removePiece(lastTargetMove.getTo().getX(),lastTargetMove.getTo().getY());
                        }
                        //hasMoved = false, afin de pouvoir refaire un rock
                        ((King)lastMove.getPiece()).setHasMoved(false);
                    }
                }
                if(lastTargetMove.getPiece() instanceof Rook){
                    ((Rook)lastTargetMove.getPiece()).setHasMoved(false);
                }
            }else{
                //Undo last move
                upgradeView(lastMove.getTo(),lastMove.getFrom());
                upgradeBoard(lastMove.getTo(),lastMove.getFrom());
            }







           // undoEatenPiece(lastMove);

            playerTurn = lastMove.getPiece().getColor();

            movesTargetSquare.remove(lastTargetMove);
            moves.remove(lastMove);
        }
    }

    /**
     * Return true if piece has moved more than once
     */
    private boolean hasPieceSeveralMoves(Piece piece){
        int counter = 0;
        for(Move m : moves){
            if(m.getPiece() == piece){
                counter++;
            }
        }
        return counter != 1;
    }

    //Getter
    public int getBIG_CASTLE() {
        return BIG_CASTLE;
    }

    public Square[][] getBoard() {
        return board;
    }

    public int getSMALL_CASTLE() {
        return SMALL_CASTLE;
    }

    public List<Move> getMoves() {
        return moves;
    }

    @Override
    public void newGame() {
        playerTurn = PlayerColor.WHITE;
        this.hasStarted = true;
        initPieces();
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
        while(true){

        }

    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {

        if(!hasStarted){
            view.displayMessage("Il faut commencer une 'New Game' pour jouer");
            return false;
        }

        Square from = board[fromX][fromY];
        Square to = board[toX][toY];


        //if the source square is empty it won't work
        if (moveFromEmptySquare(from)) {
            return false;
        }
        //if wrong player is playing it won't work
        if (!checkPlayerTurn(from.getPiece().getColor())) {
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
        if (from.getPiece().isLegalMove(this, to) || (isRock && fromY == to.getY() && from.getPiece().getType() == PieceType.KING && fromX == 4 && !isCheck(playerTurn))) {

            //Creating a move and adding it to the list
            Move move = new Move(from, to, from.getPiece(),to.getPiece());
            moves.add(move);
            Piece targetPiece = to.getPiece();

            //Making the move before checking if there is a check situation
            upgradeBoard(from,to);

            if (!isCheck(playerTurn)) {
                //undo last move so it can be added to the view
                upgradeBoard(to,from);

                if (isRock) {
                    if (!moveRook(from, to)) {
                        view.displayMessage("Move Impossible : une des cases du chemin vous met en echec!");
                        return false;
                    }

                }

                //
                if(!checkEnPassant(move) && !isRock){
                    //add move to movesTargetSquare if not already done in checkEnPassant/isRock
                    movesTargetSquare.add(new Move(to,to,targetPiece,null));
                }

                //Display the new move and change the squares on the board
                upgradeView(from, to);
                upgradeBoard(from,to);

                checkPromotionState(to);
                changePlayerTurn();

                //Check if the move made a check situation
                if (isCheck(playerTurn)) {
                    this.view.displayMessage("Echec !");
                }
                return true;
            } else {
                //Undo last move
                upgradeBoard(to,from);
                moves.remove(move);
                this.view.displayMessage("Move Impossible : mise en echec.");
                return false;
            }
        } else {
            this.view.displayMessage("Move Impossible : interdit.");
            return false;
        }
    }



    /**
     * Check if there is a en passant state and if there is, removes teh pawn that got killed
     *
     * @param move move durant lequel il y a eu le en passant
     */
    private boolean checkEnPassant(Move move) {
        //If there is a "prise en passant" we have to delete the pawn
        //n = 2 because we check if the last move of the sidePiece was done just before this move, and we add
        //2 moves in between.
        Piece rightPiece = null;
        Piece leftPiece = null;

        boolean isEnPassant = false;

        //set side square
        if(move.getFrom().getX() == 0){
            rightPiece = board[move.getFrom().getX() + 1][move.getFrom().getY()].getPiece();
        }else if(move.getFrom().getX() == 7){
            leftPiece = board[move.getFrom().getX() - 1][move.getFrom().getY()].getPiece();
        }else{
            rightPiece = board[move.getFrom().getX() + 1][move.getFrom().getY()].getPiece();
            leftPiece = board[move.getFrom().getX() - 1][move.getFrom().getY()].getPiece();
        }

        //delete pawn if needed
        if (move.getFrom().getPiece().getType() == PieceType.PAWN) {
            if (move.getFrom().getX() == 0) {
                isEnPassant = removePieceEnPassant(move,rightPiece);
            } else if (move.getFrom().getX() == 7) {
               isEnPassant = removePieceEnPassant(move,leftPiece);
            } else {
               if(removePieceEnPassant(move,leftPiece)){
                   return true;
               }
               isEnPassant = removePieceEnPassant(move,rightPiece);

            }
        }
        return isEnPassant;

    }

    /**
     *  Remove Piece eaten by En Passant
     *
     *
     * @param piece Piece on Attacker Square
     *
     */
    private boolean removePieceEnPassant(Move move, Piece piece){
        if(piece != null && ((Pawn) move.getFrom().getPiece()).isEnPassant(piece,move.getTo(),this,2)){
            movesTargetSquare.add(new Move(piece.getSquare(),piece.getSquare(),piece, null));
            this.view.removePiece(piece.getSquare().getX(), piece.getSquare().getY());
            piece.getSquare().removePiece();
            return true;
        }
        return false;
    }



    /**
     * Check if there is a promotion state and if there is, ask the user and switch the new Piece.
     *
     *
     */
    private void checkPromotionState(Square promotionSquare) {
        if (!moves.isEmpty() && isInPromotionState(moves.get(moves.size() - 1))) {
            Piece promotePiece = view.askUser("Salut!", "En quelle piece promouvoir ?",
                     new Bishop(promotionSquare, promotionSquare.getPiece().getColor(), PieceType.BISHOP),
                     new Knight(promotionSquare, promotionSquare.getPiece().getColor(), PieceType.KNIGHT),
                     new Queen(promotionSquare, promotionSquare.getPiece().getColor(), PieceType.QUEEN),
                     new Rook(promotionSquare, promotionSquare.getPiece().getColor(), PieceType.ROOK));
            promotionSquare.getPiece().getSquare().removePiece();
            promotionSquare.setPiece(promotePiece);
            upgradeView(promotionSquare, promotionSquare);
        }
    }

    /**
     * Finds the king position, used to check if he's being checked
     *
     * @param color color of king to find
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
     * @param color color of king
     */
    private boolean isCheck(PlayerColor color) {
        Square king = getKingPos(color);
        for (Square[] x : board) {
            for (Square y : x) {
                if (y.getPiece() != null && y.getPiece().getColor() != color && y.getPiece().isLegalMove(this, king)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Switch the piece from 2 squares on the board.
     */

    private void upgradeBoard(Square from, Square to) {
        from.getPiece().move(to);
        to.setPiece(from.getPiece());
        from.removePiece();
    }


    /**
     * Switch the piece from 2 square on the view.
     */
    private void upgradeView(Square from, Square to) {
        this.view.removePiece(from.getX(), from.getY());
        this.view.putPiece(from.getPiece().getType(), from.getPiece().getColor(), to.getX(), to.getY());
    }

    /**
     * Checks which player's turn it is
     *
     * @param color of player that made move
     */
    private boolean checkPlayerTurn(PlayerColor color) {
        //Playing turn after turn, nobody can play more than once
        if (playerTurn == PlayerColor.WHITE) {
            if (color == PlayerColor.BLACK) {
                view.displayMessage("C'est aux blancs de jouer !");
                return false;
            }
        } else {
            if (color == PlayerColor.WHITE) {
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
     * @param from Square from which player tries to do a move
     */
    private boolean moveFromEmptySquare(Square from) {
        if ((from != null) && from.getPiece() == null) {
            this.view.displayMessage("La case de départ est vide...");
            return true;
        }
        return false;
    }

    /**
     * Check last move if it's a pawn on the last square in front of him
     * which would lead to a promotion state
     *
     * @param lastMove last move made
     */
    private boolean isInPromotionState(Move lastMove) {
        Piece piece = lastMove.getTo().getPiece();
        int y = lastMove.getTo().getY();
        if (piece.getType() == PieceType.PAWN) {
            if (y == 0 && piece.getColor() == PlayerColor.BLACK) {
                return true;
            } else{
                return y == 7 && piece.getColor() == PlayerColor.WHITE;
            }
        }
        return false;
    }


    /**
     * Check if the path between the king and the rook makes a check situation, if it is return false
     * else it changes the rook square on the board and on the view.
     *
     * @param from King Square
     * @param to Rook Square
     */
    private boolean moveRook(Square from, Square to) {

        int distance = from.getX() - to.getX();
        Square intermediateSquare;
        switch (distance) {
            case BIG_CASTLE:
                intermediateSquare = board[from.getX() - 1][from.getY()];
                Move moveKingLeft = new Move(from, intermediateSquare, from.getPiece(),intermediateSquare.getPiece());
                this.moves.add(moveKingLeft);
                upgradeBoard(from, intermediateSquare);
                if (!isCheck(playerTurn)) {
                    //undo
                    upgradeBoard(intermediateSquare,from);
                    moves.remove(moveKingLeft);
                    movesTargetSquare.add(new Move(board[0][from.getY()], board[3][from.getY()],board[0][from.getY()].getPiece(), board[3][from.getY()].getPiece()));
                    //Display the rook move and change the squares on the board
                    upgradeView(board[0][from.getY()], board[3][from.getY()]);
                    upgradeBoard(this.board[7][from.getY()], this.board[5][from.getY()]);
                } else {
                    //undo
                    upgradeBoard(intermediateSquare,from);
                    moves.remove(moveKingLeft);
                    return false;
                }
                break;

            case SMALL_CASTLE:
                intermediateSquare = board[from.getX() + 1][from.getY()];
                Move moveKingRight = new Move(from, intermediateSquare, from.getPiece(),intermediateSquare.getPiece());
                this.moves.add(moveKingRight);

                upgradeBoard(from,intermediateSquare);
                if (!isCheck(playerTurn)) {
                    //undo
                    upgradeBoard(intermediateSquare,from);
                    moves.remove(moveKingRight);
                    movesTargetSquare.add(new Move(board[7][from.getY()], board[5][from.getY()],board[7][from.getY()].getPiece(), board[5][from.getY()].getPiece() ));
                    //Display the rook move and change the squares on the board
                    upgradeView(this.board[7][from.getY()], this.board[5][from.getY()]);
                    upgradeBoard(this.board[7][from.getY()], this.board[5][from.getY()]);
                } else {
                    //undo
                    upgradeBoard(intermediateSquare,from);
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

        //clear the list move
        moves.clear();

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
