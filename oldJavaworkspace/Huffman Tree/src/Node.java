import java.util.Stack;

public class Node {
	private char element;
	private Node parent = null;
	private Node left = null;
	private Node right = null;
	private int weight = 0;
	public Stack<Character> codeStack = new Stack<Character>();

	public Node() {
		parent = null;
		left = null;
		right = null;
	}

	public void setElement(char setElment) {
		element = setElment;
	}

	public char getElement() {
		return element;
	}

	public void setParentNode(Node p) {
		parent = p;
	}

	public Node getParent() {
		return parent;
	}

	public void setLeft(Node p) {
		left = p;
	}

	public Node getLeft() {
		return left;
	}

	public void setRight(Node p) {
		right = p;
	}

	public Node getRight() {
		return right;
	}

	public void addWeight(int w) {
		weight += w;
	}

	public int getWeight() {
		return weight;
	}

	public void putCode(char c) {
		codeStack.push(c);
		if (left != null) {
			left.putCode(c);
		}
		if (right != null) {
			right.putCode(c);
		}
	}

	public String getCode() {
		String output = "";
		for (int i = codeStack.size() - 1; i >= 0; i--) {
			output += codeStack.get(i);
		}
		return output;
	}
}
