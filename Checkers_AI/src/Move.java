public class Move {
    Player player;
    int inRow, inCol, fiRow, fiCol;

    public Move(Player player, int inRow, int inCol, int fiRow, int fiCol) {
        this.player = player;
        this.inCol = inCol;
        this.inRow = inRow;
        this.fiRow = fiRow;
        this.fiCol = fiCol;
    }
}