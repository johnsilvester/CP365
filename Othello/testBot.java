
import java.util.ArrayList;

import java.util.Scanner;


public class testBot extends OthelloPlayer {
   
    double[][] array = new double[][]{
  { 100, -10, 9.1, 7.1, 7.1, 9.1, -10, 100 },
  { -10, -10, 4, 3, 3, 4, -10, -10 },
  { 9.1, 4, 5, 3, 3, 5, 4, 9.1 },
  { 7.1, 3, 4, 0, 0, 2, 3, 7.1 },
  { 7.1, 3, 2, 0, 0, 8, 3, 7.1 },
  { 9.1, 4, 5, 3, 3, 5, 4, 9.1 },
  { -10, -10, 4, 3, 3, 4, -10, -10 },
  { 100, -10, 9.1, 7.1, 7.1, 9.1, -10, 100 },



  
};


    public testBot(Integer _color) {
        super(_color);


    }


    public double getMoveScore(OthelloBoard b, OthelloMove m) {
         



       double score = array[m.row][m.col]; //grab score from array
       
       //bump up by 3
       array[m.row][m.col] = score + 3;
       

     
       return score; // return it

        
        // if (!b.isLegalMove(m)) {
        //     return -1;
        // }
        // else {
        //     int score = 0;
        //     score += scoreUp(b, m);
        //     score += scoreDown(b, m);
        //     score += scoreLeft(b, m);
        //     score += scoreRight(b, m);
        //     score += scoreUpLeft(b, m);
        //     score += scoreUpRight(b, m);
        //     score += scoreDownLeft(b, m);
        //     score += scoreDownRight(b, m);
            
        //     return score;
        // }
    }
    
    public OthelloMove makeMove(OthelloBoard b) {
         if (b.gameOver()){
            
       System.out.println("Press \"ENTER\" to continue...");
   Scanner scanner = new Scanner(System.in);
   scanner.nextLine();

        }
        
        
        
        ArrayList<OthelloMove> moves = b.legalMoves(playerColor);
        
        OthelloMove highestMove = moves.get(0);
        double highestScore = getMoveScore(b, highestMove);
        
        for (OthelloMove m : moves) {
            double score  = getMoveScore(b, m);
            System.out.println(m + " -> " + score);
            if (score > highestScore) {
                highestScore = score;
                highestMove = m;
            }
        }
        System.out.println("Made move: " + highestMove);
        System.out.println("SCORE: " + highestScore);

        return highestMove;

    }

    public int scoreUp(OthelloBoard b, OthelloMove m) {
        int currRow = m.row - 1;
        while (currRow >= 0 && b.board[currRow][m.col] != m.player && b.board[currRow][m.col] != 0) {
            currRow--;
        }
        if (currRow >= 0 && b.board[currRow][m.col] == m.player) {
            return m.row - currRow - 1;
        }
        return 0;
    }
    
    
    public int scoreDown(OthelloBoard b, OthelloMove m) {
        int currRow = m.row + 1;
        while (currRow < b.size && b.board[currRow][m.col] != m.player && b.board[currRow][m.col] != 0) {
            currRow++;
        }
        if (currRow < b.size && b.board[currRow][m.col] == m.player) {
            return currRow - m.row - 1;
        }
        return 0;
    }
    
    public int scoreRight(OthelloBoard b, OthelloMove m) {
        int currCol = m.col + 1;
        while (currCol < b.size && b.board[m.row][currCol] != m.player && b.board[m.row][currCol] != 0) {
            currCol++;
        }
        if (currCol < b.size && b.board[m.row][currCol] == m.player) {
            return currCol - m.col - 1;
        }
        return 0;
    }
    
    
    public int scoreLeft(OthelloBoard b, OthelloMove m) {
        int currCol = m.col - 1;
        while (currCol >= 0 && b.board[m.row][currCol] != m.player && b.board[m.row][currCol] != 0) {
            currCol--;
        }
        if (currCol >= 0 && b.board[m.row][currCol] == m.player) {
            return m.col-currCol - 1;
        }
        return 0;
    }
    
    public int scoreUpRight(OthelloBoard b, OthelloMove m) {
        int currRow = m.row - 1;
        int currCol = m.col + 1;
        while (currCol < b.size && currRow >= 0 && b.board[currRow][currCol] != m.player && b.board[currRow][currCol] != 0) {
            currCol++;
            currRow--;
        }
        if (currCol < b.size && currRow >= 0 && b.board[currRow][currCol] == m.player) {
            return currCol - m.col - 1;
        }
        return 0;
    }
    
    
    public int scoreUpLeft(OthelloBoard b, OthelloMove m) {
        int currRow = m.row - 1;
        int currCol = m.col - 1;
        while (currCol >= 0 && currRow >= 0 && b.board[currRow][currCol] != m.player && b.board[currRow][currCol] != 0) {
            currCol--;
            currRow--;
        }
        if (currCol >= 0 && currRow >= 0 && b.board[currRow][currCol] == m.player) {
            return m.row - currRow - 1;
        }
        return 0;
    }
    
    
    public int scoreDownRight(OthelloBoard b, OthelloMove m) {
        int currRow = m.row + 1;
        int currCol = m.col + 1;
        while (currCol < b.size && currRow < b.size && b.board[currRow][currCol] != m.player && b.board[currRow][currCol] != 0) {
            currCol++;
            currRow++;
        }
        if (currCol < b.size && currRow < b.size && b.board[currRow][currCol] == m.player) {
            return currRow - m.row - 1;
        }
        return 0;
    }
    
    
    public int scoreDownLeft(OthelloBoard b, OthelloMove m) {
        int currRow = m.row + 1;
        int currCol = m.col - 1;
        while (currCol >= 0 && currRow < b.size && b.board[currRow][currCol] != m.player && b.board[currRow][currCol] != 0) {
            currCol--;
            currRow++;
        }
        if (currCol >= 0 && currRow < b.size && b.board[currRow][currCol] == m.player) {
            return currRow - m.row - 1;
        }
        return 0;
    }
}