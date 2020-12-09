import java.util.*;

// For making moves you can see that each column is represented by numbers(1-8) and each row is represented by
// letters(A-H). Hence you can make moves like “G6-F5” or “F7-E6”, without quotes.

public class TestClass {

    public static void main(String[]args) {
        String player1 = "", player2 = "", move = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Which piece do you prefer - Black(b) or White(w)?");
        player1 = sc.nextLine();
        while(!player1.equals("b") && !player1.equals("w")) {
            System.out.println("Would you like to play b(b) or w(w)?");
            player1 = sc.nextLine();
        }
        System.out.println("You're "+ player1);
        Player pHuman = new Player(player1);
        if(player1.equals("w")) {
            player2 = "b";
        }else {
            player2 = "w";
        }
        System.out.println("For making moves you can see that each column is represented by numbers(1-8) and each row is represented by\n" +
                "letters(A-H). Hence you can make moves like “G6-F5” or “F7-E6”, without quotes.");
        Player pAI = new Player(player2);

        String[][] board = new String[8][8];
        State s = new State(board, pHuman);
        s.start();
        s.fillMap();
        System.out.println(s.toString());
        State next;

        System.out.println("Black Starts First");
        MinMax_AB Node = new MinMax_AB();
        while(true) {
            if(player1.equals("b")) {
                while(true) {
                    System.out.println("Black's Turn! Make a Move: ");
                    move = sc.nextLine();
                    while(s.isValidInput(pHuman, move) == false) {
                        move = sc.nextLine();
                    }
                    Move human = s.getInput(pHuman, move);
                    next = s.applyMove(human,s);
                    System.out.println(s.toString());
                    if(s.Win(pHuman)) {
                        System.exit(0);
                    }
                    if(!s.AnyMovePossible(pAI,s)) {
                        System.out.println("No more moves possible! Game over!");
                        System.exit(0);
                    }
                    next = Node.MinMax(pAI, next);
                    s.updateBoard(next);
                    System.out.println(s.toString());
                    if(s.Win(pAI)) {
                        System.exit(0);
                    }
                    if(!s.AnyMovePossible(pHuman,s)) {
                        System.out.println("No more moves possible! Game over!");
                        System.exit(0);
                    }
                }
            }
            else {
                next = new State(board, pHuman);
                while(true){
                    next = Node.MinMax(pAI, next);
                    s.updateBoard(next);
                    System.out.println(s.toString());
                    if(s.Win(pAI)) {
                        System.exit(0);
                    }
                    if(!s.AnyMovePossible(pHuman,s)) {//no more moves for w
                        System.out.println("No more moves possible! Game over!");
                        System.exit(0);

                    }
                    System.out.println("White's Turn! Make a Move: ");
                    move = sc.nextLine();
                    while(s.isValidInput(pHuman, move) == false) {
                        move = sc.nextLine();

                    }
                    Move human = s.getInput(pHuman, move);
                    next = s.applyMove(human,s);
                    System.out.println(s.toString());
                    if(s.Win(pHuman)) {
                        System.exit(0);
                    }
                    if(s.AnyMovePossible(pAI,s)==false) {
                        System.out.println("No more moves possible! Game over!");
                        System.exit(0);

                    }
                }
            }
        }
    }
}