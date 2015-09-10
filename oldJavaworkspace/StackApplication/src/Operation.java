import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Operation {
	String infix;
	char[] infixCharArray;
	Stack<Character> operatorStack = new Stack<Character>();
	Queue<Object> postfixQueue = new LinkedList<Object>();
	String postfix;
	Stack<String> countStack = new Stack<String>();

	public float computePostfix() {
		float output = 0;
		System.out.println(postfixQueue);
		while (!postfixQueue.isEmpty()) {
			String z = String.valueOf(postfixQueue.poll());
			if (z.equals("+") ||z.equals("-") || z.equals("*") || z.equals("/")) {
				float a, b;
				b = Float.parseFloat(countStack.pop());
				if (countStack.isEmpty()) {
					a = 0;
				} else {
					a = Float.parseFloat(countStack.pop());
				}
				float result = 0;
				if(z.equals("+")){
					result = a+b;
				}else if(z.equals("-")){
					result = a-b;
				}else if(z.equals("*")){
					result = a*b;
				}else if(z.equals("/")){
					result = a/b;
				}
				countStack.push(Float.toString(result));
			} else {
				countStack.push(z);
			}
		}
		output = Float.valueOf(countStack.pop());
		return output;
	}

	public String postfix(String setInfix) {
		String output = "";

		infix = setInfix;
		infixCharArray = infix.toCharArray();
		for (int i = 0; i < infixCharArray.length; i++) {
			char x = infixCharArray[i];
			if(x == '(' || x == ')' ){
				operatorStack.push(x);
				if(x == ')'){
					operatorStack.pop();
					while(operatorStack.lastElement()!='('){
						char w = operatorStack.pop();
						postfixQueue.offer(w);
						output+=w;
					}
					operatorStack.pop();
				}
			}else if (x == '+' || x == '-' || x == '*' || x == '/') {
				while (true) {
					if (havePriorityinStack(x)) {
						operatorStack.push(x);
						break;
					} else {
						char y = operatorStack.pop();
						output += y;
						postfixQueue.offer(y);
					}
				}
			} else {
				String number = "";
				number += x;
				while (i < infixCharArray.length - 1
						&& infixCharArray[i + 1] != '+'
						&& infixCharArray[i + 1] != '-'
						&& infixCharArray[i + 1] != '*'
						&& infixCharArray[i + 1] != '/'
						&& infixCharArray[i + 1] != '('
						&& infixCharArray[i + 1] != ')') {
					number += infixCharArray[++i];
				}
				output += number;
				postfixQueue.offer(number);
			}
		}
		while (!operatorStack.isEmpty()) {
			char y = operatorStack.pop();
			output += y;
			postfixQueue.offer(y);
		}

		return output;
	}

	private boolean havePriorityinStack(char operator) {
		boolean output = true;
		if (!operatorStack.isEmpty()) {
			if (operator == '*' || operator == '/') {
				if (operatorStack.lastElement() == '*'
						|| operatorStack.lastElement() == '/') {
					output = false;
				}
			} else {
				if (operatorStack.lastElement() == '+'
						|| operatorStack.lastElement() == '-'
						|| operatorStack.lastElement() == '*'
						|| operatorStack.lastElement() == '/') {
					output = false;
				}
			}
		}
		return output;
	}
}
