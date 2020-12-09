# Checkers

Automated Player for Checkers

This document provides a log of making and analysing the results of a basic Automated Player for Checkers. We will see what each class provides, what are the states, what heuristics can be used, and how the Min-Max algorithm was applied with Alpha-Beta pruning.

The classes used in the following   - 
Player - This class basically has the name of the player. We have only 2 players, b(black) and w(white). It Overrides equals and hashcode function for proper hashing.
State - State contains the board. The Player who is going to make the move. Helpers for printing the board. Map hCols and hRows, which map different moves to the board. It has many functions, start() initializes the board, cloneBoard() clones the board, NextMoves() helps the Min-Max algorithm to check all the next moves, NextStates() function helps to prune all the children of the moves possible. applyMove() applies any move on the board, HasWon() checks if any player has won, updateBoard() updates the board for the AI, fillMap() fills the board, getInput gets the input from the user, isValidInput() checks if the input is valid, AnyMovesPossible() checks if any further moves or possible for the AI, isMovePossible() checks if the current move is possible on the board, isJumpPossible() checks if any jump is possible on the board, toString prints the current board which can GUI for testing, lastly Win(), this function is used whenever a player wins and we print it on the command line, unlike HasWon() which helps the AI to find a given player wins.
TestClass - It is the class which contains the main function, which basically runs the game, it takes input from the user, selects the player, it prints the board, makes the AI-move, and finally tells when someone has won or game is over. 
Move - Move contains a player which makes the move, it has inRow and inCol, which are initial row and column of the player and fiRow and fiCol, which represents the final row and column of the player.
MinMax_AB - This is the class which implements MinMax algorithm with alpha beta pruning, it also applies the heuristic in it, about which we will discuss later in this report.  

Working 
We’ll discuss how we created the Checkers game and what were the results that we saw.
A.Environment

We have a 8*8 board, on which on the lower side we have white pieces and on the upper side we have black pieces, placed on diagonally adjacent squares on both the sides. The pieces can move diagonally, it can jump over an opposing piece if it’s diagonal square on the corresponding side is empty. If one piece reaches the end of it’s opposite side, it becomes a king piece, which can take backward moves also. Everytime the game starts black moves first.

B. Taking Input

In this program we are using a command line GUI. Hence, we have to take input from command line, so we created helpers in the State class, so all the columns are represented by numbers, (1 to 8) and all the rows are represented by letters, (A to H), so the user can make moves like G6-F5, F7-E6 and many more.

C. Dataflow and Working of the game.

First, the user chooses which player to choose, black or white. Then if black, the user plays first, else the computer plays first. Next, the user moves are applied on the board. Then the AI runs MinMax function, which brings out the best possible move. In between this, if more moves are not possible, or any player wins, the program prints about it, and ends there.

D. MinMax Algorithm

The algorithm is designed in a way that, whatever the player is the algorithm can be easily applied.
As we know, only black can make the first move, hence black will always try to maximise its output and white will always try to minimize it’s output. By output we refer to its states heuristic value.
The getMax and getMin terminates, if the state is a winning state or no more states are possible or if it came to maximum depth, which is set to 6, you can increase or decrease or remove it, which would lead to different time requirements.
Alpha-Beta Pruning is applied in the normal way as provided in any pseudocode of MinMax.

E. Heuristics

The Heuristics used in this program are quite simple. More number of pieces on the board, more are it’s chances to win. Since Black is trying to maximize, it’s each piece get’s +10, and since White is trying to minimize its each piece get’s -10.
We also see that King pieces can also make backward moves, hence they have double importance than normal pieces hence, each BlackKing get’s +20, and each WhiteKing get’s -20. 
It can be improved if we take note of all the pieces on the edges which cannot be jumped across, so they can also be a factor.

F. GUI

For ease of testing a command line GUI is made, with easy commands, so that the program can be tested simply. As told earlier, for moves, each row is marked with letters (A-H) and each column is marked with numbers (1-8). Hence, moves like “G6-F5” or “F7-E6”, without quotes can be made.

 


