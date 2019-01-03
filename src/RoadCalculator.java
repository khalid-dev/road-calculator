import java.util.*;

import big.data.DataSource;
import big.data.DataSourceException;
public class RoadCalculator {
	private static HashMap<String, Node> graph;
	private static LinkedList<Edge> mst;
	private static String[] cityNames;
	private static boolean programContinues = true;
	private static boolean validData = false;
	private static Scanner input= new Scanner(System.in);
	private static Node dummy1 = new Node("dummy1");
	private static Node dummy2 = new Node("dummy2");
	private static Edge dummy = new Edge(dummy1, dummy2, 100000000);
	public static void main(String[] args) {
		while (programContinues) {
			while (!validData) {
				System.out.println("Please enter graph URL");
				String graphURL = input.next();
				System.out.println("Loading map....");
				try {
					graph = buildGraph(graphURL);
					validData = true;
					System.out.println("\nMinimum Spanning Tree:\n");
					for (Edge edge: buildMST(graph)) {
						System.out.println(edge);
					}
					for (int i = 0; i<cityNames.length; i++) {
						graph.get(cityNames[i]).setVisited(false);
					}
				}
				catch (DataSourceException ex) {
					System.out.println("Please enter a valid file source");
				}
			}
			//add in something to handle erroneous city input
			System.out.println("\nEnter a starting point for shortest path or Q to quit: ");
			input.nextLine();
			String source = input.nextLine().replace("\n", "");
			if (source.toUpperCase().equals("Q")) {
				System.out.println("Exiting Program.");
				return;
			}
			System.out.println("Enter a destination: ");
			String dest = input.nextLine().replace("\n", "");
			System.out.println("\nDistance: "+Djikstra(graph, source, dest));
		}
	}
	public static HashMap<String, Node> buildGraph(String location) {
		//build a graph with XML link
		HashMap<String, Node> citiesGraph = new HashMap<String, Node>();
		DataSource ds = DataSource.connect(location);
		ds.load();
		String cityNamesStr = ds.fetchString("cities");
		cityNames=cityNamesStr.substring(1,cityNamesStr.length()-1).replace("\"","").split(",");
        String roadNamesStr=ds.fetchString("roads");
        String[] roadNames=roadNamesStr.substring(1,roadNamesStr.length()-1).split("\",\"");
        ArrayList<HashSet<Edge>> edges= new ArrayList<HashSet<Edge>>();
        System.out.println("Cities: \n");
        for (String cityStr: cityNames) {
        	System.out.println(cityStr);
        	Node cityNode = new Node(cityStr);
        	citiesGraph.put(cityStr, cityNode);
        }
        System.out.println("\nRoads:\n");
        for (String roadStr: roadNames) {
        	String[] roadInfo = roadStr.replace("\"","").split(",");
        	Node nodeA = citiesGraph.get(roadInfo[0]);
        	Node nodeB = citiesGraph.get(roadInfo[1]);
        	System.out.println(roadInfo[0]+" to "+roadInfo[1]+" "+roadInfo[2]);
        	int cost = Integer.parseInt(roadInfo[2]);
        	Edge edge = new Edge(nodeA, nodeB, cost);
        	nodeA.addEdge(edge);
        	edge = new Edge(nodeB, nodeA, cost);
        	nodeB.addEdge(edge);
        }
		return citiesGraph;
	}
	public static LinkedList<Edge> buildMST(HashMap<String, Node> graph) {
		//create minimum spanning tree of graph
		LinkedList<Edge> MST = new LinkedList<Edge>();
		Node startNode = graph.get(cityNames[0]);
		startNode.setVisited(true);
		Edge lowestEdge = Collections.min(startNode.getEdges());
		MST.add(lowestEdge);
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(startNode);
		for (int i = 1; i<cityNames.length-1; i++) {
			lowestEdge.getNodeB().setVisited(true);
			nodes.add(lowestEdge.getNodeB());
			lowestEdge = dummy;
			for (Node node: nodes) {
				for (Edge edge: node.getEdges()) {
					if (edge.getNodeB().isVisited()==false)
						if (edge.getCost()<lowestEdge.getCost())
							lowestEdge = edge;
				}
			}
			MST.add(lowestEdge);
		}
		Collections.sort(MST);
		return MST;
	}
	public static int Djikstra(HashMap<String, Node> graph, String source, String dest) {
		Node sourceNode = graph.get(source);
		Node destNode = graph.get(dest);
		sourceNode.setDistance(0);
		Node currentNode = sourceNode;
		ArrayList<Node> unvisited = new ArrayList<Node>();
		for(String str: cityNames) {
			unvisited.add(graph.get(str));
		}
		while (currentNode!=destNode) {
			for (Edge edge: currentNode.getEdges()) {
				int tentativeDistance = edge.getCost()+edge.getNodeA().getDistance();
				if (edge.getNodeB().getDistance()>tentativeDistance) {
					edge.getNodeB().setDistance(tentativeDistance);
					edge.getNodeB().setClosestNeighbor(currentNode);
				}
			}
			currentNode.setVisited(true);
			unvisited.remove(currentNode);
			currentNode = Collections.min(unvisited);
		}
		System.out.println("Path:");
		ArrayList<String> outputPath = new ArrayList<String>();
		Node nodePTR = currentNode;
		while (nodePTR!=sourceNode) {
			outputPath.add(nodePTR.getName());
			nodePTR = nodePTR.getClosestNeighbor();
		}
		Collections.reverse(outputPath);
		System.out.print(sourceNode.getName());
		for (String str: outputPath) {
			System.out.print(", "+str);
		}
		return destNode.getDistance();
	}
}
