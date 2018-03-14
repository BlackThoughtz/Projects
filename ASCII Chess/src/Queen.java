/**
 * Created by butlr on 10/19/2017.
 */
public class Queen extends gamePiece {


    public Queen(boolean wOrB) {
        super(wOrB);

        title += "Q";

    }

    /**
     * Checks to see if input is valid accoridng to Queen's movement paramters
     * @param prevXPos
     * @param prevYPos
     * @param newXPos
     * @param newYPos
     * @param currBoard
     * @return
     */
    @Override
    public boolean validMove(int prevXPos, int prevYPos, int newXPos, int newYPos, theBoard currBoard) {
        /*Can be updated to create a rook or bishop instance of validMove depending on the type of move that is inputted */
        this.valid = false;
        boolean clear = true;

        dX = newXPos - prevXPos;
        dY = newYPos - prevYPos;

        if ((dX == dY )|| (dX == 0 && dY != 0) || (dX != 0 && dY == 0)) {
            this.valid = true;
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
        //if Queen moves Horizontal
        if (valid) {
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

            }
            if(Math.abs(dX) > 0 && dY == 0){
                if(dX > 0) {
                    for (int i = prevXPos + 1; i < newXPos; i++) {
                        if (currBoard.isSpotEmpty(i, prevYPos)){
                            continue;
                        }else{
                            return false;
                        }
                    }
                }

                if(dX < 0){

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





        if( !currBoard.isSpotEmpty(newXPos, newYPos)){
            if(currBoard.gameBoard[prevYPos][prevXPos].toString().charAt(0) == currBoard.gameBoard[newYPos][newXPos].toString().charAt(0)){
                valid = false;
            }
        }

        return this.valid && clear;
    }

    /**
     * Checks to see if Queen can put King in check on next move
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
