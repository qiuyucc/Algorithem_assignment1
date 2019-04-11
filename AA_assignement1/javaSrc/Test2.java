import java.io.PrintWriter;

public class Test2 {

	public static void main(String[] args) {
		// IncidenceMatrix IM = new IncidenceMatrix();
		AdjList IM = new AdjList();
		PrintWriter os = new PrintWriter(System.out);
		
		IM.addVertex("A");
		IM.addVertex("B");
		IM.addVertex("C");
		IM.addVertex("D");
		IM.addVertex("E");
		IM.addVertex("F");
		
		IM.addEdge("A", "B", 1);
		IM.addEdge("C", "B", 1);
		IM.addEdge("B", "D", 1);
		IM.addEdge("A", "E", 3);
		IM.addEdge("D", "C", 5);
		IM.addEdge("F", "A", 2);
		
		IM.outNearestNeighbours(-1, "A");
		IM.inNearestNeighbours(-1, "F");
		
		IM.getEdgeWeight("C", "B");
		IM.getEdgeWeight("B", "C");
		IM.getEdgeWeight("A", "E");
		
		IM.updateWeightEdge("C", "B", 4);
		IM.updateWeightEdge("A", "B", 0);
		
		IM.removeVertex("D");
		
		IM.addVertex("G");
		
		IM.printVertices(os);
		IM.printEdges(os);
		
	}
}
