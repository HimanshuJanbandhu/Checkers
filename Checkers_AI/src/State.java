import java.util.*;

public class State {
    String[][] Board;
    Player p;
    Character[] number = {'1', '2', '3', '4', '5', '6', '7', '8'};
    Character[] letter = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    HashMap<Character, Integer> hRows = new HashMap<>();
    HashMap<Character, Integer> hCols = new HashMap<>();

    public static String w = "w", wK = "W", b = "b", bK = "B";

    public State(String[][] board, Player p) {
        this.p = p;
        this.Board = board;
    }

    public void start() {
        for(int i = 0;i<8;i++){
            for(int j = 0;j<8;j++){
                if(i%2!=j%2){
                    if(i<3){
                        Board[i][j] = b;
                    }
                    else if(i>=5){
                        Board[i][j] = w;
                    }else{
                        Board[i][j] = " ";
                    }
                }else if(Board[i][j] == null){
                    Board[i][j] = " ";
                }
            }
        }
    }

    public String[][] cloneBoard(String[][] b) {
        String[][] nb = new String[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                nb[i][j] = b[i][j];
            }
        }
        return nb;
    }

    public ArrayList<Move> NextMoves(Player p, State s) {
        ArrayList<Move> moves = new ArrayList<Move>();
        String[][] newBoard = cloneBoard(s.Board);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isJumpPossible(new Move(p, i, j, i+2, j+2),newBoard)) {
                    moves.add(new Move(p, i, j, i+2, j+ 2));
                }
                if (isJumpPossible(new Move(p, i, j, i-2, j+2),newBoard)) {
                    moves.add(new Move(p, i, j, i-2, j+2));
                }
                if (isJumpPossible(new Move(p, i, j, i+2, j-2),newBoard)) {
                    moves.add(new Move(p, i, j, i+2, j-2));
                }
                if (isJumpPossible(new Move(p, i, j, i -2, j-2),newBoard)) {
                    moves.add(new Move(p, i, j, i - 2, j-2));
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isMovePossible(new Move(p, i, j, i +1, j +1), newBoard)) {
                    moves.add(new Move(p, i, j, i + 1, j + 1));
                }
                if (isMovePossible(new Move(p, i, j, i -1, j +1), newBoard)) {
                    moves.add(new Move(p, i, j, i - 1, j + 1));
                }
                if (isMovePossible(new Move(p, i, j, i +1, j -1), newBoard)) {
                    moves.add(new Move(p, i, j, i + 1, j - 1));
                }
                if (isMovePossible(new Move(p, i, j, i -1, j -1), newBoard)) {
                    moves.add(new Move(p, i, j, i - 1, j - 1));
                }
            }
        }

        return moves;
    }

    public ArrayList<State> NextStates(ArrayList<Move> moves, State s){
        ArrayList<State> states = new ArrayList<State>();
        for(Move a : moves) {
            String[][] tempBoard = cloneBoard(s.Board);
            State tempState = new State(tempBoard, p);
            states.add(applyMove(a, tempState));
        }
        return states;
    }

    public State applyMove(Move a, State s) {
        Player p = a.player;
        int fromRow = a.inRow;
        int fromCol = a.inCol;
        int toRow = a.fiRow;
        int toCol = a.fiCol;
        String[][] board = s.Board;

        if(isMovePossible(a,board)) {
            board[a.fiRow][a.fiCol] = board[a.inRow][a.inCol];
            board[a.inRow][a.inCol] = " ";
        }

        if(isJumpPossible(a,board)) {
            board[toRow][toCol] = board[fromRow][fromCol];
            board[fromRow][fromCol] = " ";
            int jumpRow = (fromRow + toRow) / 2;
            int jumpCol = (fromCol + toCol) / 2;
            board[jumpRow][jumpCol] = " ";

        }
        if (toRow == 0 && board[toRow][toCol] == w) {
            board[toRow][toCol] = wK;
        }
        if (toRow == 7 && board[toRow][toCol] == b) {
            board[toRow][toCol] = bK;
        }
        return new State(board,p);
    }

    public static boolean HasWon(Player p, State s) {
        if(p.name.equals("b")) {
            for(int i = 0;i<8;i++) {
                for(int j = 0;j<8;j++) {
                    if(s.Board[i][j].equals("w") || s.Board[i][j].equals("W")) {
                        return false;
                    }
                }
            }
        }else {
            for(int i = 0;i<8;i++) {
                for(int j = 0;j<8;j++) {
                    if(s.Board[i][j].equals("b") || s.Board[i][j].equals("B")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void updateBoard(State s) {
        for(int i = 0; i< Board.length; i++) {
            for(int j = 0; j< Board.length; j++) {
                Board[i][j] = s.Board[i][j];
            }
        }
    }

    public void fillMap() {
        for(int i = 0;i<letter.length;i++){
            hRows.put(letter[i], i);
        }
        for(int i = 0;i<number.length;i++){
            hCols.put(number[i], i);
        }
    }

    public Move getInput(Player p, String input) {
        char[] temp = input.toCharArray();
        int[] values = new int[temp.length];
        int fromRow = 0, fromCol = 0, toRow = 0, toCol = 0;
        for(int i = 0; i< temp.length; i++){
            if(hRows.containsKey(temp[i])){
                values[i] = hRows.get(temp[i]);
            }
            else if(hCols.containsKey(temp[i])){
                values[i] = hCols.get(temp[i]);
            }
        }
        fromRow = values[0];
        fromCol = values[1];
        toRow = values[3];
        toCol = values[4];

        return new Move(p,fromRow, fromCol, toRow, toCol);
    }

    public boolean isValidInput(Player p, String input) {
        if(input.length()!=5) {
            return false;
        }
        char[] temp = input.toCharArray();
        if((int) temp[0]<65 || (int) temp[0] > 72 || (int) temp[1] < 49 || (int) temp[1] > 56 || (int) temp[3]<65 || (int) temp[3] > 72 || (int) temp[4] < 49 || (int) temp[4] > 56 || (temp[2] != '-' && temp[2] != 'x')) {
            System.out.println("Invalid input, please try again: ");
            return false;
        }

        int[] values = new int[temp.length];
        int fromRow = 0, fromCol = 0, toRow = 0, toCol = 0;
        for(int i = 0; i< temp.length; i++){
            if(hRows.containsKey(temp[i])){
                values[i] = hRows.get(temp[i]);
            }
            else if(hCols.containsKey(temp[i])){
                values[i] = hCols.get(temp[i]);
            }
        }
        fromRow = values[0];
        fromCol = values[1];
        toRow = values[3];
        toCol = values[4];
        if (!isMovePossible(new Move(p,fromRow,fromCol,toRow,toCol),this.Board)&& !isJumpPossible(new Move(p, fromRow, fromCol,toRow, toCol),this.Board)) {
            System.out.println("Invalid input, please try again: ");
            return false;
        }
        return true;
    }

    public boolean AnyMovePossible(Player p, State s) {
        String[][] newBoard = cloneBoard(s.Board);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isJumpPossible(new Move(p, i, j, i +2, j +2),newBoard)) {
                    return true;
                }
                if (isJumpPossible(new Move(p, i, j, i -2, j +2),newBoard)) {
                    return true;
                }
                if (isJumpPossible(new Move(p, i, j, i +2, j -2),newBoard)) {
                    return true;
                }
                if (isJumpPossible(new Move(p, i, j, i -2, j -2),newBoard)) {
                    return true;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isMovePossible(new Move(p, i, j, i +1, j +1), newBoard)) {
                    return true;
                }
                if (isMovePossible(new Move(p, i, j, i -1, j +1), newBoard)) {
                    return true;
                }
                if (isMovePossible(new Move(p, i, j, i +1, j -1), newBoard)) {
                    return true;
                }
                if (isMovePossible(new Move(p, i, j, i -1, j -1), newBoard)) {
                    return true;
                }
            }
        }
        return false;
    }

    //chhecks if an action is possible
    private boolean isMovePossible(Move a, String[][] board) {
        Player player= a.player;
        int r1= a.inRow;
        int c1= a.inCol;
        int r2= a.fiRow;
        int c2= a.fiCol;

        if(player.name.equals("w")) {
            if(board[r1][c1].equals("w")) {
                if((r1>=0&&r1<=7)&&(c1>=0&&c1<=7)&&(r2>=0&&r2<=7)&&(c2>=0&&c2<=7)) {
                    if((board[r2][c2])==" ") {
                        if((r2==r1-1&&c2==c1+1)||((r2==r1-1&&c2==c1-1))) {
                            return true;
                        }
                    }
                }
            }else if(board[r1][c1].equals("W")) {
                if((r1>=0&&r1<=7)&&(c1>=0&&c1<=7)&&(r2>=0&&r2<=7)&&(c2>=0&&c2<=7)) {
                    if((board[r2][c2])==" ") {
                        if((r2==r1+1&&c2==c1+1)||((r2==r1+1&&c2==c1-1))||(r2==r1-1&&c2==c1+1)||((r2==r1-1&&c2==c1-1))) {
                            return true;
                        }
                    }
                }
            }
        }else if(player.name.equals("b")) {
            if(board[r1][c1].equals("b")) {
                if((r1>=0&&r1<=7)&&(c1>=0&&c1<=7)&&(r2>=0&&r2<=7)&&(c2>=0&&c2<=7)) {
                    if((board[r2][c2])==" ") {
                        if((r2==r1+1&&c2==c1+1)||((r2==r1+1&&c2==c1-1))) {
                            return true;
                        }
                    }
                }
            }else if(board[r1][c1].equals("B")) {
                if((r1>=0&&r1<=7)&&(c1>=0&&c1<=7)&&(r2>=0&&r2<=7)&&(c2>=0&&c2<=7)) {
                    if((board[r2][c2])==" ") {
                        if((r2==r1+1&&c2==c1+1)||((r2==r1+1&&c2==c1-1))||(r2==r1-1&&c2==c1+1)||((r2==r1-1&&c2==c1-1))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isJumpPossible(Move a, String[][] board) {
        Player player= a.player;
        int r1= a.inRow;
        int c1= a.inCol;
        int r2= a.fiRow;
        int c2= a.fiCol;

        if(player.name.equals("w")) {
            if(board[r1][c1].equals("w")) {
                if((r1>=0&&r1<=7)&&(c1>=0&&c1<=7)&&(r2>=0&&r2<=7)&&(c2>=0&&c2<=7)&& r2<r1) {
                    if((board[r2][c2])==" ") {
                        if(board[(r1+r2)/2][(c1+c2)/2].equals("b")||board[(r1+r2)/2][(c1+c2)/2].equals("B")) {
                            return true;
                        }
                    }
                }
            }
            else if(board[r1][c1].equals("W")) {
                if((r1>=0&&r1<=7)&&(c1>=0&&c1<=7)&&(r2>=0&&r2<=7)&&(c2>=0&&c2<=7)) {
                    if((board[r2][c2])==" ") {
                        if(board[(r1+r2)/2][(c1+c2)/2].equals("b")||board[(r1+r2)/2][(c1+c2)/2].equals("B")) {
                            return true;
                        }
                    }
                }
            }
        }
        if(player.name.equals("b")) {
            if((r1>=0&&r1<=7)&&(c1>=0&&c1<=7)&&(r2>=0&&r2<=7)&&(c2>=0&&c2<=7)&&r2>r1) {
                if(board[r1][c1].equals("b")) {
                    if((board[r2][c2])==" ") {
                        if(board[(r1+r2)/2][(c1+c2)/2].equals("w")||board[(r1+r2)/2][(c1+c2)/2].equals("W")) {
                            return true;
                        }
                    }
                }
            }
            else if(board[r1][c1].equals("B")) {
                if((r1>=0&&r1<=7)&&(c1>=0&&c1<=7)&&(r2>=0&&r2<=7)&&(c2>=0&&c2<=7)) {
                    if((board[r2][c2])==" ") {
                        if(board[(r1+r2)/2][(c1+c2)/2].equals("w")||board[(r1+r2)/2][(c1+c2)/2].equals("W")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String toString(){
        String s = " ";
        for(int i = 0;i<8;i++){
            s += " "+number[i];
        }
        s+="\n";
        for(int i = 0;i<8;i++) {
            s += "---";
        }
        s+="\n";
        for(int i = 0;i<8;i++){
            s+=letter[i];
            for(int j = 0;j<8;j++){
                if(Board[i][j] == " ") {
                    s+="| ";
                }
                else {
                    s+="|"+Board[i][j];
                }
            }
            s+="|";
            s+="\n";
            for(int k = 0;k<8;k++) {
                s+= "---";
            }
            s+="\n";
        }
        return s;
    }

    public boolean Win(Player p) {
        String p1 = "", p2 = "";
        if(p.name.equals("b")) {
            p1 = "b";
            p2 = "w";
            for(int i = 0; i< Board.length; i++) {
                for(int j = 0; j< Board.length; j++) {
                    if(Board[i][j].equals("w")|| Board[i][j].equals("W")) {
                        return false;
                    }
                }
            }
        }else if(p.name.equals("w")) {
            p1 = "White";
            p2 = "Black";
            for(int i = 0; i< Board.length; i++) {
                for(int j = 0; j< Board.length; j++) {
                    if(Board[i][j].equals("b")|| Board[i][j].equals("B")) {
                        return false;
                    }
                }
            }
        }
        System.out.println(p1 +" Wins since there are no "+ p2 +" pieces on the Board.");
        return true;
    }
}
