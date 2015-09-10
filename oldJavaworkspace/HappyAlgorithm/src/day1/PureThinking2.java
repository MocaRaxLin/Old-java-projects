package day1;

public class PureThinking2 {
	public static void main(String[] args) {
		int a = 2;//variable
		int b = 6;//variable
		int n = 11;//variable
		
		Cup[] cups = new Cup[n];
		for(int i = 0;i<n;i++){
			cups[i] = new Cup(a, b);
		}
		
		People he = new People();
		People she = new People();
		
		for(int i = 0;;i++){
			
			int useless = 0;
			for(int j = 0;j<n;j++){
				if(cups[j].A == 0|| cups[j].A == cups[j].B){
					useless++;
				}
			}
			if(useless == n-1){
				if(i%2 == 0){
					System.out.println("he loss");
				}else{
					System.out.println("she loss");
				}
				break;
			}
			
			if(i%2 == 0){
				Cup x = he.pickCupX(cups);
				Cup y = he.pickCupY(cups);
				he.cupXToCupY(x, y);
			}else{
				Cup x = she.pickCupX(cups);
				Cup y = she.pickCupY(cups);
				she.cupXToCupY(x, y);
			}
		}
		
	}
}
class People {
	public Cup pickCupX(Cup[] cups){
		int minA = 1000;
		Cup outputCup = null;
		for(int i = 0;i<cups.length;i++){
			if(minA>cups[i].A&&cups[i].A!=0){
				minA = cups[i].A;
			}
		}
		for(int i = 0;i<cups.length;i++){
			if(cups[i].A == minA){
				outputCup = cups[i];
				break;
			}
		}
		return outputCup;
	}
	public Cup pickCupY(Cup[] cups){
		int maxSpace = 0;
		Cup outputCup = null;
		for(int i = 0;i<cups.length;i++){
			if(maxSpace<cups[i].B-cups[i].A){
				maxSpace = cups[i].B-cups[i].A;
			}
		}
		for(int i = 0;i<cups.length;i++){
			if((cups[i].B-cups[i].A) == maxSpace&&cups[i].A!=cups[i].B){
				outputCup = cups[i];
				break;
			}
		}
		return outputCup;
	}
	public void cupXToCupY(Cup X , Cup Y){
		if(X.A <= Y.B - Y.A){
			X.A = 0;//cup is empty
			Y.A+=X.A;
		}else{
			X.A-=(Y.B - Y.A);
			Y.A = Y.B;//cup is full
		}
	}
}
class Cup{
	int A,B;//current volume , maximum volume
	public Cup(int setA, int setB){
		A = setA;
		B = setB;
	}
}