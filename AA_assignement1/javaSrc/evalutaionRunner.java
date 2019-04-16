import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class evalutaionRunner {
	
	//how many repeats vertexes test will be conduted
	//private static final int EXECUTION_REPEATS=5;
	
	//Sample data sizes
	//private static int[] vertexNum = {200,1500};
	
	//private static int[] grahpDensity= {2,55};
	private static long startTime;
	private static long endTime;
	private static double density;
	private static AdjList adjGraph;
	private static IncidenceMatrix incGraph;
	
	/*static{
		// Start meausurement for vertexes addition to a graph
		fileName = "100-0.1.txt";
		startTime = System.nanoTime();
		createAdjGraph();
		endTime=System.nanoTime();
		System.out.println(" Init Time: " + ((double) (endTime - startTime)) / Math.pow(10, 9) + " sec");
		//createIncGraph();
		
	}*/
	
	public static void main(String [] args) throws ClassNotFoundException,IllegalAccessException,InstantiationException, IOException
	{
		adjGraph =new AdjList();
		incGraph= new IncidenceMatrix();
		startTime = System.nanoTime();
		//createAdjGraph();
		createIncGraph();
		scen1_shrinkGraph();
		//scen2_nestestNeigh();
		//scen3_changeAssoc();
		endTime=System.nanoTime();
		System.out.println(" Init Time: " + ((double) (endTime - startTime)) / Math.pow(10, 9) + " sec");
	}
	
	

	private static void createAdjGraph() {
		
		//AdjList tempGraph = new AdjList();
		try 
		{
			String path = new File("").getAbsolutePath();
			File output1 = new File(path + "/javaSrc/100-0.1.txt");
			File output2 = new File(path + "/javaSrc/100-0.5.txt");
			File output3 = new File(path + "/javaSrc/100-0.8.txt");
			File output4 = new File(path + "/javaSrc/default.txt");
			File output5 = new File(path + "/javaSrc/200-0.5.txt");
			BufferedReader reader = new BufferedReader(new FileReader(output4));
			String line;
			String delimiter = "\\s+";
			String[] tokens;
			String srcLabel, tarLabel;
			int weight;
			
			while((line = reader.readLine())!=null)
			{
				tokens = line.split(delimiter);
                srcLabel = tokens[0];
                tarLabel = tokens[1];
                weight =Integer.parseInt(tokens[2]); 
                adjGraph.addVertex(srcLabel);
                adjGraph.addVertex(tarLabel);
                adjGraph.addEdge(srcLabel, tarLabel,weight);
			}
		}catch(FileNotFoundException ef) 
		{
			System.err.println("Error: File not present.");
		}catch(IOException ei) 
		{
			System.out.println("Error:Something went wrong");
		}
	}
	
	
	private static void createIncGraph() {
		try 
		{
			String path = new File("").getAbsolutePath();
			File output1 = new File(path + "/javaSrc/100-0.1.txt");
			File output2 = new File(path + "/javaSrc/100-0.5.txt");
			File output3 = new File(path + "/javaSrc/100-0.8.txt");
			File output4 = new File(path + "/javaSrc/default.txt");
			File output5 = new File(path + "/javaSrc/200-0.5.txt");
			BufferedReader reader = new BufferedReader(new FileReader(output1));
			String line;
			String delimiter = "\\s+";
			String[] tokens;
			String srcLabel, tarLabel;
			int weight;
			
			while((line = reader.readLine())!=null)
			{
				tokens = line.split(delimiter);
                srcLabel = tokens[0];
                tarLabel = tokens[1];
                //System.out.println(tokens[2]);
                weight =Integer.parseInt(tokens[2]); 
                incGraph.addVertex(srcLabel);
                incGraph.addVertex(tarLabel);
                incGraph.addEdge(srcLabel, tarLabel,weight);
			}
		}catch(FileNotFoundException ef) 
		{
			System.err.println("Error: File not present.");
		}catch(IOException ei) 
		{
			System.out.println("Error:Something went wrong");
		}
	}
	


	private static void scen3_changeAssoc() {
		//adjGraph.updateWeightEdge("74", "81", 19);
		incGraph.updateWeightEdge("74", "81", 19);
	}

	private static void scen2_nestestNeigh() {
		//adjGraph.inNearestNeighbours(-1, "1");
		incGraph.inNearestNeighbours(-1, "74");
 
		
	}

	private static void scen1_shrinkGraph() {
		//adjGraph.removeVertex("1");
		for(int x =1; x<100; x++) 
		{
			String xs=Integer.toString(x);
			incGraph.removeVertex(xs);
		}
		
	}
	
}
