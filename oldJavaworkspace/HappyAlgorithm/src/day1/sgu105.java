package day1;

import java.util.Stack;

public class sgu105 {
	static int n = 10;
	static Stack<Integer> stack;
	static boolean allBreak = false;
	public static void main(String[] args) {
		stack = new Stack<Integer>();
		int t = 0;
		System.out.println(t/10+", "+t%10);
		for (int i = 1;; i++) {//²Äi¶µ
			for (int j = 1; j <= i; j++) {
				int rest = j;
				
				while ((rest / 10 != 0 || rest % 10 != 0)) {
					stack.push(getMaxDigit(rest));
					n--;
					if(n==0){
						System.out.println(stack);
						allBreak = true;
						break;
					}
					rest = getRestDigits(rest);
					
				}
				if(j%10 == 0){
					stack.push(getMaxDigit(0));
					n--;
				}
				if(allBreak){
					break;
				}
			}
			if(allBreak){
				break;
			}
		}
		
		//let items in stack mod 3
		int count = 0;
		for (int i = 0; i < stack.size(); i++) {
			if(stack.get(i)%3 == 0){
				count++;
			}
		}
		System.out.println(count+"­Ó");
	}

	private static int getMaxDigit(int i) {
		int a = 1;
		while (true) {
			a *= 10;
			if (a > i) {
				break;
			}
		}
		int output = i / (a / 10);
		return output;
	}

	private static int getRestDigits(int i) {
		int a = 1;
		while (true) {
			a *= 10;
			if (a > i) {
				break;
			}
		}
		int output = i % (a / 10);
		return output;
	}
}