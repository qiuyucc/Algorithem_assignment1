import java.io.PrintWriter;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AdjList AJ=new AdjList();
		PrintWriter pw = new PrintWriter(System.out);
		
		AJ.addVertex("A");
		AJ.addVertex("B");
		AJ.addVertex("C");
		AJ.addVertex("D");
		AJ.addVertex("E");	
		AJ.addVertex("F");
		
		AJ.addEdge("A", "B", 1);
		AJ.addEdge("C", "B", 1);
		AJ.addEdge("B", "D", 1);
		AJ.addEdge("A", "E", 3);
		AJ.addEdge("D", "C", 5);
		AJ.addEdge("F", "A", 2);
		
		AJ.outNearestNeighbours(2, "A");
		
		AJ.inNearestNeighbours(1, "B");
		
		AJ.getEdgeWeight("C", "B");
		AJ.getEdgeWeight("B", "C");
		AJ.getEdgeWeight("A", "D");
		
			
		//System.out.println(AJ.getEdgeWeight("A", "D"));
		
		//AJ.printVertices(pw);
		//AJ.printEdges(pw);
		
		pw.flush();
		pw.close();
		

	}
}
