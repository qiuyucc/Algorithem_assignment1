import java.io.*;
import java.lang.reflect.Array;
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
	private int rows;
	private int cols;
	private String[][] incMatrix;

	public IncidenceMatrix() {
		this(0, 0);
	} // end of IncidentMatrix()

	public IncidenceMatrix(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
		incMatrix = new String[cols + 1][rows + 1];
	}

	public void addVertex(String vertLabel) {
		// check the vertex existed or not
		if (getIndex(vertLabel) != -1) {
			System.out.println("AddVertex: " + vertLabel);
			System.out.println("Failed, vertex already exists.");
			return;
		}

		// if array of vertex is full, we have to expand it and Edges
		if (incMatrix.length == cols) {
			incMatrix = resize(incMatrix, cols++);
		}
		incMatrix[cols++][0] = vertLabel;
		System.out.println("This vertex added: " + incMatrix[cols - 1][0]);
	} // end of addVertex()

	public void addEdge(String srcLabel, String tarLabel, int weight) {
		int index;
		int posWeight = weight;
		int negWeight = -weight;
		if (srcLabel == tarLabel) {
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
				incMatrix[src][index] = Integer.toString(negWeight);
				incMatrix[tar][index] = Integer.toString(posWeight);
			}

		} else {
			System.out.println("No self connected edge in this case!");
		}

	} // end of addEdge()

	public int getEdgeWeight(String srcLabel, String tarLabel) {
		int index, weight = 0;
		int src = getIndex(srcLabel);
		if (srcLabel == tarLabel) {
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
				weight = Integer.parseInt(incMatrix[tar][index]);
			}
		} else {
			System.out.println("No self connected edge in this case!");
		}
		return weight;

	} // end of existEdge()

	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
		int index = 0;
		int src = getIndex(srcLabel);
		int posWeight = weight;
		int negWeight = -weight;
		if (srcLabel == tarLabel) {
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
				incMatrix[src][index] = Integer.toString(negWeight);
				incMatrix[tar][index] = Integer.toString(posWeight);
				System.out.println("updated success");
			}
		} else {
			System.out.println("No self connected edge in this case!");
		}
	} // end of updateWeightEdge()

	public void removeVertex(String vertLabel) {
		int i = getIndex(vertLabel);
		if (i == -1) {
			System.out.print("remove vertex:" + vertLabel);
			System.out.println("does not exist");
		}

		int index = findstartIndexForVertex(incMatrix, vertLabel);
		// delete the weight in the 2D array which relate with VertLabel is it's source
		for (int x = i; x < incMatrix.length; x++) {
			for (int y = 0; x < incMatrix.length - i - 1; y++) {
				incMatrix[x][index + y] = null;
			}
		}

		// delte the weight in the 2D arary which relate with VertLabel is it's endpoint
		/*
		 * only need to check the vertex before
		 */
		for (int j = 0; j < i; j++) {
			int indexEnd = findColumnIndex(incMatrix, incMatrix[j][0], vertLabel);
			incMatrix[j][indexEnd] = null;
			incMatrix[i][indexEnd] = null;
		}

		// delete the whole row of the matrix
		cols--;
		removeRow(incMatrix, i);
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
			for (int x = 1; x <= incMatrix[i].length; x++) {
				if (isInteger(incMatrix[i][x])) {
					for (int z = 0; z < incMatrix.length; z++) {
						if (isInteger(incMatrix[z][x])) {
							int weight1 = Integer.parseInt(incMatrix[i][x]);
							int weight2 = Integer.parseInt(incMatrix[z][x]);
							if (weight1 > weight2)
								System.out.println(incMatrix[i][0] + " " + incMatrix[z][0] + " " + weight1);
							else
								System.out.println(incMatrix[z][0] + " " + incMatrix[i][0] + " " + weight2);
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

	public String[][] resize(String[][] array, int newCols) {

		String[][] temp = new String[newCols][(newCols * (newCols - 1)) / 2];
		int smallerCols = newCols;
		if (array.length < newCols)
			smallerCols = array.length;

		for (int i = 0; i < array.length; i++) {
			for (int y = 0; y < array.length; y++) {
				temp[i][y] = array[i][y];
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
		for (int x = 1; x <= src1; x++) {
			index += x * (num - 1);
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
		for (int x = 1; x <= vert; x++) {
			index += x * (num - 1);
			num--;
		}
		return index + 1;
	}

	public String[][] removeRow(String[][] array, int index) {
		String[][] newIncMatrix = array;
		int columIndex = 0;
		int startIndex = 0;
		int num = array.length;

		// deal with column first. If it is first vertex, the 2D array can be removed
		// directly
		if (index == 0) {
			for (int x = 0; x < array.length; x++) {
				for (int y = 1; y < array[x].length; y++) {
					newIncMatrix[x][y] = array[x][y + array.length - 1];
				}
			}
		} else { // if the vertex is not the first, the 2D array should be moved many times based
					// on index of vertex
					// move the column when the specific vertex is end point
			for (int x = 0; x < index; x++) {
				// calculate the which column should be deleted
				columIndex += (x + 1) * (index - x);
				for (int z = 0; z < array[z].length; x++) {
					newIncMatrix[z][columIndex] = array[z][columIndex + 1];
				}

			}

			for (int x = 1; x <= index; x++) {
				startIndex += x * (num - 1);
				num--;
			}
			// get the start index
			startIndex = startIndex + 1;
			// move the column when the specific vertex is start points

			for (int z = 0; z < array.length; z++) {
				// how many edges it has for the start points
				for (int numOfEdgeOfVertex = array.length - 2 - index; numOfEdgeOfVertex >= 0; numOfEdgeOfVertex--) {
					newIncMatrix[z][startIndex + numOfEdgeOfVertex] = array[z][startIndex + numOfEdgeOfVertex + 1];
				}

			}
		}

		// deal with row, move row
		// if the row is the last one
		if (index == array.length - 1) {
			for (int x = 0; x < array.length; x++) {
				newIncMatrix[index][x] = null;
			}
		} else {
			for (int x = index; x < array.length - 1; x++) {
				for (int y = 0; x < array[index + 1].length; y++) {
					newIncMatrix[index][y] = array[index + 1][y];
				}
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
