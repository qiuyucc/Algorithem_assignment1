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
	protected myLinkedList[] Edges;
	protected int numVertices;
	protected int numEdges;
     //wozaixie
	// Default constructor. Set the initial size is 1.
	public AdjList() {
		// Implement me!
		this(1);
	} // end of AdjList()

	// Constructor that set the particular size of graph.
	public AdjList(int size) {
		names = new String[size];
		Edges = new myLinkedList[size];
		for (int i = 0; i < size; i++) {
			Edges[i] = new myLinkedList();
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
			Edges = resize(Edges, 2 * numVertices + 1);
		}
		names[numVertices++]=vertLabel;
	} // end of addVertex()

	public void addEdge(String srcLabel, String tarLabel, int weight) {
		// Implement me!
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

} // end of class AdjList

//Java program to implement 
//a Singly Linked List 
class myLinkedList {

	Node head; // head of list

	// Linked list Node.
	// This inner class is made static
	// so that main() can access it
	class Node {

		int data;
		Node next;

		// Constructor
		Node(int d) {
			data = d;
			next = null;
		}
	}

	// **************INSERTION**************

	// Method to insert a new node
	public myLinkedList insert(myLinkedList list, int data) {
		// Create a new node with given data
		Node new_node = new Node(data);
		new_node.next = null;

		// If the Linked List is empty,
		// then make the new node as head
		if (list.head == null) {
			list.head = new_node;
		} else {
			// Else traverse till the last node
			// and insert the new_node there
			Node last = list.head;
			while (last.next != null) {
				last = last.next;
			}

			// Insert the new_node at last node
			last.next = new_node;
		}

		// Return the list by head
		return list;
	}

	// **************TRAVERSAL**************

	// Method to print the LinkedList.
	public void printList(myLinkedList list) {
		Node currNode = list.head;

		System.out.print("\nLinkedList: ");

		// Traverse through the LinkedList
		while (currNode != null) {
			// Print the data at current node
			System.out.print(currNode.data + " ");

			// Go to next node
			currNode = currNode.next;
		}
		System.out.println("\n");
	}

	// **************DELETION BY KEY**************

	// Method to delete a node in the LinkedList by KEY
	public myLinkedList deleteByKey(myLinkedList list, int key) {
		// Store head node
		Node currNode = list.head, prev = null;

		//
		// CASE 1:
		// If head node itself holds the key to be deleted

		if (currNode != null && currNode.data == key) {
			list.head = currNode.next; // Changed head

			// Display the message
			System.out.println(key + " found and deleted");

			// Return the updated List
			return list;
		}

		//
		// CASE 2:
		// If the key is somewhere other than at head
		//

		// Search for the key to be deleted,
		// keep track of the previous node
		// as it is needed to change currNode.next
		while (currNode != null && currNode.data != key) {
			// If currNode does not hold key
			// continue to next node
			prev = currNode;
			currNode = currNode.next;
		}

		// If the key was present, it should be at currNode
		// Therefore the currNode shall not be null
		if (currNode != null) {
			// Since the key is at currNode
			// Unlink currNode from linked list
			prev.next = currNode.next;

			// Display the message
			System.out.println(key + " found and deleted");
		}

		//
		// CASE 3: The key is not present
		//

		// If key was not present in linked list
		// currNode should be null
		if (currNode == null) {
			// Display the message
			System.out.println(key + " not found");
		}

		// return the List
		return list;
	}

	// **************DELETION AT A POSITION**************

	// Method to delete a node in the LinkedList by POSITION
	public myLinkedList deleteAtPosition(myLinkedList list, int index) {
		// Store head node
		Node currNode = list.head, prev = null;

		//
		// CASE 1:
		// If index is 0, then head node itself is to be deleted

		if (index == 0 && currNode != null) {
			list.head = currNode.next; // Changed head

			// Display the message
			System.out.println(index + " position element deleted");

			// Return the updated List
			return list;
		}

		//
		// CASE 2:
		// If the index is greater than 0 but less than the size of LinkedList
		//
		// The counter
		int counter = 0;

		// Count for the index to be deleted,
		// keep track of the previous node
		// as it is needed to change currNode.next
		while (currNode != null) {

			if (counter == index) {
				// Since the currNode is the required position
				// Unlink currNode from linked list
				prev.next = currNode.next;

				// Display the message
				System.out.println(index + " position element deleted");
				break;
			} else {
				// If current position is not the index
				// continue to next node
				prev = currNode;
				currNode = currNode.next;
				counter++;
			}
		}

		// If the position element was found, it should be at currNode
		// Therefore the currNode shall not be null
		//
		// CASE 3: The index is greater than the size of the LinkedList
		//
		// In this case, the currNode should be null
		if (currNode == null) {
			// Display the message
			System.out.println(index + " position element not found");
		}

		// return the List
		return list;
	}
}
