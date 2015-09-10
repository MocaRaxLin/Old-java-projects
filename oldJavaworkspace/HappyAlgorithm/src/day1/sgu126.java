package day1;
import java.util.Stack;


public class sgu126 {
	public static void main(String[] args) {
		int a=5;
		int b=2;
		int count = 0;
		boolean findLoop = false;
		Stack<Integer> maxStack = new Stack<Integer>();
		Stack<Integer> minStack = new Stack<Integer>();
		while(a!=0&&b!=0){
		if(a<b){
			int t = a;
			a = b;
			b = t;
		}
		maxStack.push(a);
		minStack.push(b);
		for(int i = 0;i<maxStack.size()-1;i++){
			if(a == maxStack.get(i) && b == minStack.get(i)){
				findLoop = true;
				break;
			}
		}
		if(findLoop){
			System.out.println(a+", "+b);
			System.out.println(-1);
			break;
		}
		System.out.println(a+", "+b);
		a = a-b;
		b = b+b;
		count++;
	}
		if(!findLoop)
		System.out.println(count);
	}
}
