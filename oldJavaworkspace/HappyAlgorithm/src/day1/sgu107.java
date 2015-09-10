package day1;

public class sgu107 {
	public static void main(String[] args) {
		int a = 0;
		for(long i = 1;i<=Math.pow(10, 6);i++){
			long b = i*i;
			if(b%1000000000 == 987654321){
				a++;
			}
			System.out.println(b);
		}
		System.out.println(a+"­Ó¼Æ");
	}
}
