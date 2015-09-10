import java.util.Stack;

public class BinaryTree<E> {
	private int n;
	private Position<E> root;
	private Stack<Position<E>> stack;

	public BinaryTree() {
		root = null;
		stack = new Stack<Position<E>>();
	}

	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void addRoot(E r) {
		root = new Position<E>();
		stack.push(root);
		root.setElement(r);
		n++;
	}

	public Position<E> root() {
		return root;
	}

	public void expandExternal(Position<E> p) {
		if (p.isExternal()) {
			Position<E> left = new Position<E>();
			left.setParent(p);
			p.setLeft(left);
			stack.push(left);
			n++;

			Position<E> right = new Position<E>();
			right.setParent(p);
			p.setRight(right);
			stack.push(right);
			n++;
		}
	}

	// danger function
	public void expandleft(Position<E> p) {
		Position<E> left = new Position<E>();
		left.setParent(p);
		p.setLeft(left);
		stack.push(left);
		n++;
	}

	public void expandright(Position<E> p) {
		Position<E> right = new Position<E>();
		right.setParent(p);
		p.setRight(right);
		stack.push(right);
		n++;
	}

	public Position<E> getPosition(int n) {
		return stack.get(n);
	}

	public String getStackElement() {
		String output = "";
		for (int i = 0; i < stack.size() && !stack.get(i).equals(null); i++) {
			output += " ";
			output += stack.get(i).getElement();
		}
		return output;
	}

	// recursive version
	public String inOrderTraversal(Position<E> p) {
		String output = "";
		if (p.getLeft() != null) {
			output += inOrderTraversal(p.getLeft());
		}
		output += p.getElement();
		if (p.getRight() != null) {
			output += inOrderTraversal(p.getRight());
		}
		return output;
	}

	// non_recursive version
	public String inOrderTraversal() {
		String output = "";
		Position<E> node = root;
		Stack<Position<E>> stackPointer = new Stack<Position<E>>();
		while (node != null) {
			stackPointer.push(node);
			node = node.getLeft();
		}
		while (!stackPointer.isEmpty()) {
			node = stackPointer.pop();
			output += node.getElement();
			if (node.getRight() != null) {
				node = node.getRight();
				while (node != null) {
					stackPointer.push(node);
					node = node.getLeft();
				}
			}
		}

		return output;
	}

	public void showDetail() {
		for (int i = 0; i < stack.size(); i++) {
			System.out.println();
			Position<E> t = stack.get(i);
			System.out.print(t.getElement());
			if (t.getLeft() != null)
				System.out.print(" " + t.getLeft().getElement());
			if (t.getRight() != null) {
				System.out.print(" " + t.getRight().getElement());
			}
		}
		System.out.println();
	}
}
