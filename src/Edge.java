
public class Edge implements Comparable<Edge>{
	private Node nodeA;
	private Node nodeB;
	private int cost;
	public Edge(Node nodeA, Node nodeB, int cost) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.cost = cost;
	}
	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}
	public Node getNodeA() {
		return this.nodeA;
	}
	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}
	public Node getNodeB() {
		return this.nodeB;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getCost() {
		return this.cost;
	}
	public String toString() {
		return nodeA.getName()+" to "+nodeB.getName()+" "+cost;
	}
	public int compareTo(Edge otherEdge) {
		if (this.cost<otherEdge.getCost())
			return -1;
		if (this.cost>otherEdge.getCost())
			return 1;
		return 0;
	}
}
