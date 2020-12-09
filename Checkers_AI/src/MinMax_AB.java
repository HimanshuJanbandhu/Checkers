import java.util.*;

public class MinMax_AB {

    public static int heuristic(String [][] board) {
        int cb =0,cw =0,cbk =0,cwk = 0;
        for(int i = 0;i<board.length;i++) {
            for(int j = 0;j<board.length;j++) {
                if(board[i][j].equals("b")) {
                    cb++;
                }
                if(board[i][j].equals("B")) {
                    cbk++;
                }
                if(board[i][j].equals("w")) {
                    cw++;
                }
                if(board[i][j].equals("W")){
                    cwk++;
                }

            }
        }
        int h = ((cb)*(10) + (cw)*(-10) + cbk * 20 + cwk * (-20));
        return h;
    }

    public static State MinMax(Player p, State s) {
        if (p.name.equals("w")) { //min
            return getMin(p, s, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else { //max
            return getMax(p, s, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }

    public static Player changePlayer(Player p) {
        if (p.name.equals("w")) {
            return new Player("b");
        } else {
            return new Player("w");
        }
    }

    public static State getMax(Player p, State s, int d, int alpha, int beta) {
        ArrayList<Move> moves = s.NextMoves(p, s);
        ArrayList<State> children = s.NextStates(moves, s);
        if ((s.HasWon(p,s)) || (d > 6) || (children.size()==0)) {
            return s;
        }
        int maxUtil = Integer.MIN_VALUE;
        State maxNode = null;
        for (State c : children) {
            int util = heuristic(getMin(changePlayer(p), c, d +1, alpha, beta).Board);
            if (util >= maxUtil) {
                maxUtil = util;
                maxNode = c;
            }
            alpha = Math.max(alpha, maxUtil);
            if(beta <= alpha) {
                break;
            }
        }
        return maxNode;
    }

    public static State getMin(Player p, State s, int d, int alpha, int beta) {
        ArrayList<Move> moves = s.NextMoves(p, s);
        ArrayList<State> children = s.NextStates(moves, s);
        if (s.HasWon(p,s) || (d>6) || (children.size()==0)) {
            return s;
        }
        int minUtil = Integer.MAX_VALUE;
        State minNode = null;
        for (State c : children) {
            int util = heuristic(getMax(changePlayer(p), c, d +1, alpha, beta).Board);
            if (util <= minUtil) {
                minUtil = util;
                minNode = c;
            }
            beta = Math.min(beta, minUtil);
            if(beta <= alpha) {
                break;
            }
        }
        return minNode;
    }

}