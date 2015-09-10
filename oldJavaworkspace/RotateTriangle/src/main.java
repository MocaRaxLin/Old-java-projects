import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class main {
	static Scanner file;
	static float a[][] = new float[2][2];
	static float b[][] = new float[2][2];
	static float angleinRadian;
	static float rotateMatrix[][];
	static FileWriter outputFile;
	public static void main(String args[]) throws IOException {
		readtheFile("src/input.txt");
		rotateMatrix = rotateMatrixinR2(angleinRadian);
		b = multiflyMatrix(rotateMatrix, a);
		showMatrix(b);
		rounding(b);
		showMatrix(b);
		outputFile(b ,"src/output.txt");
	}

	private static void outputFile(float[][] matrix, String path) throws IOException {
		outputFile = new FileWriter(path);
		for (int i = 0; i < matrix[0].length; i++) {
			outputFile.write("(");
			for (int j = 0; j < matrix.length; j++) {
				outputFile.write(matrix[j][i]+"");
				if(j!=matrix.length-1){
					outputFile.write(" , ");
				}
			}
			outputFile.write(")\n");
		}
		outputFile.close();
	}

	private static void rounding(float[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if(matrix[i][j]<0){
					matrix[i][j] = (float)((int)(100*matrix[i][j]-0.5))/100;
				}else{
					matrix[i][j] = (float)((int)(100*matrix[i][j]+0.5))/100;
				}
			}
		}
	}

	private static void showMatrix(float[][] matrix) {
		System.out.println();
		for (int i = 0; i < matrix.length; i++) {
			System.out.println();
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
		}
	}

	private static float[][] multiflyMatrix(float[][] a, float[][] b) {
		float[][] output = new float[a.length][b[0].length];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				float t = 0;
				for (int k = 0; k < b.length; k++) {
					t += a[i][k] * b[k][j];
				}
				output[i][j] = t;
			}
		}
		return output;
	}

	private static float[][] rotateMatrixinR2(float radian) {
		float output[][] = {
				{ (float) Math.cos(radian), (float) -Math.sin(radian) },
				{ (float) Math.sin(radian), (float) Math.cos(radian) } };
		return output;
	}

	private static void readtheFile(String path) throws FileNotFoundException {
		file = new Scanner(new File(path));
		for (int i = 0; i < a[0].length; i++) {
			for (int j = 0; j < a.length; j++) {
				a[j][i] = file.nextFloat();
			}
		}
		angleinRadian = degreetoPi(file.nextFloat());
		System.out.println("(" + a[0][0] + "," + a[1][0] + ")");
		System.out.println("(" + a[0][1] + "," + a[1][1] + ")");
		System.out.println(angleinRadian);

	}

	private static float degreetoPi(float degree) {
		return (float) (degree * Math.PI / 180);
	}
}
