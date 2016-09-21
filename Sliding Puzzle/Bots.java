import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.PriorityQueue;




class Bots extends SlidingPlayer {

   
    public SlidingMove makeMove(SlidingBoard board) {
        moveNum ++;
        return path.get(moveNum);
    } 

    ArrayList<SlidingMove> path;
    int moveNum = -1;

    
    public Bots(SlidingBoard _sb) {
        super(_sb);
        path = idas(_sb,5);
    }

    public int getCost(int pathLength, SlidingBoard b){
        int cost = pathLength;
        for(int r = 0; r<b.board.length; r++){
            for(int c = 0; c<b.board.length; c++){
                if(b.board[r][c] != 0){

                    cost += Math.abs((b.board[r][c]%b.size)-c);
                    
                    cost += Math.abs((b.board[r][c]/b.size)-r);
                }
            }
        }
        return cost;
    }

   
    public ArrayList<SlidingMove> dfs(SlidingBoard board){

        HashSet<String> nodesViewed = new HashSet<String>();

        LinkedList<Node> prevBoards = new LinkedList<Node>();

        Node currNode = new Node(board, new ArrayList<SlidingMove>());


        while(!currNode.board.isSolved()){
            
            ArrayList<SlidingMove> legalMoves = currNode.board.getLegalMoves();

            for(SlidingMove move:legalMoves){

                SlidingBoard newBoard = new SlidingBoard(board.size);

                newBoard.setBoard(currNode.board);

                newBoard.doMove(move);

                if(!nodesViewed.contains(newBoard.toString())){

                    nodesViewed.add(newBoard.toString());

                    ArrayList<SlidingMove> pathWay = (ArrayList<SlidingMove>)currNode.path.clone();

                    pathWay.add(move);

                    Node childNode = new Node(newBoard, pathWay);

                    prevBoards.push(childNode);
                }
            }
            currNode = prevBoards.pop();
        }
        return currNode.path;
    }

    public ArrayList<SlidingMove> bfs(SlidingBoard board){
    
        HashSet<String> nodesViewed = new HashSet<String>();

        LinkedList<Node> prevBoards = new LinkedList<Node>();

        Node currNode = new Node(board, new ArrayList<SlidingMove>());

        while(!currNode.board.isSolved()){
            
            ArrayList<SlidingMove> legalMoves = currNode.board.getLegalMoves();

            for(SlidingMove move:legalMoves){

                SlidingBoard newBoard = new SlidingBoard(board.size);

                newBoard.setBoard(currNode.board);

                newBoard.doMove(move);

                if(!nodesViewed.contains(newBoard.toString())){

                    nodesViewed.add(newBoard.toString());

                    ArrayList<SlidingMove> pathWay = (ArrayList<SlidingMove>)currNode.path.clone();

                    pathWay.add(move);

                    Node childNode = new Node(newBoard, pathWay);
                    
                    prevBoards.push(childNode);
                }
            }
            currNode = prevBoards.removeFirst();
        }


        return currNode.path;
    }

    public ArrayList<SlidingMove> idas(SlidingBoard board, int costLimit){
        
  HashSet<String> nodesViewed = new HashSet<String>();

        PriorityQueue<AStarNode> pq = new PriorityQueue<AStarNode>();

        AStarNode currNode = new AStarNode(board, new ArrayList<SlidingMove>());

        pq.add(currNode);

        while(!currNode.board.isSolved()){

            currNode = pq.poll();

            ArrayList<SlidingMove> legalMoves = currNode.board.getLegalMoves();

            for(SlidingMove move:legalMoves){

                SlidingBoard newBoard = new SlidingBoard(board.size);

                newBoard.setBoard(currNode.board);

                newBoard.doMove(move);

                int pathLength = currNode.path.size() + 1;

                if(!nodesViewed.contains(newBoard.toString())){

                    nodesViewed.add(newBoard.toString());

                    ArrayList<SlidingMove> pathWay = (ArrayList<SlidingMove>)currNode.path.clone();

                    pathWay.add(move);
                    
                    AStarNode childNode = new AStarNode(newBoard, pathWay);

                    pq.add(childNode);
                }
            }
            currNode = pq.poll();
        }       

        if(currNode.board.isSolved()) 
            {return currNode.path;}
        else 
            {return idas(board, costLimit+10);}
    }

    
   
    class AStarNode implements Comparable{

    public SlidingBoard board;
    public ArrayList<SlidingMove> path;
    public int cost;

    public AStarNode(SlidingBoard incomingBoard, ArrayList<SlidingMove> pathway){
        board = incomingBoard;

        path = pathway;

        cost += pathway.size();

        for(int r = 0; r<incomingBoard.board.length; r++){

            for(int c = 0; c<incomingBoard.board.length; c++){

                if(incomingBoard.board[r][c] != 0){

                    cost += Math.abs((incomingBoard.board[r][c]%incomingBoard.size)-c);

                    cost += Math.abs((incomingBoard.board[r][c]/incomingBoard.size)-r);
                }
            }
        }
    }

    public int compareTo(Object otherNode) {
        AStarNode other = (AStarNode) otherNode;
        return new Double(cost).compareTo(new Double(other.cost));
    }
}

    public ArrayList<SlidingMove> aStarBot(SlidingBoard board){
        
        HashSet<String> nodesViewed = new HashSet<String>();

        PriorityQueue<AStarNode> pq = new PriorityQueue<AStarNode>();

        AStarNode currNode = new AStarNode(board, new ArrayList<SlidingMove>());

        while(!currNode.board.isSolved()){

            ArrayList<SlidingMove> legalMoves = currNode.board.getLegalMoves();

            for(SlidingMove move:legalMoves){

                SlidingBoard newBoard = new SlidingBoard(board.size);

                newBoard.setBoard(currNode.board);

                newBoard.doMove(move);

                if(!nodesViewed.contains(newBoard.toString())){

                    nodesViewed.add(newBoard.toString());

                    ArrayList<SlidingMove> pathWay = (ArrayList<SlidingMove>)currNode.path.clone();

                    pathWay.add(move);

                    AStarNode childNode = new AStarNode(newBoard, pathWay);

                    pq.add(childNode);
                }
            }
            currNode = pq.poll();
        }
        return currNode.path;
    }

  public ArrayList<SlidingMove> iterativeDepthBot(SlidingBoard board, int depth){

        HashSet<String> nodesViewed = new HashSet<String>();

        LinkedList<Node> prevBoards = new LinkedList<Node>();

        Node currNode = new Node(board, new ArrayList<SlidingMove>());

         prevBoards.push(currNode);


        while(!currNode.board.isSolved() && prevBoards.size()>0){
            currNode = prevBoards.pop();
            ArrayList<SlidingMove> legalMoves = currNode.board.getLegalMoves();

            for(SlidingMove move:legalMoves){

                SlidingBoard newBoard = new SlidingBoard(board.size);

                newBoard.setBoard(currNode.board);

                newBoard.doMove(move);

                if(!nodesViewed.contains(childBoard.toString()) && currNode.path.size()<depth){

                    nodesViewed.add(newBoard.toString());

                    ArrayList<SlidingMove> pathWay = (ArrayList<SlidingMove>)currNode.path.clone();

                    pathWay.add(move);

                    Node childNode = new Node(newBoard, pathWay);

                    prevBoards.push(childNode);
                }
            }
          
        }
        if(currNode.board.isSolved()) {
            return currNode.path;}

        else {
            return ids(board, depth+5);}}


   
class Node{
    public SlidingBoard board;
    public ArrayList<SlidingMove> path;

    public Node(SlidingBoard b, ArrayList<SlidingMove> p){
        board = b;
        path = p;
    }
}

      
}