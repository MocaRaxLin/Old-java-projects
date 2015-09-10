import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		/*Operation operation = new Operation();
		Scanner input = new Scanner(System.in);

		System.out.println("¿é¤J¹Bºâ¦¡");
		String infix = input.next();
		String postfix = operation.postfix(infix);
		System.out.println(postfix);
		float answer = operation.computePostfix();
		System.out.println(answer);*/
		ArrayStack<Integer> stack = new ArrayStack<Integer>();
		for(int i = 0;i<10;i++){
			stack.push(i);
			stack.showStack();
		}
		for(int i = 0;i<11;i++){
			int x = stack.pop();
			System.out.println(x);
		}
	}
}
