import java.util.LinkedList;

public class Vertex {
	private char element;
	LinkedList<Integer> nextCost = new LinkedList<Integer>();
	LinkedList<Vertex> nextVertexs = new LinkedList<Vertex>();

	public Vertex(char e) {
		setElement(e);
	}

	public void add(int cost, Vertex v) {
		nextCost.add(cost);
		nextVertexs.add(v);
	}

	public char getElement() {
		return element;
	}

	public void setElement(char element) {
		this.element = element;
	}

	public String printNextVerteice() {
		String output = "[";
		for (int i = 0; i < nextVertexs.size(); i++) {
			output += nextVertexs.get(i).getElement();
			if (i != nextVertexs.size() - 1) {
				output += ", ";
			}
		}
		output += "]";
		return output;
	}
}
