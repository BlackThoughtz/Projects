/**
 * Used to keep Last move
 * @author  David Butler net ID
 */
public class LastMove {

    /* variables to check for enpassant*/
    boolean enPassant = false;
    int dX, dY;
    gamePiece testPiece;


    public LastMove(int prevX, int prevY, int newX, int newY, gamePiece piece) {
        this.dX = Math.abs(newX - prevX);
        this.dY = Math.abs(newY - prevY);
        this.testPiece = piece;
    }

    /*method to see if conditions for enpassant are true*/
    public boolean canEnPassant() {
        enPassant = false;

        if(testPiece instanceof Pawn && testPiece.firstMove && dX == 0 && dY == 2){
            enPassant = true;
        }
        return enPassant;
    }
}


