import java.io.PrintWriter;

public class Test {

	public static void main(String[] args) {
		IncidenceMatrix IM = new IncidenceMatrix();
		//AdjList IM = new AdjList();
		PrintWriter os = new PrintWriter(System.out);

		IM.addVertex("A");
		IM.addVertex("B");
		IM.addVertex("C");
		IM.addVertex("D");
		IM.addVertex("E");
		IM.addVertex("F");
		IM.addVertex("G");
		IM.addVertex("H");

		IM.addEdge("A", "B", 1);
		IM.addEdge("B", "C", 2);
		IM.addEdge("D", "B", 3);
		IM.addEdge("C", "E", 5);
		IM.addEdge("E", "F", 6);
		IM.addEdge("C", "G", 7);
		IM.addEdge("G", "E", 7);
		IM.addEdge("G", "H", 4);
		
		IM.updateWeightEdge("G", "H", 5);
		IM.updateWeightEdge("A", "B", 10);
		IM.updateWeightEdge("D", "B", 9);
		
		IM.outNearestNeighbours(-1, "B");
		IM.outNearestNeighbours(-1, "F");
		IM.outNearestNeighbours(-1, "C");
		IM.outNearestNeighbours(1, "C");
		
		IM.getEdgeWeight("A", "B");
		IM.getEdgeWeight("D", "A");
		
		IM.removeVertex("J");
		IM.removeVertex("A");
		
		IM.updateWeightEdge("B", "C", 0);
		IM.updateWeightEdge("E", "F", 0);
		IM.updateWeightEdge("F", "E", 0);
		
		IM.addVertex("J");
		
		IM.printVertices(os);
		IM.printEdges(os);
		
		 
		os.flush();
		os.close();
	}

}