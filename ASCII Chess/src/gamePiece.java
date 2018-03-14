/**
 * Parent class for all pieces
 * @author David Butler netID drb197
 *  An abstract class that will be used to determine the structure of all pieces in the game
 */
public abstract class gamePiece {
    /*variables every piece will need*/
    protected boolean white;
    protected String title;
    protected int dX,dY;
    protected boolean firstMove = true;
    protected boolean valid = false;

    /*constructor */
    public gamePiece(boolean wOrB){

        this.white = wOrB;

        if(this.white == true){
            this.title = "w";
        } else{
            this.title = "b";
        }
    }

    /**
     * Returns true if particular piece can move
     * @param prevXPos
     * @param prevYPos
     * @param newXPos
     * @param newYPos
     * @param currBoard
     * @return
     */
    public abstract boolean validMove(int prevXPos, int prevYPos, int newXPos,int newYPos,theBoard currBoard);


    /**
     * Method to check if piece can capture king on the next move
     * @param currX
     * @param currY
     * @param kingX
     * @param kingY
     * @param currBoard
     * @return
     */
    public abstract boolean canCaptureKing(int currX, int currY, int kingX, int kingY, theBoard currBoard);


    /**
     * override to string method
     * @return
     */
    @Override
    public String toString(){
        return title;
    }


}
