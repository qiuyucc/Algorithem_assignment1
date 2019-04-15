import java.io.*;
import java.util.*;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class. You may add
 * methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */

public class AdjList extends AbstractAssocGraph {

	/**
	 * Contructs empty graph.
	 */

	protected String[] names;
	protected myLinkedList[] list;
	protected int numVertices;
	protected int numEdges;

	// Default constructor. Set the initial size is 1.
	public AdjList() {
		// Implement me!
		this(1);
	} // end of AdjList()

	// Constructor that set the particular size of graph.
	public AdjList(int size) {
		names = new String[size];
		list = new myLinkedList[size];
		for (int i = 0; i < size; i++) {
			list[i] = new myLinkedList();
		}
	}

	public int numberOfVertices() {
		return numVertices;
	}

	public int numberOfEdges() {
		return numEdges;
	}

	// Find whether the particular vertex in the graph,
	// return -1 if not found.
	public int getIndex(String vertLabel) {
		for (int i = 0; i < numVertices; i++) {
			if (vertLabel.equals(names[i]))
				return i;
		}
		return -1;
	}

	// Resizes the array of vertices.
	protected String[] resize(String[] array, int newSize) {
		String[] temp = new String[newSize];

		int smallerSize = newSize;
		if (array.length < smallerSize)
			smallerSize = array.length;

		// copy all the vertices to the new array
		for (int i = 0; i < smallerSize; i++)
			temp[i] = array[i];

		return temp;
	}

	protected myLinkedList[] resize(myLinkedList[] array, int newSize) {
		myLinkedList[] temp = new myLinkedList[newSize];

		int smallerSize = newSize;
		if (array.length < newSize)
			smallerSize = array.length;

		for (int i = 0; i < smallerSize; i++)
			temp[i] = array[i];

		for (int i = smallerSize; i < temp.length; i++)
			temp[i] = new myLinkedList();

		return temp;
	}

	public void addVertex(String vertLabel) {
		// check the vertex existed or not
		if (getIndex(vertLabel)!=-1) {
			System.out.println("AddVertex: " + vertLabel);
			System.out.println("Failed, vertex already exists.");
			return;
		}
		// if array of vertex is full, we have to expand it and Edges
		if (names.length == numVertices) {
			names = resize(names, 2 * numVertices + 1);
			list = resize(list, 2 * numVertices + 1);
		}
		
		names[numVertices++] = vertLabel;
	    
		System.out.println("This vertex added: " + names[numVertices - 1] +numVertices);
	} // end of addVertex()

	public void addEdge(String srcLabel, String tarLabel, int weight) {

		Node newNode = new Node(tarLabel, weight);
		int i = getIndex(srcLabel);
		if (i == -1) {
			System.out.print("addEdge failed: ");
			System.out.print(srcLabel);
			System.out.println(" - does not exist.");
			return;
		}
		int j = getIndex(tarLabel);
		if (j == -1) {
			System.out.print("addEdge failed: ");
			System.out.print(tarLabel);
			System.out.println(" - does not exist.");
			return;
		}
		if (this.list[i].find(newNode) == true) {
			System.out.print("addEdge failed: ");
			System.out.println(" - edge already exist.");
			return;
		}
		System.out.println("addEdge success");
		list[i].add(newNode);
		list[i].mLengthInc();
		numEdges++;

	} // end of addEdge()

	public int getEdgeWeight(String srcLabel, String tarLabel) {
		// Implement me!
		int i = getIndex(srcLabel);
		if (i == -1) {
			System.out.print("getEdge failed: ");
			System.out.print(srcLabel);
			System.out.println(" does not exist.");
			return EDGE_NOT_EXIST;
		}
		if (this.list[i].find(tarLabel) == null) {
			System.out.print("getEdge failed: ");
			System.out.print(tarLabel);
			System.out.println(" does not exist.");
			return EDGE_NOT_EXIST;
		}
		return this.list[i].find(tarLabel).getWeight();
		// update return value
	} // end of existEdge()

	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {

		Node newNode = new Node(tarLabel, weight);
		int i = getIndex(srcLabel);
		if (i == -1) {
			System.out.print("updateWeightEdge failed: ");
			System.out.print(srcLabel);
			System.out.println(" does not exist.");
			return;
		}
		if (this.list[i].find(newNode) == false) {
			System.out.print("updateWeightEdge failed: ");
			System.out.print("Edge");
			System.out.println(" does not exist.");
			return;
		}
		if (this.list[i].find(tarLabel).getWeight() == weight) {
			System.out.println("updated fail - same value");
			return;
		}
		System.out.println("updated success");
		this.list[i].find(tarLabel).setWeight(weight);

	} // end of updateWeightEdge()

	public void removeVertex(String vertLabel) {

		int i = getIndex(vertLabel);
		if (i == -1) {
			System.out.print("remove failed: ");
			System.out.print(vertLabel);
			System.out.println(" does not exist.");
			return;
		}
		// delete the vertLabel in the names array
		for (int j = i; j < names.length - 1; j++) {
			names[j] = names[j + 1];
		}
		numVertices--;

		// clear all the destination of this source vertex
		list[i].clear();

		// sync the index array to the name array
		for (int j = i; j < list.length - 1; j++) {
			list[j] = list[j + 1];

		}
		for (int z = 0; z < list.length; z++) {
			list[z].delete(vertLabel);
		}
		
		System.out.println("remove success");

	} // end of removeVertex()

	public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
		List<MyPair> neighbours = new ArrayList<MyPair>();
		List<MyPair> nbTemp = new ArrayList<MyPair>();
		MyPair pairTemp;
		Node[] nodeTemp = null;
		String temp;
		int weightTemp;

		int i = getIndex(vertLabel);
		if (i == -1) {
			System.out.print("search failed: ");
			System.out.print(vertLabel);
			System.out.println("does not exist.");
			return neighbours;
		}
		if (k > list.length - 1 || k < -1) {
			System.out.print("out of bound");
		}
		if (k != -1) {

			for (int j = 0; j < names.length; j++) {
				for (int z = 0; z < list[j].mLength; j++) {
					if (list[j].find(vertLabel) != null) {
						temp = names[j];
						weightTemp = list[j].find(vertLabel).getWeight();
						MyPair myPair = new MyPair(temp, weightTemp);
						nbTemp.add(myPair);
					}
				}
			}

			for (int j = 0; j < nbTemp.size() - 1; j++) {
				for (int y = 0; y < nbTemp.size() - 1 - j; y++) {
					int tempWeight1 = nbTemp.get(y).getValue();
					int tempWeight2 = nbTemp.get(y + 1).getValue();
					if (tempWeight1 < tempWeight2) {
						pairTemp = nbTemp.get(y);
						nbTemp.set(y, nbTemp.get(y + 1));
						nbTemp.set(y + 1, pairTemp);
					}
				}
			}

			for (int x = 0; x < k; x++) {
				if(x<=nbTemp.size()) 
				{
					MyPair newPair = new MyPair(nbTemp.get(x).getKey(), nbTemp.get(x).getValue());
					neighbours.add(newPair);
				}
				else 
				{
					System.out.println("out of bound -- vertex number");
				}
				
			}

		} else {
			// K=-1 print all the connections
			for (int j = 0; j < names.length; j++) {
				for (int z = 0; z < list[j].mLength; j++) {
					if (list[j].find(vertLabel) != null) {
						temp = names[j];
						weightTemp = list[j].find(vertLabel).getWeight();
						MyPair myPair = new MyPair(temp, weightTemp);
						neighbours.add(myPair);
					}

				}
			}

		}
		/*for(int x=0; x<neighbours.size();x++) 
		{
			System.out.println(neighbours.get(x).getKey()+" "+ neighbours.get(x).getValue());
		}*/
		
		return neighbours;
	} // end of inNearestNeighbours()
		// Find the tarLabel of this vertex

	public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
		List<MyPair> neighbours = new ArrayList<MyPair>();

		int i = getIndex(vertLabel);
		if (i == -1) {
			System.out.print("search failed: ");
			System.out.print(vertLabel);
			System.out.println("does not exist.");
			return neighbours;
		}
		String[] nbrs = getNeighbors(vertLabel); // vertex a, neighbor: b,c,d
		if (k > list.length - 1 || k < -1) {
			System.out.println("out of bound");
		}
		if (k != -1) {

			String[] nbrsTemp = nbrs;
			String tempName = null;
			for (int z = 0; z < nbrs.length - 1; z++) {
				for (int y = 0; y < nbrs.length - 1 - z; y++) {
					int tempWeight1 = getWeight(i, y);
					int tempWeight2 = getWeight(i, y + 1);
					if (tempWeight1 < tempWeight2) {
						tempName = nbrs[y];
						nbrs[y] = nbrs[y + 1];
						nbrs[y + 1] = tempName;
					}
				}
			}
			for (int x = 0; x < k; x++) {
				if(x<=nbrs.length-1) 
				{
					MyPair newPair = new MyPair(nbrsTemp[x], list[i].find(nbrsTemp[x]).getWeight());
					neighbours.add(newPair);
				}
				else 
				{
					
					System.out.println("out of bound");
				}
			}

		} else { // get all the neighbor of specific vertex
				
			for (int j = 0; j < nbrs.length; j++) {
				int tempWeight = getWeight(i, j);
				
				MyPair newPair = new MyPair(nbrs[j], tempWeight);
				neighbours.add(newPair);
			}
		}

			
		return neighbours;
	} // end of outNearestNeighbours()

	public Integer getWeight(int i, int j) {
		if ((i < 0) || (i > numVertices - 1)) {
			System.out.print("getWeight failed: ");
			System.out.print("index " + i);
			System.out.println(" out of bounds.");
			return -1;
		}

		if ((j < 0) || (j > numVertices - 1)) {
			System.out.print("getWeight failed: ");
			System.out.print("index " + j);
			System.out.println(" out of bounds.");
			return -1;
		}
		
		// Look for vertex j in list[i]
//System.out.println("Looking for " + names[j] + " in linked list " + i);
		Node e = list[i].find(list[i].get(j));

		// If vertex j is found in Edges[i] then return the weight of
		// the edge, otherwise return null
		if (e != null)
			return e.getWeight();
		else
			return -1;
	}

	public void printVertices(PrintWriter os) {
		//when use PRINTWRITER to test, remember to flush and close.
		//System.out.println(numVertices);
		/*for (int i = 0; i < numVertices; i++) {
			System.out.println(names[i] + " ");
		}*/
		for (int i = 0; i < numVertices; i++) {
			os.println(names[i] + " ");
		}
		
	} // end of printVertices()

	public void printEdges(PrintWriter os) {
   /*  try 
      {
     
    	  for (int i = 0; i < numVertices; i++) {
  			int[] nbrs = getNeighbors(i);
  			for (int j = 0; j < nbrs.length; j++) {
  				
  					
  					if (list[i].find(names[nbrs[j]]).getWeight() == 0)
  	  					continue;
  	  				os.println(names[i] + " " + names[nbrs[j]]+" "+list[i].find(names[nbrs[j]]).getWeight());
  	  			
  				
  			}
  		}
      }
      catch(RuntimeException e) 
      {
    	  e.printStackTrace();
      }
		*/
		for(int i = 0;i<numVertices;i++) 
		{
			list[i].displayList(names,i,os);
		}
		
	} // end of printEdges()

	// returns the name of all the neighbors of a given vertex in a string array
	public String[] getNeighbors(String vertex) {
		int source = getIndex(vertex);
		if (source == -1) {
			System.out.print("getNeighbors failed: Vertex ");
			System.out.print(vertex);
			System.out.println(" does not exist.");
			return null;
		}
		return list[source].copyIntoArray();
	}
	// returns the indices of all the neighbors of a given vertex.
	// The vertex is specified as an index and the neighbors are returned in an int
	// array

	public int[] getNeighbors(int index) {
		if ((index < 0) || (index >= numVertices)) {
			System.out.print("getNeighbors failed: Index");
			System.out.print(index);
			System.out.println(" is out of bounds.");
			return null;
		}

// Call the earlier getNeighbors function to get the names of
// neighbors
		String[] nbrNames = getNeighbors(names[index]);

// Turn the array of neighbor names into an array
// of neighbor indices
		int[] nbrIndices = new int[nbrNames.length];
		for (int i = 0; i < nbrIndices.length; i++)
			nbrIndices[i] = getIndex(nbrNames[i]);

		return nbrIndices;
	}

	protected class Node {
		/** Stored value of node. */
		protected String mValue;
		/** Reference to next node. */
		protected Node mNext;
		protected int weight;

		public Node(String value, int weight) {
			mValue = value;
			mNext = null;
			this.weight = weight;
		}

		public String getValue() {
			return mValue;
		}

		public Node getNext() {
			return mNext;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public void setValue(String value) {
			mValue = value;
		}

		public void setNext(Node next) {
			mNext = next;
		}

		public void displayNode() {
			System.out.print(mValue);
		}
	} // end of inner class Node

	class myLinkedList {
		// Reference to head node.
		protected Node mHead;

		// Length of list.
		protected int mLength;

		public myLinkedList() {
			mHead = null;
			mLength = 0;
		} // end of SimpleList()

		/**
		 * Add a new value to the start of the list.
		 * 
		 * @param newValue Value to add to list.
		 */

		public void add(Node newNode) {
			if (mHead == null) {
				mHead = newNode;

			} else {
				newNode.setNext(mHead);
				mHead = newNode;

			}
		}

		public int mLengthInc() {
			return mLength++;
		}

		public int mLengthDec() {
			return mLength--;
		}

		public boolean find(Node node) {
			// if empty linked list, then return null

			if (mHead == null)
				return false;
			// otherwise scan list until a node equals edge is found
			Node current = mHead;
			while (!(current.getValue().equals(node.getValue()))) {
				if (current.getNext() == null)
					return false;
				else
					current = current.getNext();
			}
			return true; // Found it
		}

		public Node find(String label) {
			// if empty linked list, then return null

			if (mHead == null)
				return null;
			// otherwise scan list until a node equals edge is found
			Node current = mHead;
			while (!(current.getValue().equals(label))) {
				if (current.getNext() == null)
					return null;
				else
					current = current.getNext();
			}
			return current;
		}

		public void displayList(String[] list,int i,PrintWriter os) {
			//System.out.print("List (first-->last):");
			Node current = mHead;
			while (current != null &&current.getWeight() !=0) {				
					os.println(list[i] +" "+ current.getValue()+" "+current.getWeight());
					current = current.getNext();				
			}
			//System.out.println("");
		}

		public void clear() {
			mHead = null;
			mLength = 0;
		}

		/**
		 * Delete given value from list (delete first instance found).
		 * 
		 * @param value Value to remove.
		 * @return True if deletion was successful, otherwise false.
		 */
		public boolean delete(String vertex) {
			// if empty linked list, then return null
			Node current = null;
			Node previous = null;
			if (mHead == null)
				return false;
			current = mHead;
			previous = mHead;

			while (current.getValue() != vertex) {
				if (current.getNext() == null)
					return false;
				else {
					previous = current;
					current = current.getNext();
				}
			}
			if (current == mHead)
				mHead = mHead.getNext();
			else
				previous.setNext(current.getNext());

			mLengthDec();
			return true;

		}

		/**
		 * Print the list in head to tail.
		 */
		public void print() {
			System.out.println(toString());
		} // end of print()

		/**
		 * @return String representation of the list.
		 */
		public String toString() {
			Node currNode = mHead;

			StringBuffer str = new StringBuffer();

			while (currNode != null) {
				str.append(currNode.getValue() + " ");
				currNode = currNode.getNext();
			}

			return str.toString();
		} // end of getString();

		/*
		 * The funtion returns a copy of the keys (Strings) in the list in an array, but
		 * with items in reverse order
		 */
		public String get(int index)
		{
		
			// Initialize current and initialize a counter
			Node current = mHead;
			int count = 0;

			// Scan as many nodes as specified by index
			while(count < index)
			{
				// check to make sure that we have not scanned
				// past the end of the list; if not move 
				// current and increment counter
				if(current != null)
				{
					current = current.getNext();
					count++;
				}
				else
					return null;
			}

			// We have reached index
	       		if(current != null)
				return current.mValue;
	                else
	                	return null;
		}	
		public String[] copyIntoArray() {
			String[] temp = new String[mLength];
			Node current = mHead;

			// Scan this list from front to back
			int index = 0;

			while (current != null) {
				temp[index++] = current.getValue();
				current = current.getNext();
			}
			return temp;
		}
	}
} // end of class AdjList
