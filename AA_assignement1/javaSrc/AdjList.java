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
		if (getIndex(vertLabel) != -1) {
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
	} // end of addVertex()

	public void addEdge(String srcLabel, String tarLabel, int weight) {

		Node newNode = new Node(tarLabel, weight);
		int i = getIndex(srcLabel);
		if (i == -1) {
			System.out.print("addEdge failed: ");
			System.out.print(srcLabel);
			System.out.println("does not exist.");
			return;
		}
		if (this.list[i].find(newNode) == false) {
			System.out.print("addEdge failed: ");
			System.out.print(tarLabel);
			System.out.println("does not exist.");
			return;
		}
		System.out.println("addEdge success");
		list[i].add(newNode);

			
	} // end of addEdge()

	public int getEdgeWeight(String srcLabel, String tarLabel) {
		// Implement me!

		// update return value
		return EDGE_NOT_EXIST;
	} // end of existEdge()

	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
		// Implement me!
	} // end of updateWeightEdge()

	public void removeVertex(String vertLabel) {
		// Implement me!
	} // end of removeVertex()

	public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
		List<MyPair> neighbours = new ArrayList<MyPair>();

		// Implement me!

		return neighbours;
	} // end of inNearestNeighbours()

	public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
		List<MyPair> neighbours = new ArrayList<MyPair>();

		// Implement me!

		return neighbours;
	} // end of outNearestNeighbours()

	public void printVertices(PrintWriter os) {
		// Implement me!
	} // end of printVertices()

	public void printEdges(PrintWriter os) {
		// Implement me!
	} // end of printEdges()

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

		/**
		 * Delete given value from list (delete first instance found).
		 * 
		 * @param value Value to remove.
		 * @return True if deletion was successful, otherwise false.
		 */
		public boolean remove(int value) {
			// YOUR IMPLEMENTATION
			if (mLength == 0) {
				return false;
			}

			Node currNode = mHead;
			Node prevNode = null;

			// check if value is head node
			if (currNode.getValue() == value) {
				mHead = currNode.getNext();
				mLength--;
				return true;
			}

			prevNode = currNode;
			currNode = currNode.getNext();

			while (currNode != null) {
				if (currNode.getValue() == value) {
					prevNode.setNext(currNode.getNext());
					currNode = null;
					mLength--;
					return true;
				}
				prevNode = currNode;
				currNode = currNode.getNext();
			}

			return false;
		} // end of delete()

		/**
		 * Delete value (and corresponding node) at position 'index'. Indices start at
		 * 0.
		 * 
		 * @param index Position in list to get new value for.
		 * @param dummy Dummy variable, serves no use apart from distinguishing
		 *              overloaded methods.
		 * @return Value of node that was deleted.
		 */
		public int remove(int index, boolean dummy) throws IndexOutOfBoundsException {
			// YOUR IMPLEMENTATION
			if (index >= mLength || index < 0) {
				throw new IndexOutOfBoundsException("Supplied index is invalid.");
			}

			Node currNode = mHead;
			Node prevNode = null;

			int value;
			// deleting head
			if (index == 0) {
				value = currNode.getValue();
				mHead = currNode.getNext();
			} else {
				for (int i = 0; i < index; ++i) {
					prevNode = currNode;
					currNode = currNode.getNext();
				}

				value = currNode.getValue();
				prevNode.setNext(currNode.getNext());
				currNode = null;
			}

			mLength--;

			return value;
		} // end of delete()

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

	}
} // end of class AdjList
