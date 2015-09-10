
public class TreeNode {
	private int cost;
	private char toChar;
	public TreeNode(int c ,char e){
		setCost(c);
		setToChar(e);
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public char getToChar() {
		return toChar;
	}
	public void setToChar(char toChar) {
		this.toChar = toChar;
	}
}
