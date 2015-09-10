import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class main {
	static Scanner file;
	static BinaryTree<Character> tree;
	static int pointer;
	static int putcounter;
	public static void main(String[] args) throws FileNotFoundException {
		file = new Scanner(new File("input.txt"));
		tree = new BinaryTree<Character>();
		
		//put root
		if(file.next().charAt(0) == '-'){
			tree.addRoot(file.next().charAt(0));
		}
		pointer = 0;//current position pointer
		putcounter = 0;
		putElement();
		System.out.println(tree.getStackElement());
		System.out.println("recursive version: "+tree.inOrderTraversal(tree.root()));
		//tree.showDetail();
		System.out.println("non-recursive version: "+tree.inOrderTraversal());
		
		String array[] = tree.inOrderTraversal(tree.root()).split("-");
		System.out.print("in-order traversal: ");
		for(int i = 0;i<array.length;i++){
			System.out.print(array[i]);
		}
		System.out.println();
	}

	private static void putElement() {
		if(file.hasNext()){
			if(pointer%2 == 0){
				tree.expandleft(tree.getPosition(putcounter));
				pointer++;
				tree.getPosition(pointer).setElement(file.next().charAt(0));
			}else{
				tree.expandright(tree.getPosition(putcounter));
				pointer++;
				tree.getPosition(pointer).setElement(file.next().charAt(0));
				putcounter++;
			}
			
		putElement();
		}
	}
	
	
}
