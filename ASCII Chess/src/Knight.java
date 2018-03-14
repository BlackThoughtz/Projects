/**
 * Knight Chess Piece Class
 * @author David Butler net ID drb197
 *
 */
public class Knight extends gamePiece{



    public Knight(boolean wOrB) {
        super(wOrB);

        title += "N";
    }

    /**
     * Checks if a move is valid according to Knight's movement parameters
     * @param prevXPos
     * @param prevYPos
     * @param newXPos
     * @param newYPos
     * @param currBoard
     * @return
     */
    @Override
    public boolean validMove(int prevXPos, int prevYPos, int newXPos, int newYPos, theBoard currBoard) {

        this.valid = false;

        dX = Math.abs(newXPos - prevXPos);
        dY = Math.abs(newYPos - prevYPos);

        if(dX == 2 && dY == 1 || dX == 1 && dY == 2){
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
     * Checks if Knight can capture king on the next move
     * @param currX
     * @param currY
     * @param kingX
     * @param kingY
     * @param currBoard
     * @return
     */
    @Override
    public boolean canCaptureKing(int currX, int currY, int kingX, int kingY, theBoard currBoard) {
        return validMove(currX, currY,kingX,kingY,currBoard);
    }

    @Override
    public String toString() {
        return this.title;
    }
}
