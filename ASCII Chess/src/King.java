/**
 * King Chess Piece Class
 * @author David Butler netID
 */
public class King extends gamePiece {


    public King(boolean wOrB) {
        super(wOrB);
        title += "K";
    }

    /**
     * Valid move according to king's movement parameters
     * @param prevXPos
     * @param prevYPos
     * @param newXPos
     * @param newYPos
     * @param currBoard
     * @return
     */
    @Override
    public boolean validMove(int prevXPos, int prevYPos, int newXPos, int newYPos, theBoard currBoard) {
        dX = Math.abs(newXPos - prevXPos);
        dY = Math.abs(newYPos - prevYPos);

        this.valid = false;

        if( dX == 1 && dY == 1 ||dX == 1 && dY == 0 || dX == 0 && dY == 1 ) {
            valid = true;
        }

        if( !currBoard.isSpotEmpty(newXPos, newYPos)){
            if(currBoard.gameBoard[prevYPos][prevXPos].toString().charAt(0) == currBoard.gameBoard[newYPos][newXPos].toString().charAt(0)){
                valid = false;
            }
        }

        return valid;

    }

    /**
     * Checks to see if a king can capture another king
     * @param currX
     * @param currY
     * @param kingX
     * @param kingY
     * @param currBoard
     * @return
     */
    public boolean canCaptureKing(int currX, int currY, int kingX, int kingY, theBoard currBoard) {
        return validMove(currX, currY,kingX,kingY,currBoard);
    }
    @Override
    public String toString() {
        return this.title;
    }
}
