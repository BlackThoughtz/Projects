import java.util.Scanner;

import static java.lang.Character.getNumericValue;



/**
 * Main class that controls the game
 * @author David Butler netID drb197
 *
 */
public class Chess {
    public static void main(String[] args) {

        /*Create game board and Scanner for input*/
        theBoard Board = new theBoard();
        Scanner readIn = new Scanner(System.in);

        /*Last move Data Structure to check for enPassant w/ accompanying boolean*/
        LastMove history;
        boolean canEnpassant = false;

        /*Boolean for castling*/
        boolean canCastle = false;

        /*Booleans for game control*/
        boolean gameOver = false;
        boolean whiteTurn = true;
        boolean validPlay = true;
        boolean wKingCheck = false;
        boolean bKingCheck = false;

        /*String array for input handling*/
        String[] holder;
        String input;
        String promo = "bknqr";

        /*numbers for handling board movement
        * pX = Previous X Location -> cX = Current X Location
        * pY = Previous Y Location -> cY = Current Y Location
        * bKX && bKY track location movement for the black King.... will be used for check and checkmate handling
        * wKX && wKY trackLocation for the white King*/
        int pX, pY, cX, cY;

        int bKX = 3;
        int bKY = 7;
        int wKX = 3;
        int wKY = 0;

        /*Game Control Loop*/
        while(!gameOver) {

            /*valid and whiteTrun start off true to control, which will print the gameBoard when a previous move was valid
            * if a valid move was not specified we wont change the turn or print the board, but print illegal move...*/
            if (validPlay == true) {
                Board.showGameBoard();
                printTurn(whiteTurn);
            } else{
                System.out.println("Illegal move, try again.");
            }

            input = readIn.nextLine();

            /*Handling resignation if that was inputted immediately ending the game and awarding the win to the other player*/
            if (input.trim().toLowerCase().equals("resign")) {
                gameOver = true;
                resignation(whiteTurn);
                break;
            }

            /*Populate string array with input*/
            holder = input.trim().split(" ", 3);

            /*Draw requires a move with a third input and will be handled here*/
            if (holder.length == 3) {
                if (holder[2].trim().toLowerCase().equals("draw?")) {
                    input = readIn.next();
                    if (input.trim().toLowerCase().equals("draw")) {
                        gameOver = true;
                        break;
                    }
                }
            }

            /*error handling for invalid input size*/
            if(holder.length < 2){
                validPlay = false;
                continue;
            }

            if(holder[0].length() != 2){
                validPlay = false;
                continue;
            }
            if(holder[1].length() != 2){
                validPlay = false;
                continue;
            }

            /*populate coordiantes */
            pX = coordinateToVal(holder[0].charAt(0));
            pY = getNumericValue(holder[0].charAt(1)) - 1;

            cX = coordinateToVal(holder[1].charAt(0));
            cY = getNumericValue(holder[1].charAt(1)) - 1;

            /*error handling if the inputs are correct size but wrong values*/
            if(pX == 99 || cX == 99){
                validPlay = false;
                continue;
            }
            if(pY < 0 || pY > 7 || cY < 0 || cY > 7){
                validPlay = false;
                continue;
            }

            /*Enpassant check*/
            if (Board.gameBoard[pY][pX] instanceof Pawn) {

                validPlay = Board.checkEnp(pX, pY, cX, cY, Board.isSpotEmpty(cX,cY), canEnpassant);
                if(validPlay == true){
                    Board.executeEnPassant(pX,pY,cX,cY);
                    whiteTurn = !whiteTurn;
                    continue;

                }

            }
            /*Castling Check*/
            if (Board.gameBoard[pX][pY] instanceof King) {
                //castling check

            }
            /*Error check for Valid Inputs but no piece in starting spot*/
            if(Board.gameBoard[pY][pX] == null){
                validPlay = false;
                continue;
            }

            /*Error check for player moving wrong piece*/
            if(whiteTurn == true){
                if(Board.gameBoard[pY][pX].toString().charAt(0) == 'b'){
                    validPlay = false;
                    continue;
                }
            } else{
                if(Board.gameBoard[pY][pX].toString().charAt(0) == 'w'){
                    validPlay = false;
                    continue;
                }
            }

            /*Boolean used to see if piece moved was valid to its corresponding piece*/
            validPlay = Board.gameBoard[pY][pX].validMove(pX, pY, cX, cY, Board);


            /*if valid move was iputtied we check to see if piece is a king to update locations of king*/
            if (validPlay == true) {

                if(Board.gameBoard[pY][pX] instanceof King){
                    if(whiteTurn){
                        wKX = cX;
                        wKY = cY;
                    }else{
                        bKX = cX;
                        bKY = cY;
                    }

                }

                /*find out if this move will allow the piece to be captures for Enpassant*/
                history = new LastMove(pX, pY, cX, cY, Board.gameBoard[pY][pX]);
                canEnpassant = history.canEnPassant();

                /*Move piece and check for pawn promotion with error handling for improper input*/
                Board.movePiece(pX, pY, cX, cY);
                if(Board.gameBoard[cY][cX] instanceof Pawn){
                    if(whiteTurn){
                        if(cY == 7){
                            System.out.println("Enter Initial of Piece to promote Pawn to: ");
                            input = readIn.nextLine().trim();
                            while (promo.indexOf(input.toLowerCase().charAt(0)) == -1 ){
                                System.out.println("Invalid input, try again.");
                                input = readIn.nextLine().trim();
                            }
                            Board.pawnPromotion(cX, cY, whiteTurn, input.toLowerCase().charAt(0));

                        }
                    }else {
                        if(cY == 0){
                            System.out.println("Enter Initial of Piece to promote Pawn to: ");
                            input = readIn.nextLine().trim();
                            while (promo.indexOf(input.toLowerCase().charAt(0)) == -1 ){
                                System.out.println("Invalid input, try again.");
                                input = readIn.nextLine().trim();
                            }
                            Board.pawnPromotion(cX, cY, whiteTurn, input.toLowerCase().charAt(0));
                        }
                    }
                }

                /*if a piece moves and can immediately capture king on next move.... */
                if(whiteTurn){
                    bKingCheck = Board.gameBoard[cY][cX].canCaptureKing(cX,cY,bKX,bKY,Board);
                }
                else{
                    wKingCheck = Board.gameBoard[cY][cX].canCaptureKing(cX,cY,wKX,wKY,Board);

                }

                if(bKingCheck){
                    System.out.printf("Black King in Check\n");
                }
                if(wKingCheck){
                    System.out.println("White King in Check\n");
                }
                whiteTurn = !whiteTurn;
            }
        }
    }

    /**
     *If user entered resign as an input we check who resigned and display the winner
     * @param wOrB = whiteTurn if white turn is true black wins and vice verse
     */
    public static void resignation(boolean wOrB){
        if(wOrB) {
            System.out.println("Black wins");
        }else{
            System.out.println(("White Wins"));
        }
    }

    /**
     * Used to print who's turn it is
     * @param wOrB  = white turn and print whos turn it is
     */
    public static void printTurn(boolean wOrB){
        if(wOrB){
            System.out.print("\nWhite's move: ");
        }else{
            System.out.print("\nBlack's move: ");
        }
    }

    /**
     * Method to turn board coordinate to proper Matrix Coordinate
     * @param letter = board coordinate
     * @return int for coordinate in matrix
     */
    public static int coordinateToVal(char letter){

        int rank = 0;

        switch(letter) {
            case 'a':
                rank = 7;
                break;
            case 'b':
                rank = 6;
                break;
            case 'c':
                rank = 5;
                break;
            case 'd':
                rank = 4;
                break;
            case 'e':
                rank = 3;
                break;
            case 'f':
                rank = 2;
                break;
            case 'g':
                rank = 1;
                break;
            case 'h':
                rank = 0;
                break;
            default:
                rank = 99;
                break;
        }
        return rank;
    }



}
