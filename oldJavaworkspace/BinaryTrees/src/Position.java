
public class Position<E> {
	private E element;
	private Position<E> parent;
	private Position<E> left;
	private Position<E> right;
	public Position() {
		parent = null;
		left = null;
		right = null;
	}
	
	public void setElement(E setElment){
		element = setElment;
	}
	public E getElement(){
		return element;
	}
	
	public void setParent(Position<E> p){
		parent = p;
	}
	public Position<E> getParent(){
		return parent;
	}
	
	public void setLeft(Position<E> p){
		left = p;
	}
	public Position<E> getLeft(){
		return left;
	}
	
	public void setRight(Position<E> p){
		right = p;
	}
	public Position<E> getRight(){
		return right;
	}
	
	public boolean isRoot(){
		return parent == null;
	}
	public boolean isExternal(){
		return left == null&&right == null;
	}
}
