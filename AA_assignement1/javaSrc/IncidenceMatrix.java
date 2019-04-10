import java.io.*;
import java.util.*;

/**
 * Incident matrix implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class. You may add
 * methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class IncidenceMatrix extends AbstractAssocGraph {

	/**
	 * Contructs empty graph.
	 */
	private int Xs;
	private int Ys;
	private String[][] incMatrix;

	public IncidenceMatrix() {
		this(0, 0);
	} // end of IncidentMatrix()

	public IncidenceMatrix(int Ys, int Xs) {
		this.Ys = Ys;
		this.Xs = Xs;
		incMatrix = new String[Ys + 1][Xs + 1];
	}

	public void addVertex(String vertLabel) {
		// check the vertex existed or not
		if (getIndex(vertLabel) != -1) {
			System.out.println("AddVertex: " + vertLabel);
			System.out.println("Failed, vertex already exists.");
			return;
		}
		if (Ys == 0) {
			incMatrix[Ys][0] = vertLabel;
			System.out.println("This vertex added: " + incMatrix[Ys][0]);
			Ys++;
		} else {
			// if array of vertex is full, we have to expand it and Edges
			if (incMatrix.length == Ys) {
				incMatrix = resize(incMatrix, Ys);
			}
			incMatrix[Ys][0] = vertLabel;
			System.out.println("This vertex added: " + incMatrix[Ys][0]);
			Ys++;
			// System.out.println(Ys);
		}
	} // end of addVertex()

	public void addEdge(String srcLabel, String tarLabel, int weight) {
		int index;
		int posWeight = weight;
		int negWeight = -weight;
		if (srcLabel != tarLabel) {

			int src = getIndex(srcLabel);
			if (src == -1) {
				System.out.println("AddEdge: " + srcLabel);
				System.out.println("Failed, vertex already exists.");
				return;
			}
			int tar = getIndex(tarLabel);

			if (tar == -1) {
				System.out.println("AddEdge: " + tarLabel);
				System.out.println("Failed, vertex already exists.");
				return;
			}
			if (tar > src) {
				index = findColumnIndex(incMatrix, srcLabel, tarLabel);
				incMatrix[src][index] = Integer.toString(posWeight);
				incMatrix[tar][index] = Integer.toString(negWeight);

			}
			if (tar < src) {
				index = findColumnIndex(incMatrix, tarLabel, srcLabel);
				incMatrix[tar][index] = Integer.toString(negWeight);
				incMatrix[src][index] = Integer.toString(posWeight);
			}

		} else {
			System.out.println("No self connected edge in this case!");
		}

	} // end of addEdge()

	public int getEdgeWeight(String srcLabel, String tarLabel) {
		int index, weight = 0;
		int src = getIndex(srcLabel);
		if (srcLabel != tarLabel) {
			if (src == -1) {
				System.out.println("getEdgeWeight: " + srcLabel);
				System.out.println("Failed, vertex not exists.");
				return EDGE_NOT_EXIST;
			}
			int tar = getIndex(tarLabel);
			if (tar == -1) {
				System.out.println("getEdgeWeight: " + tarLabel);
				System.out.println("Failed, vertex not exists.");
				return EDGE_NOT_EXIST;
			}
			if (src < tar) {
				index = findColumnIndex(incMatrix, srcLabel, tarLabel);
				weight = Integer.parseInt(incMatrix[src][index]);
			}
			if (src > tar) {
				index = findColumnIndex(incMatrix, tarLabel, srcLabel);
				weight = Integer.parseInt(incMatrix[src][index]);
			}
		} else {
			System.out.println("No self connected edge in this case!");
		}
		System.out.println(weight);
		return weight;

	} // end of existEdge()

	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
		int index = 0;
		int src = getIndex(srcLabel);
		int posWeight = weight;
		int negWeight = -weight;
		if (srcLabel != tarLabel) {
			if (src == -1) {
				System.out.println("update: " + srcLabel);
				System.out.println("Failed, vertex doesnt exists.");
			}
			int tar = getIndex(tarLabel);
			if (tar == -1) {
				System.out.println("update: " + tarLabel);
				System.out.println("Failed, vertex doesnt exists.");

			}
			if (src < tar) {
				index = findColumnIndex(incMatrix, srcLabel, tarLabel);
				incMatrix[src][index] = Integer.toString(posWeight);
				incMatrix[tar][index] = Integer.toString(negWeight);
				System.out.println("updated success");
			}
			if (src > tar) {
				index = findColumnIndex(incMatrix, tarLabel, srcLabel);
				incMatrix[src][index] = Integer.toString(posWeight);
				incMatrix[tar][index] = Integer.toString(negWeight);
				System.out.println("updated success");
			}
		} else {
			System.out.println("No self connected edge in this case!");
		}

	} // end of updateWeightEdge()

	public void removeVertex(String vertLabel) {
		int columIndex =0;
		int i = getIndex(vertLabel);
		if (i == -1) {
			System.out.print("remove vertex:" + vertLabel);
			System.out.println("does not exist");
		}

		/*
		 * //Testing for(int x=0;x<incMatrix.length;x++) { for(int
		 * y=0;y<incMatrix[x].length;y++) { System.out.print(incMatrix[x][y]+" "); }
		 * System.out.println("\n"); }
		 * System.out.println("--------------------------------------------"); //Testing
		 */
		int index = findstartIndexForVertex(incMatrix, vertLabel);
		// delete the weight in the 2D array which relate with VertLabel is it's source
		for (int x = i; x < incMatrix.length; x++) {
			for (int y = 0; y < incMatrix.length - i - 1; y++) {
				incMatrix[x][index + y] = null;
			}
		}

		/*
		 * //Testing for(int x=0;x<incMatrix.length;x++) { for(int
		 * y=0;y<incMatrix[x].length;y++) { System.out.print(incMatrix[x][y]+" "); }
		 * System.out.println("\n"); }
		 * System.out.println("--------------------------------------------"); //Testing
		 * 
		 */ // delte the weight in the 2D arary which relate with VertLabel is it's endpoint
		/*
		 * only need to check the vertex before
		 */
		for (int j = 0; j < i; j++) {
			int indexEnd = findColumnIndex(incMatrix, incMatrix[j][0], vertLabel);
			incMatrix[j][indexEnd] = null;
			incMatrix[i][indexEnd] = null;
		}
		/*for (int x = 0; x < index; x++) {
		// calculate the which column should be deleted
		columIndex = findColumnIndex(array, array[x][0], array[index][0]);
		System.out.print(columIndex);
		for (int z = 0; z < array[x].length; z++) {
			//newIncMatrix[z][columIndex] = array[z][columIndex + 1];
			for(int j=0;j<) {
				newIncMatrix[z][columIndex+j] = array[z][columIndex + 1+j];
			}
		}

	}System.out.print("done");
*/		
		// delete the first vertex, move the column
		if(i==0) 
		{
			for(int z=0; z<incMatrix.length-1;z++) 
			{
				incMatrix = removeCol(incMatrix,1);
			}
		}else 
		{	int count =0;
			int tempColumIndex = index;
			//System.out.println(tempColumIndex);
			for(int k=0;k<i;k++) 
			{	
			
				// calculate the which column should be deleted
				columIndex = findColumnIndex(incMatrix, incMatrix[k][0], incMatrix[i][0]);
				incMatrix = removeCol(incMatrix,columIndex-count);
				count ++;
			}
			for(int x=0;x<incMatrix.length-1-i;x++) 
			{
				incMatrix =removeCol(incMatrix,tempColumIndex-i);
			}
		}

		incMatrix = removeRow(incMatrix, i);
		Ys--;
		//
	} // end of removeVertex()

	public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
		List<MyPair> neighbours = new ArrayList<MyPair>();
		int index = getIndex(vertLabel);
		int tempIndex = 0;
		int startIndex = 0;
		// Search for the Search D for 2D array to find, ensure the weight number is
		// postive

		for (int x = 0; x < index; x++) {
			tempIndex += (x + 1) * (index - x);
			for (int i = 0; i < incMatrix.length; i++) {
				if (isInteger(incMatrix[i][tempIndex])) {
					int weight = Integer.parseInt(incMatrix[i][tempIndex]);
					if (weight > 0) {
						MyPair mp = new MyPair(incMatrix[i][0], weight);
						neighbours.add(mp);
					}
				}
			}
		}
		// Search for the second D for 2D array, where startpoints is specific vertex,
		// but the weight number is negative
		startIndex = findstartIndexForVertex(incMatrix, vertLabel);
		for (int z = 0; z < incMatrix.length; z++) { // how many edges it has for the start points
			for (int y = incMatrix.length - 2 - index; y >= 0; y--) {
				if (isInteger(incMatrix[z][y])) {
					int weight = Integer.parseInt(incMatrix[z][y]);
					if (weight < 0) {
						MyPair mp = new MyPair(incMatrix[z][0], -weight);
						neighbours.add(mp);
					}
				}
			}
		}
		// bubble sort --sort neighours
		for (int i = 0; i < neighbours.size(); i++) {
			for (int j = 0; j < neighbours.size() - 1; j++) {
				if (neighbours.get(j).getValue() < neighbours.get(j + 1).getValue()) {
					Collections.sort(neighbours, new ByWeightComparator());
				}
			}
		}
		if (k > incMatrix.length || k < -1) {
			System.out.println("out of bound");
		}

		if (k == -1) {
			for (int i = 0; i < neighbours.size(); i++) {
				System.out.println(neighbours.get(i).getKey() + " " + neighbours.get(i).getValue());
			}
		} else {
			for (int i = 0; i < k; i++) {
				System.out.println(neighbours.get(i).getKey() + " " + neighbours.get(i).getValue());
			}
		}

		// Implement me!

		return neighbours;
	} // end of inNearestNeighbours()

	public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
		List<MyPair> neighbours = new ArrayList<MyPair>();
		int index = getIndex(vertLabel);
		int tempIndex = 0;
		int startIndex = 0;
		// Search for the Search D for 2D array to find, ensure the weight number is
		// postive
		if (k > incMatrix.length || k < -1) {
			System.out.println("out of bound");
		}
		for (int x = 0; x < index; x++) {
			tempIndex += (x + 1) * (index - x);
			for (int i = 0; i < incMatrix.length; i++) {
				if (isInteger(incMatrix[i][tempIndex])) {
					int weight = Integer.parseInt(incMatrix[i][tempIndex]);
					if (weight < 0) {
						MyPair mp = new MyPair(incMatrix[i][0], -weight);
						neighbours.add(mp);
					}
				}
			}
		}
		// Search for the second D for 2D array, where startpoints is specific vertex,
		// but the weight number is negative
		startIndex = findstartIndexForVertex(incMatrix, vertLabel);
		for (int z = 0; z < incMatrix.length; z++) { // how many edges it has for the start points
			for (int y = incMatrix.length - 2 - index; y >= 0; y--) {
				if (isInteger(incMatrix[z][y])) {
					int weight = Integer.parseInt(incMatrix[z][y]);
					if (weight > 0) {
						MyPair mp = new MyPair(incMatrix[z][0], weight);
						neighbours.add(mp);
					}
				}
			}
		}

		if (k > incMatrix.length || k < -1) {
			System.out.println("out of bound");
		}

		if (k == -1) {
			for (int i = 0; i < neighbours.size(); i++) {
				System.out.println(neighbours.get(i).getKey() + " " + neighbours.get(i).getValue());
			}
		} else {
			for (int i = 0; i < k; i++) {
				System.out.println(neighbours.get(i).getKey() + " " + neighbours.get(i).getValue());
			}
		}
		// Implement me!

		return neighbours;
	} // end of outNearestNeighbours()

	public void printVertices(PrintWriter os) {
		for (int i = 0; i < incMatrix.length; i++) {
			os.print(incMatrix[i][0] + " ");
		}
	} // end of printVertices()

	public void printEdges(PrintWriter os) {
		for (int i = 0; i < incMatrix.length; i++) {
			for (int x = 1; x < incMatrix[i].length; x++) {
				if (isInteger(incMatrix[i][x]) && Integer.parseInt(incMatrix[i][x]) > 0) {
					for (int z = 0; z < incMatrix.length; z++) {
						if (isInteger(incMatrix[z][x])) {
							int weight1 = Integer.parseInt(incMatrix[i][x]);
							int weight2 = Integer.parseInt(incMatrix[z][x]);
							// System.out.println(weight1+" "+weight2);
							if (weight1 + weight2 == 0) {
								System.out.println(incMatrix[i][0] + " " + incMatrix[z][0] + " " + weight1);

							}
						}
					}
				}
			}
		}
	} // end of printEdges()

	public int getIndex(String vertLabel) {
		for (int i = 0; i < incMatrix.length; i++) {
			if (vertLabel.equals(incMatrix[i][0]))
				return i;
		}
		return -1;
	}

	public String[][] resize(String[][] array, int newYs) {

		String[][] temp;
		if (newYs == 1) {
			temp = new String[2][2];
			for (int i = 0; i < array.length; i++) {
				for (int y = 0; y < array[i].length; y++) {

					temp[i][y] = array[i][y];

				}
			}
		} else if (newYs == 2) {
			temp = new String[3][4];
			for (int i = 0; i < array.length; i++) {
				for (int y = 0; y < array[i].length; y++) {
					// System.out.println(i + " " + y);
					temp[i][y] = array[i][y];
					// System.out.println(array[1][0]);

				}
			}
		} else {
			temp = new String[newYs + 1][(newYs * (newYs + 1)) / 2 + 1];
			for (int i = 0; i < array.length; i++) {
				for (int y = 0; y < array[i].length; y++) {
					// System.out.println(i + " " + y);
					temp[i][y] = array[i][y];
				}
			}

		}
		return temp;

	}

	// custom alogrithem for the index for edge, which is the second D for 2D array(
	// like BC BD)
	// find the position of BD
	public int findColumnIndex(String[][] array, String src, String tar) {

		int index = 0;
		int src1 = getIndex(src);
		int tar1 = getIndex(tar);
		int num = array.length;
		for (int x = 0; x < src1; x++) {
			index += num - 1;
			num--;
		}
		index = index + (tar1 - src1);
		return index;
	}

	// custom alogrithem for the index of vertex, get the first one, like AB,AC,AD.
	// Find when A starts
	public int findstartIndexForVertex(String[][] array, String vertex) {
		int index = 0;
		int vert = getIndex(vertex);
		int num = array.length;
		for (int x = 0; x < vert; x++) {
			index += num - 1;
			num--;
		}
		return index + 1;
	}
	
	private String[][] removeCol(String [][] array, int colRemove)
	{
	    int row = array.length;
	    int col = array[0].length;

	    String [][] newArray = new String[row][col-1]; //new Array will have one column less


	    for(int i = 0; i < row; i++)
	    {
	        for(int j = 0,currColumn=0; j < col; j++)
	        {
	            if(j != colRemove)
	            {
	                newArray[i][currColumn++] = array[i][j];
	            }
	        }
	    }

	    return newArray;
	}

	public String[][] removeRow(String[][] array, int index) {
		String[][] newIncMatrix = array;
		int columIndex = 0;
		int startIndex = 0;
		int num = array.length;
		
		/*// Testing
				for (int x = 0; x < newIncMatrix.length; x++) {
					for (int y = 0; y < newIncMatrix[x].length; y++) {
						System.out.print(newIncMatrix[x][y] + " ");
					}
					System.out.println("\n");
				}
				System.out.println("--------------------------------------------");
				// Testing
*/
		// deal with row, move row
		// if the row is the last one
		int count = 0;
		if (index == array.length - 1) {
			for (int x = 0; x < array[index].length; x++) {
				newIncMatrix[index][x] = null;
			}
		} else {

			for (int x = 0; x < array.length; x++) {
				for (int c = 0; c < array[0].length; c++) {
					if (index == x)
						x++;
					newIncMatrix[count][c] = array[x][c];
				}
				count++;
			}
		}
		return newIncMatrix;
	}

	public static boolean isInteger(String s) {
		boolean isValidInteger = false;
		try {
			Integer.parseInt(s);

			// s is a valid integer

			isValidInteger = true;
		} catch (NumberFormatException ex) {
			// s is not an integer
		}

		return isValidInteger;
	}
}

class ByWeightComparator implements Comparator<MyPair> {

	@Override
	public int compare(MyPair o1, MyPair o2) {
		if (o1.getValue() < o2.getValue())
			return -1;
		else if (o1.getValue() == o2.getValue())
			return 0;
		else
			return 1;

	}

}

// end of class IncidenceMatrix
