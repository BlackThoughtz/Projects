/**
 * Created by butlr on 10/19/2017.
 */
public class Pawn extends gamePiece {



    public Pawn(boolean wOrb){
        super(wOrb);
        title += "P";
    }

    /**
     * Check to see if a move is valid accoridng Pawn's movement parameters
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

        if(dY == 1 && dX == 0 && currBoard.isSpotEmpty(newXPos, newYPos)){
            valid = true;
        } else if(dY == 1 && dX == 1 && !currBoard.isSpotEmpty(newXPos, newYPos)){
            valid = true;
        } else if(dY == 2 && dX == 0 && currBoard.isSpotEmpty(newXPos, newYPos) && firstMove){
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
     * checks to see if Pawn can put king in check on next move
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
    public String toString(){
        return this.title;
    }


}
