/**
 * Bishop Chess Piece
 * @author  David Butler netID DRB
 */
public class Bishop extends gamePiece{



    public Bishop(boolean wOrB) {
        super(wOrB);
        title += "B";
    }

    /**
     * Checks to see if input is a valid Bishop Move
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

        /*System.out.printf("In bishop valid move dx = %d, dy = %d\n", dX, dY);
        System.out.printf("prevX = %d, prevY = %d, newX = %d, newY = %d \n", prevXPos,prevYPos,newXPos,newYPos);*/

        if(Math.abs(dX) ==  Math.abs(dY)){
            valid = true;
        }

        if(valid){

            //to up and left
            if( dX > 0 && dY > 0){
                for(int i = prevXPos + 1, j = prevYPos + 1; i < newXPos || j < newYPos; i++,j++){
                    if(currBoard.isSpotEmpty(i,j)){
                        clear = true;
                    }else{
                        return false;
                    }
                }
            } else if(dX < 0 && dY > 0){
                //bottom and right
                for(int i = prevXPos - 1, j = prevYPos + 1; i > newXPos || j < newYPos; i--,j++){
                    if(currBoard.isSpotEmpty(i,j)){
                        clear = true;
                    }else{
                        return false;
                    }
                }

            } else if(dX > 0 && dY < 0){
                //top and left
                for(int i = prevXPos + 1, j = prevYPos - 1; i < newXPos || j > newYPos; i++,j--){
                    if(currBoard.isSpotEmpty(i,j)){
                        clear = true;
                    }else{
                        clear = false;
                        break;
                    }
                }

            }else if(dX < 0 && dY < 0){
                //bottom and left
                for(int i = prevXPos - 1, j = prevYPos - 1; i > newXPos || j > newYPos; i--,j--){
                    if(currBoard.isSpotEmpty(i,j)){
                        clear = true;
                    }else{
                        clear = false;
                        break;
                    }
                }
            }
        }

        if( !currBoard.isSpotEmpty(newXPos, newYPos)){
            if(currBoard.gameBoard[prevYPos][prevXPos].toString().charAt(0) == currBoard.gameBoard[newYPos][newXPos].toString().charAt(0)){
                valid = false;
            }
        }

        return valid && clear;
    }

    /**
     * Checks to see if next move will capture enemy King
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
