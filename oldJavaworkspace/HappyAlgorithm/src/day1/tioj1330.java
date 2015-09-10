package day1;

public class tioj1330 {
	public static void main (String args[]){
		int n = 4;//variable
		//ps.though we use long to replace int ,it still overflow.So the max variable should be 3
		long t = firstExponent(n);//t = n^n
		
		System.out.println(t);
		for(int i = 0;i<n-2;i++){
			t = exponent(t, n);//t = t^n
			//System.out.println(t);
		}
		
		long answer = t;
		System.out.println(answer);
	}
	public static long firstExponent(int x){
		long y = 1;
		for(int i = 0;i<x;i++){
			y *=x;
		}
		return y;
	}
	public static long exponent(long t ,int x){
		long y = 1;
		for(int i = 0;i<x;i++){
			y *=t;
			//System.out.println(y);
		}
		return y;
	}
}
