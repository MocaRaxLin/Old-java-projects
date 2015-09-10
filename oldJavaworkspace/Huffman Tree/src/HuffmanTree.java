import java.util.Stack;

public class HuffmanTree {
	private String symbolString;
	private char[] symbolArray;
	private int[] frequencyArray;
	private Stack<Node> nodeStack = new Stack<Node>();
	public Stack<Node> vaildNodeStack = new Stack<Node>();
	public Node root = new Node();
	
	public HuffmanTree(String input) {
		symbolString = input;
	}

	public HuffmanTree(char[] t, int[] f) {
		symbolArray = t;
		frequencyArray = f;
		putinStack();
		while (nodeStack.size() > 1) {
			sortstack();
			Node right = nodeStack.pop();
			right.putCode('1');
			Node left = nodeStack.pop();
			left.putCode('0');
			Node parent = new Node();
			parent.setLeft(left);
			parent.setRight(right);
			parent.addWeight(left.getWeight() + right.getWeight());
			nodeStack.push(parent);
		}
		root = nodeStack.pop();
		//showDetail(root);
		//showVaildData(root);
		storeVaildData(root);
	}
	private void storeVaildData(Node p) {
		if(p.getElement()!='\u0000'){
			vaildNodeStack.push(p);
		}
		if (p.getLeft() != null) {
			storeVaildData(p.getLeft());
		}
		if (p.getRight() != null) {
			storeVaildData(p.getRight());
		}
	}

	public void showVaildData(Node p) {
		if(p.getElement()!='\u0000'){
			System.out.println(p.getElement() + " " + p.getCode());
		}
		if (p.getLeft() != null) {
			showVaildData(p.getLeft());
		}
		if (p.getRight() != null) {
			showVaildData(p.getRight());
		}
	}

	private void putinStack() {
		for (int i = 0; i < symbolArray.length; i++) {
			Node v = new Node();
			v.setElement(symbolArray[i]);
			v.addWeight(frequencyArray[i]);
			nodeStack.push(v);
		}
	}

	public void sortstack() {
		for (int i = 0; i < nodeStack.size(); i++) {
			for (int j = i + 1; j < nodeStack.size(); j++) {
				if (nodeStack.get(i).getWeight() < nodeStack.get(j).getWeight()) {
					Node t = nodeStack.get(i);
					nodeStack.set(i, nodeStack.get(j));
					nodeStack.set(j, t);
				}
			}
		}
	}

	public void showDetail(Node p) {
		System.out.print("\n(" + p.getElement() + "," + p.getWeight() +","+ p.getCode()+ ") ");
		System.out.print("[ ");
		if (p.getLeft() != null) {
			showDetail(p.getLeft());
		}
		if (p.getRight() != null) {
			showDetail(p.getRight());
		}
		System.out.print("] ");
	}
}
