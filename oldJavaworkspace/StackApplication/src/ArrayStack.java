
public class ArrayStack<E> {
	private final int defaultCapacity = 50;
	private E array[];
	private int size;
	public ArrayStack(){
		array = (E[]) new Object[defaultCapacity];
		size = 0;
	}
	public void push(E element){
		if(size == defaultCapacity-1){
			System.out.print("Stack overflow");
		}else{
			array[size] = element;
		size++;
		}
		
	}
	public E pop(){
		if(size == 0){
			System.out.println("Stack is empty");
			return null;
		}else{
			E t = array[size-1];
			size--;
			return t;
			
		}
	}
	public void showStack(){
		System.out.println();
		for(int i = 0;i<size;i++){
			System.out.print(array[i]+" ");
		}
	}
}
