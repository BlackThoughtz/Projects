/**
 * Created by butlr on 10/19/2017.
 */

public class Rook extends gamePiece {
    public Rook(boolean wOrB) {
        super(wOrB);

        title += "R";
    }

    /**
     * Checks to see if movement is valid according to Rook movemenet Parameters
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
        boolean clear = true;


        dX = newXPos - prevXPos;
        dY = newYPos - prevYPos;
        //valid input
        if( dX == 0 && dY != 0 || dX != 0 && dY == 0){
            valid = true;
        }

        //if valid check if path clear
        if(valid) {
            if (Math.abs(dY) > 0 && dX == 0) {
                //move right
                if (dY > 0) {
                    for(int i = prevYPos + 1; i < newYPos; i++) {
                        if (currBoard.isSpotEmpty(newXPos, i)) {
                            continue;
                        } else {
                            return false;
                        }
                    }
                } else {
                    //move left
                    for (int i = prevYPos - 1; i > newYPos; i--) {
                        if (currBoard.isSpotEmpty(newXPos, i)) {
                            continue;
                        } else {
                            return false;
                        }
                    }
                }

            } else{
                if(dX > 0) {
                    for (int i = prevXPos + 1; i < newXPos; i++) {
                        if (currBoard.isSpotEmpty(i, prevYPos)){
                            continue;
                        }else{
                            return false;
                        }
                    }
                } else{

                    for(int i = prevXPos - 1; i > newXPos; i--){
                        if(currBoard.isSpotEmpty(i, prevYPos)){
                            continue;
                        }else{
                            return false;
                        }
                    }
                }
            }
        }

        return valid && clear;
    }

    /**
     * Checks to see if Rook can put King in Check on next Move
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
