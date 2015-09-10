
public class MatrixOnlySolver {
	private float[][] matrix;
	private int m;//row
	private int n;//column
	public MatrixOnlySolver(float[][] setMatrix){
		matrix = setMatrix;
		m = matrix.length;
		n = matrix[0].length;
	}
	public void reduceEchelonForm(){
		echelonForm();
		for(int i = m-1;i>=0;i--){
			for(int j = i-1;j>=0;j--){
				float t = -matrix[j][i];
				rowAmultiplyTAddtoRowB(i, t, j);
			}
		}
	}
	public void echelonForm(){
		for(int i = 0;i<m;i++){
			//find the row start with nonzero
			for(int j = i;j<m;j++){
				if(matrix[j][i] != 0){
				changeRowAwithRowB(i, j);
				break;
				}
			}
			//divide the first to one
			if(matrix[i][i]!=0){
				rowAmultiplyT(i, 1/matrix[i][i]);
			}
			
			//do row operation with row i
			for(int j = i+1;j<m;j++){
				rowAmultiplyTAddtoRowB(i, -matrix[j][i], j);
			}
			
		}
	}
	public void rowAmultiplyTAddtoRowB(int a,float t,int b){
		float[][] factor = unitFactor();
		factor[b][a] = t;
		matrix = matrixAMultiplyMatrixB(factor, matrix);
	}
	public void rowAmultiplyT(int a,float t){
		float[][] factor = unitFactor();
		factor[a][a] = t;
		matrix = matrixAMultiplyMatrixB(factor, matrix);
	}
	public void changeRowAwithRowB(int a ,int b){
		float[][] factor = unitFactor();
		factor[a][a] = 0;
		factor[b][b] = 0;
		factor[a][b] = 1;
		factor[b][a] = 1;
		matrix = matrixAMultiplyMatrixB(factor, matrix);
	}
	private float[][] unitFactor(){
		float[][] factor = new float[m][m];
		for(int i = 0;i<m;i++){
			factor[i][i] = 1;
		}
		return factor;
	}
	private float[][] matrixAMultiplyMatrixB(float[][] a , float[][] b){
		float[][] output = new float[a.length][b[0].length];
		for(int i = 0;i<b.length;i++){
			for(int j = 0;j<b[0].length;j++){
				float t = 0;
				for(int k = 0;k<b.length;k++){
					t+=a[i][k]*b[k][j];
				}
				output[i][j] = t;
			}
		}
		return output;
	}
	public void showMatrix(){
		System.out.println();
		for (int i = 0; i < m; i++) {
			System.out.println();
			for (int j = 0; j < n; j++) {
				System.out.print(matrix[i][j] + " ");
			}
		}
	}
}
