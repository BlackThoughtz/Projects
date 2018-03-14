/**
 * Class for the main Game Board
 * @author  David Butler netID drb197
 */
public class theBoard {
    /*matrices for holding pieces and printing out value*/
    gamePiece[][] gameBoard;
    String emptyBoard[][];
    String[] abc = {"a", "b", "c", "d", "e", "f", "g", "h"};


    public theBoard() {
        gameBoard = new gamePiece[8][8];
        emptyBoard = new String[8][8];



        /* Initializes an empty array to beging making pieces*/
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                gameBoard[i][j] = null;
            }
        }

        setGameBoard(gameBoard);
        createEmptyBoard();
    }

    /**
     * Method used to create put the pieces in starting spot
     * @param gameBoard
     */
    private void setGameBoard(gamePiece[][] gameBoard) {
        int i;
        //Initialize Pawn
        for (i = 0; i < gameBoard.length; i++) {
            gameBoard[1][i] = new Pawn(true);
            gameBoard[6][i] = new Pawn(false);
        }

        //initialize other White pieces
        gameBoard[0][0] = new Rook(true);
        gameBoard[0][1] = new Knight(true);
        gameBoard[0][2] = new Bishop(true);
        gameBoard[0][3] = new King(true);
        gameBoard[0][4] = new Queen(true);
        gameBoard[0][5] = new Bishop(true);
        gameBoard[0][6] = new Knight(true);
        gameBoard[0][7] = new Rook(true);

        //initializa other Black Pieces
        gameBoard[7][0] = new Rook(false);
        gameBoard[7][1] = new Knight(false);
        gameBoard[7][2] = new Bishop(false);
        gameBoard[7][3] = new King(false);
        gameBoard[7][4] = new Queen(false);
        gameBoard[7][5] = new Bishop(false);
        gameBoard[7][6] = new Knight(false);
        gameBoard[7][7] = new Rook(false);

    }

    /**
     * used to Display empty spot pieces
     */
    private void createEmptyBoard() {
        int i, j;

        for (i = 0; i < emptyBoard.length; i += 2) {
            for (j = 0; j < emptyBoard.length; j++) {
                if (j % 2 == 0) {
                    emptyBoard[i][j] = " ##";
                } else {
                    emptyBoard[i][j] = "   ";
                }
            }
        }


        for (i = 1; i < emptyBoard.length; i += 2) {
            for (j = 0; j < emptyBoard.length; j++) {
                if (j % 2 != 0) {
                    emptyBoard[i][j] = "##";
                } else {
                    emptyBoard[i][j] = "  ";
                }
            }
        }

    }

    /**
     * used to show game state after every valid move
     */
    public void showGameBoard() {
        System.out.println();

        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = gameBoard.length - 1; j >= 0; j--) {
                if (gameBoard[i][j] == null) {
                    System.out.printf("%3s", emptyBoard[i][j]);
                } else {
                    System.out.printf("%3s", gameBoard[i][j].toString());
                }
            }
            System.out.println(" " + (i + 1));
        }
        for (int i = 0; i < gameBoard.length; i++) {
            System.out.printf("%3s", abc[i]);
        }

        System.out.println();
    }

    /**
     * Used to move piece
     * @param prevX
     * @param prevY
     * @param newX
     * @param newY
     */
    public void movePiece(int prevX, int prevY, int newX, int newY) {


            gameBoard[newY][newX] = gameBoard[prevY][prevX];
            gameBoard[prevY][prevX] = null;
            gameBoard[newY][newX].firstMove = false;
    }

    /**
     * Used to check if a space is empty or not
     * @param x
     * @param y
     * @return
     */
    public boolean isSpotEmpty(int x, int y){
        if (gameBoard[y][x] == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Special move used to execute Enpassant only
     * @param prevX
     * @param prevY
     * @param newX
     * @param newY
     */
    public void executeEnPassant(int prevX, int prevY, int newX, int newY){

        gameBoard[newY][newX] = gameBoard[prevY][prevX];
        gameBoard[prevY][prevX] = null;
        gameBoard[newY - 1][newX] = null;
        gameBoard[newY][newX].firstMove = false;

    }

    /**
     * Returns a Boolean to see if the piece is able to be captured using enpassant
     * @param prevXPos
     * @param prevYPos
     * @param newXPos
     * @param newYPos
     * @param isEmpty
     * @param enP
     * @return
     */
    public boolean checkEnp(int prevXPos, int prevYPos, int newXPos, int newYPos, boolean isEmpty, boolean enP){

        int dX = Math.abs(newXPos - prevXPos);
        int dY = Math.abs(newYPos - prevYPos);

        boolean valid = false;

        if(dY == 1 && dX == 1 && isEmpty && enP){
            valid = true;
        }

        return valid;
    }

    /**
     * Used to promote pawn to another piece
     * @param currX
     * @param currY
     * @param wOrB
     * @param initial - used in the switch statement to detemrmine which class to turn the pawn into
     */
    public void pawnPromotion(int currX, int currY, boolean wOrB, char initial ){

        switch(initial){
            case 'b':
                gameBoard[currY][currX] = new Bishop(wOrB);
                break;

            case 'k':
            case 'n':
                gameBoard[currY][currX] = new Knight(wOrB);
                break;

            case 'q':
                gameBoard[currY][currX] = new Queen(wOrB);
                break;

            case 'r':
                gameBoard[currY][currX] = new Rook(wOrB);
                break;

            default:
                break;

        }

    }

    /**
     * Checks to see if the conditions are available for castling
     * @param prevXPos
     * @param prevYPos
     * @param newXPos
     * @param newYPos
     * @param isEmpty
     * @return
     */
    public boolean checkCastle(int prevXPos, int prevYPos, int newXPos, int newYPos, boolean isEmpty){
        int dx = newXPos - prevXPos;

        if(gameBoard[prevYPos][prevXPos].toString().charAt(0) == 'b' && prevYPos == 7 && newYPos == 7 && gameBoard[prevYPos][prevXPos].firstMove){



            }
        return false;
    }

    /**
     * Performs the movement for the castling
     * @param prevXPos
     * @param prevYPos
     * @param newXPos
     * @param newYPos
     * @param isEmpty
     */
    public void executeCastle(int prevXPos, int prevYPos, int newXPos, int newYPos, boolean isEmpty){

    }
}





