import java.util.Scanner;
import java.util.Stack;


public class main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Stack<Character> symbol = new Stack<Character>();
		Stack<Integer> frequency = new Stack<Integer>();
		
		System.out.println("Please input symbol and frequency\n:");
		symbol.push(input.next().charAt(0));
		frequency.push(input.nextInt());
		
		while(true){
			System.out.println("Please input symbol and frequency(input -1 to input code)\n:");
			String s = input.next();
			if(s.equals("-1")){
				break;
			}else{
				symbol.push(s.charAt(0));
				frequency.push(input.nextInt());
				
			}
		}
		char symbolArray[] = new char[symbol.size()];
		int frequencyArray[] = new int[frequency.size()];
		for(int i = 0;i<symbol.size();i++){
			symbolArray[i] = symbol.get(i);
			frequencyArray[i] = frequency.get(i);
		}
		
		HuffmanTree tree = new HuffmanTree(symbolArray, frequencyArray);
		tree.showVaildData(tree.root);
		
		System.out.println("Please input encode message:");
		String message = input.next();
		char charMessage[] = message.toCharArray();
		String code = "";
		for(int i = 0;i<charMessage.length;i++){
			for(int j = 0;j<tree.vaildNodeStack.size();j++){
				if(charMessage[i] == tree.vaildNodeStack.get(j).getElement()){
					code+=tree.vaildNodeStack.get(j).getCode();
					break;
				}
			}
		}
		System.out.println(code);
		input.close();
	}
}
