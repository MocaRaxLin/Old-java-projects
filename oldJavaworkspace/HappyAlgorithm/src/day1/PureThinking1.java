package day1;

public class PureThinking1 {
	public static void main(String[] args) {
		int m = 2;//variable
		
		
		long b = xToThePowerXUpToYTimes(9,m);
		System.out.println(b);
		
		for(int i = 1;;i++){
			long a = xToThePowerXUpToYTimes(3,i);
			if(a-b>0){
				System.out.println(a);
				System.out.println("n="+i);
				break;
			}
		}
	}

	private static long xToThePowerXUpToYTimes(int x, int y) {
		long output = x;
		for(int i = 0;i<y-1;i++){
			output = xToThePowerY(output,x);
		}
		return output;
	}

	private static long xToThePowerY(long x, long y) {
		int output = 1;
		for(int i = 0;i<y;i++){
			output*=x;
		}
		return output;
	}
}
