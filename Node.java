import java.util.ArrayList;

public class Node {
	private Node parent;
	private ArrayList<Node> children;
	private int nodeDepth;
	private int[] nodeMove;
	private Board nodeBoard;
	private double nodeEvaluation;
	
	public Node(ArrayList<Node> children, int nodeDepth, Board nodeBoard, double nodeEvaluation) {
        this.children = children;
        this.nodeDepth = nodeDepth;
        this.nodeBoard = nodeBoard;
        this.nodeEvaluation = nodeEvaluation;
    }
	
	public Node(Node parent, int nodeDepth, int[] nodeMove, Board nodeBoard, double nodeEvaluation) {
        this.parent = parent;
        this.nodeDepth = nodeDepth;
        this.nodeMove = nodeMove;
        this.nodeBoard = nodeBoard;
        this.nodeEvaluation = nodeEvaluation;
    }
	
	public Node(Node parent, ArrayList<Node> children, int nodeDepth, int[] nodeMove, Board nodeBoard, double nodeEvaluation) {
        this.parent = parent;
        this.children = children;
        this.nodeDepth = nodeDepth;
        this.nodeMove = nodeMove;
        this.nodeBoard = nodeBoard;
        this.nodeEvaluation = nodeEvaluation;
    }
	
	public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public int getNodeDepth() {
        return nodeDepth;
    }

    public void setNodeDepth(int nodeDepth) {
        this.nodeDepth = nodeDepth;
    }

    public int[] getNodeMove() {
        return nodeMove;
    }

    public void setNodeMove(int[] nodeMove) {
        this.nodeMove = nodeMove;
    }

    public Board getNodeBoard() {
        return nodeBoard;
    }

    public void setNodeBoard(Board nodeBoard) {
        this.nodeBoard = nodeBoard;
    }

    public double getNodeEvaluation() {
        return nodeEvaluation;
    }

    public void setNodeEvaluation(double nodeEvaluation) {
        this.nodeEvaluation = nodeEvaluation;
    }
    
    
}
