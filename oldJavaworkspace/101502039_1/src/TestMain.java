import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TestMain {
	static Scanner file;
	static int m,n;
	static float matrix[][];
	public static void main(String[] args) throws FileNotFoundException {
		//open and read the file
		openandReadtheFile();
		//new a solver
		MatrixOnlySolver solver = new MatrixOnlySolver(matrix);
		solver.showMatrix();
		
		solver.reduceEchelonForm();
		//show in console
		solver.showMatrix();
	}

	private static void openandReadtheFile() throws FileNotFoundException {
		file = new Scanner(new File("input.txt"));
		// read m(row) n(column)
		m = file.nextInt();
		n = file.nextInt();

		// make an array[m][n]
		matrix = new float[m][n];

		// import number into matrix
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = file.nextFloat();
			}
		}
		//close the input file
		file.close();
	}

}
