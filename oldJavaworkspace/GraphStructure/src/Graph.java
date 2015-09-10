import java.util.LinkedList;

public class Graph {
	private int totalVertex = 0;
	LinkedList<Vertex> vertexlist = new LinkedList<Vertex>();
	Vertex headvertex;
	public Graph(int setTotalVertex) {
		setTotalVertex(setTotalVertex);
	}

	public void add(Vertex v1, Vertex v2, int cost) {
		
		int v1inlist = inlist(vertexlist, v1);
		if (v1inlist == -1) {// == -1 means not in list
			v1.add(cost, v2);
			vertexlist.add(v1);
		} else {
			vertexlist.get(v1inlist).add(cost, v2);
		}
		if(vertexlist.size() == 1){
			headvertex = vertexlist.getFirst();
		}
		int v2inlist = inlist(vertexlist, v2);
		if (v2inlist == -1) {
			v2.add(cost, v1);
			vertexlist.add(v2);
		} else {
			vertexlist.get(v2inlist).add(cost, v1);
		}
	}

	private int inlist(LinkedList<Vertex> list, Vertex v) {
		int output = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getElement() == v.getElement()) {
				output = i;
				break;
			}
		}
		return output;
	}

	public void printData() {
		for (int i = 0; i < vertexlist.size(); i++) {
			System.out.println(vertexlist.get(i).getElement());
			System.out.println(vertexlist.get(i).nextCost);
			System.out.println(vertexlist.get(i).printNextVerteice());
		}
	}

	private LinkedList<Vertex> checkList = new LinkedList<Vertex>();

	public void DFTraversal(Graph G, Vertex v) {
		if (inlist(G.checkList, v) == -1) {// unvisited
			System.out.print(v.getElement() + " ");
			G.checkList.add(v);
			for (int i = 0; i < v.nextVertexs.size(); i++) {
				DFTraversal(G, v.nextVertexs.get(i));
			}
		}
	}

	public int getTotalVertex() {
		return totalVertex;
	}

	public void setTotalVertex(int totalVertex) {
		this.totalVertex = totalVertex;
	}
	LinkedList<TreeNode> treeNodeList = new LinkedList<TreeNode>();
	public void miniumSpanningTree(Graph G) {
		headvertex
	}

}
