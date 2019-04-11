import java.io.PrintWriter;

public class Test2 {
	
	public static void main(String [] args) 
	{
		/*IncidenceMatrix IM = new IncidenceMatrix();
		PrintWriter pw = new PrintWriter(System.out);


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
	   
	    IM.updateWeightEdge("C", "B",3);
	    IM.getEdgeWeight("B", "C");
	    
	    
		
		pw.flush();*/
		
		IncidenceMatrix IM = new IncidenceMatrix();
		PrintWriter pw = new PrintWriter(System.out);

		IM.addVertex("A");
		IM.addVertex("B");
		IM.addVertex("C");
		IM.addVertex("D");
		//IM.addVertex("E");

		IM.addEdge("B", "A", 2);
		

		IM.addEdge("D", "A", 2);
		IM.addEdge("B", "D", 1);
		IM.addEdge("C", "B", 2);
		IM.addEdge("D", "C", 3);
		//IM.outNearestNeighbours(-1, "C");
		
		//IM.inNearestNeighbours(-1, "A");
		IM.removeVertex("B");

		IM.printEdges(pw);
		pw.flush();
		pw.close();
		
		
	}

	
}
