import java.util.*;
//represents a city
public class Node implements Comparable<Node>{
	private String name;
	private HashSet<Edge> edges = new HashSet<Edge>();
	private boolean visited = false;
	private LinkedList<String> path;
	private int distance = Integer.MAX_VALUE;
	private Node closestNeighbor;
	public Node(String name) {
		this.name = name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}
	public HashSet<Edge> getEdges() {
		return this.edges;
	}
	public void setVisited(boolean bool) {
		this.visited = bool;
	}
	public boolean isVisited() {
		return this.visited;
	}
	public void addToPath(String name) {
		path.add(name);
	}
	public LinkedList<String> getPath() {
		return this.path;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getDistance() {
		return this.distance;
	}
	public void setClosestNeighbor(Node closestNeighbor) {
		this.closestNeighbor = closestNeighbor;
	}
	public Node getClosestNeighbor() {
		return this.closestNeighbor;
	}
	public String toString() {
		return this.name;
	}
	public int compareTo(Node otherNode) {
		if (this.distance<otherNode.getDistance())
			return -1;
		if (this.distance>otherNode.getDistance())
			return 1;
		return 0;
	}
}
