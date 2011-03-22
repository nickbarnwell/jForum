package utilities;

import java.util.ArrayList;
/**
 * This class is an implementation of the binary tree ADT using
 * generics and comparables to work for multiple datatypes.
 * 
 * @author Nick Barnwell
 *
 * @param <Node> The type that each node will be comprised of.
 * 
 * @mastery Polymorphism
 */
public class BTree<Node extends Comparable<? super Node>> {
	
	private Entry<Node> root;
	private Integer size;
	private ArrayList<Node> sort = new ArrayList<Node>();
	/**
	 * This private class represents a single Node on the tree.
	 * 
	 *  Each node has a link to what is to its left and right.
	 *  Its fields are its own element and the left and right
	 *  entries. The constructor sets the left and right null,
	 *  and the element to the element passed in.
	 * @author Nick Barnwell
	 *
	 * @param <Node>
	 */
	private static class Entry<Node extends Comparable<? super Node>> {
		Node element;
		Entry<Node> left;
		Entry<Node> right;

		Entry(Node elem) {
			element = elem;
			left = right = null;
		}
		/**
		 * Returns the element object. 
		 * @return
		 */
		public Node getNode() {
			return this.element;
		}
	}
	/**
	 * Constructor for the class. Sets root node to null and total 
	 * size to 0. 
	 */
	public BTree() {
		root = null;
		size = 0;
	}
	/**
	 * This method simplifies the creation of a new tree by allowing the
	 * instantiation of the a non-typed tree at first before creating a 
	 * generically typed one. Purely syntactic sugar.
	 * 
	 * @param <Node> The type of tree to be returned
	 * @return BTree of type Node
	 */
	public static <Node extends Comparable<? super Node>> BTree<Node> createTree() {
		return new BTree<Node>();
	}
	/**
	 * @return The size of the tree as an Integer
	 */
	public Integer getSize() {
		return size;
	}
	/**
	 * Determines if the tree is empty or not.
	 * 
	 * @return Boolean: True if empty, false otherwise
	 */
	public Boolean isEmpty() {
		if(size == 0 || root.equals(null)) {
			return true;
		}
		return false;
	}
	/**
	 * Inserts a node into the tree, calls the private method insert
	 * @param value Value to insert
	 */
	public void insert(Node value) {
		root = insert(value, root);
	}
	/**
	 * 
	 * @param value The object to be inserted into the tree
	 * @param entry The root node
	 * @return Entry The value that has been inserted as Type Entry 
	 */
	private Entry<Node> insert(Node value, Entry<Node> entry) {
		if (entry == null) //If the root isn't set, set it to the value.
			entry = new Entry<Node>(value);
		else if (value.compareTo(entry.element) < 0)
			entry.left = insert(value, entry.left); //Otherwise if entry is smaller, insert it to the left
		else if (value.compareTo(entry.element) > 0)
			entry.right = insert(value, entry.right); //Otherwise insert to right
		else
			entry.left = insert(value, entry.left); //Unless it is equal, and then insert it to the left. THis trades runtime efficiency for allowing duplicate entries
			//throw new RuntimeException("Duplicate Entry : " + value.toString());
		size += 1;
		return entry;
	}
	/**
	 * Finds the first instance of a value in the tree
	 * 
	 * @param val
	 * @param entry
	 * @return
	 */
	public Entry<Node> find(Node val, Entry<Node> entry) {
		while (entry != null) {
			if (val.compareTo(entry.element) < 0)
				entry = entry.left;
			else if (val.compareTo(entry.element) > 0)
				entry = entry.right;
			else
				return entry;
		}
		return null;
	}
	/**
	 * @return An ArrayList of type node of the list sorted in order.
	 * @mastery Recursion
	 */
	public ArrayList<Node> sortInOrder() {
		sort.clear();
		getInOrderTraversal(root);
		return sort;
	}	

	private void getInOrderTraversal(Entry<Node> entry) {
		if (entry != null) { //While referenced node isn't null, recursively call getInOrderTraversal
			getInOrderTraversal(entry.right); //First add right tree
			sort.add(entry.getNode()); //Add entry to node
			getInOrderTraversal(entry.left); //Move to left
		}
	}
}
