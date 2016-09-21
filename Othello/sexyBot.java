import java.util.ArrayList;

public class sexyBot extends OthelloPlayer {
    class Node {
        OthelloMove move = null;
        OthelloBoard board = null;
        ArrayList<Node> children = new ArrayList();
        int score;
        Node parent = null;
        
        public Node(OthelloBoard _board) {
            board = _board;
            score = board.getBoardScore();
            if(move != null)score += array[move.row][move.col];
        }
        public Node(OthelloBoard _board, OthelloMove _move) {
            board = _board;
            move = _move;
            score = board.getBoardScore();
            if(move != null)score += array[move.row][move.col];
        }
        public void addChild(Node child) {
            child.parent = this;
        }
    }


	final int depthLimit = 5;
    public sexyBot(Integer _color) {
        super(_color);
    }
    
    public OthelloMove makeMove(OthelloBoard board) {
        Node initialNode = new Node(board);
        int value = alphabeta(initialNode, depthLimit, Integer.MIN_VALUE, Integer.MAX_VALUE, playerColor);
        ArrayList<OthelloMove> availabMoves = new ArrayList<OthelloMove>();
        for (Node child : initialNode.children) {
            if(child.score == value)availabMoves.add(child.move);
        }
        return availabMoves.get(0);
    }
    
    public int alphabeta(Node node, int depth, int alpha, int beta, int myPlayer) {
        ArrayList<OthelloMove> moves = node.board.legalMoves(myPlayer);
        if (depth == 0)return node.score;
        
        Node child = null;

        ArrayList<Node> children = new ArrayList<Node>();

        if (myPlayer == playerColor) {
        	// max player
            node.score = Integer.MIN_VALUE;
            for (OthelloMove m : moves) {
                OthelloBoard newboard = new OthelloBoard(node.board.size, false);
                setBoard(newboard, node.board);
                newboard.addPiece(m);
                child = new Node(newboard, m);
                node.addChild(child);
                children.add(child);
                if(node.parent == null) {
                    child.move = m;
                    node.children.add(child);
                }
                int childScore = alphabeta(child, depth-1, alpha, beta, getOpponentColor());
                node.score = Math.max(node.score, childScore);
                alpha = Math.max(node.score, alpha);
                if (beta < alpha)break;
            }
            return node.score;
        }   
        else {
            node.score = Integer.MAX_VALUE;
            //mix player
            for (OthelloMove m : moves) {
                OthelloBoard newboard = new OthelloBoard(node.board.size, false);
                setBoard(newboard, node.board);
                newboard.addPiece(m);
                child = new Node(newboard, m);
                node.addChild(child);
                children.add(child);

                int childScore = alphabeta(child, depth-1, alpha, beta, playerColor);
                node.score = Math.min(node.score, childScore);
                beta = Math.min(node.score, beta);
                if (beta < alpha)break;
            }
            return node.score;
        }
    }
    
int[][] array = new int[][]{
	  { 100, -20, 9, 7, 7, 9, -20, 100 },
	  { -20, -20, 4, 3, 3, 4, -20, -20 },
	  { 9, 4, 5, 3, 3, 5, 4, 9 },
	  { 7, 3, 4, 0, 0, 2, 3, 7 },
	  { 7, 5, 2, 0, 0, 8, 3, 7 },
	  { 9, 4, 5, 3, 3, 5, 4, 9 },
	  { -20, -20, 4, 3, 3, 4, -20, -20 },
	  { 100, -20, 9, 7, 7, 9, -20, 100 },
	};

int[][] array2 = new int[][]{
	  { 100, 100, 100, 100, 100, 100, 100, 100 },
	  { 100, -20, 4, 3, 3, 4, -20, 100 },
	  { 100, 4, 5, 3, 3, 5, 4, 100 },
	  { 100, 3, 4, 0, 0, 2, 3, 100 },
	  { 100, 5, 2, 0, 0, 8, 3, 100 },
	  { 100, 4, 5, 3, 3, 5, 4, 100 },
	  { 100, -20, 4, 3, 3, 4, -20, 100 },
	  { 100, 100, 100, 100, 100, 100, 100, 100 },
	};

    public void setBoard(OthelloBoard board, OthelloBoard otherBoard) {
        for (int r = 0; r < board.size; r++) {
            for (int c = 0; c < board.size; c++) {
                board.board[r][c] = otherBoard.board[r][c];
            }
        }
    }
}

