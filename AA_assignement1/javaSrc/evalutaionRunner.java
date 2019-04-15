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
		startTime = System.nanoTime();
		createAdjGraph();
		endTime=System.nanoTime();
		System.out.println(" Init Time: " + ((double) (endTime - startTime)) / Math.pow(10, 9) + " sec");
	}
	
	

	private static void createAdjGraph() {
		
		//AdjList tempGraph = new AdjList();
		try 
		{
			String path = new File("").getAbsolutePath();
			File output = new File(path + "/javaSrc/100-0.1.txt");
			BufferedReader reader = new BufferedReader(new FileReader(output));
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
                System.out.println(tokens[0]);
                System.out.println(tokens[1]);
                System.out.println(tokens[2]);
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
	
	
	/*private static void createIncGraph() {
		
		//AdjList tempGraph = new AdjList();
		try 
		{
			
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			String delimiter = " ";
			String[] tokens;
			String srcLabel, tarLabel;
			int weight;
			
			while((line = reader.readLine())!=null)
			{
				tokens = line.split(delimiter);
                srcLabel = tokens[0];
                tarLabel = tokens[1];
                weight =Integer.parseInt(tokens[3]); 
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
	}*/
	


	private static void scent3_changeAssoc() {
		
		
	}

	private static void scen2_nestestNeigh() {
		
		
	}

	private static void scen1_shrinkGraph() {
		
		
	}
	
}
