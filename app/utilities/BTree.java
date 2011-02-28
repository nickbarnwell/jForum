package utilities;

import java.util.ArrayList;

public class BTree<Node extends Comparable<? super Node>> {

	private Entry<Node> root;
	private Integer size;
	private ArrayList<Node> sort = new ArrayList<Node>();
	
	private static class Entry<Node extends Comparable<? super Node>> {
		Node element;
		Entry<Node> left;
		Entry<Node> right;

		Entry(Node elem) {
			element = elem;
			left = right = null;
		}
		
		public Node getNode() {
			return this.element;
		}
	}

	public BTree() {
		root = null;
		size = 0;
	}
	
	public static <Node extends Comparable<? super Node>> BTree<Node> createTree() {
		return new BTree<Node>();
	}
	
	public Integer getSize() {
		return size;
	}
	
	public Boolean isEmpty() {
		if(size == 0 || root.equals(null)) {
			return true;
		}
		return false;
	}
	
	public void insert(Node value) {
		root = insert(value, root);
	}

	private Entry<Node> insert(Node value, Entry<Node> entry) {
		if (entry == null)
			entry = new Entry<Node>(value);
		else if (value.compareTo(entry.element) < 0)
			entry.left = insert(value, entry.left);
		else if (value.compareTo(entry.element) > 0)
			entry.right = insert(value, entry.right);
		else
			entry.left = insert(value, entry.left); //Allow dups to left
			//throw new RuntimeException("Duplicate Entry : " + value.toString());
		size += 1;
		return entry;
	}

	private Entry<Node> find(Node val, Entry<Node> entry) {
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

	public ArrayList<Node> sortInOrder() {
		sort.clear();
		getInOrderTraversal(root);
		return sort;
	}	

	private void getInOrderTraversal(Entry<Node> entry) {
		if (entry != null) {
			getInOrderTraversal(entry.right);
			System.out.println(entry);
			sort.add(entry.getNode());
			getInOrderTraversal(entry.left);
		}
	}
}
