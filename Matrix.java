import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
public class Matrix {
    private double[][] matrix;
    int height;
    int width;

    public Matrix(double[][] arr) {
        matrix = arr;
        height = arr.length;
        width = arr[0].length;
    }

    public Matrix(int row, int column) {
        //make a 2 dimensional array with row and column
        matrix = new double[row][column];
        height = row;
        width = column;
    }

    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    //adding elements to matrix (using coordinates)
    public void addElement(double element, int i, int j) {
        matrix[i][j] = element;
    }

    public double getElement(int i, int j) {
        return matrix[i][j];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    //a method which takes a matrix and adds to to the current matrix
    public Matrix addMatrix(Matrix b) {
        if (b.getWidth() != this.getWidth() || b.getHeight() != this.getHeight()) {
            System.out.println("Matrices are not the same dimensions");
            return null;
        }
        double[][] arr = new double[this.getHeight()][this.getWidth()];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = this.getElement(i, j) + b.getElement(i, j);
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    public Matrix subtractMatrix(Matrix b) {
        if (b.getWidth() != this.getWidth() || b.getHeight() != this.getHeight()) {
            System.out.println("Matrices are not the same dimensions");
            return null;
        }
        double[][] arr = new double[this.getHeight()][this.getWidth()];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = this.getElement(i, j) - b.getElement(i, j);
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    public Matrix scalarMultiplication(double scalar) {
        double[][] arr = new double[this.getHeight()][this.getWidth()];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = (double)(this.getElement(i, j) * scalar);
            }
        }


        Matrix ans = new Matrix(arr);
        return ans;
    }

    public Matrix scalarDivision(double scalar) {
        double[][] arr = new double[this.getHeight()][this.getWidth()];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = (double) (this.getElement(i, j) / scalar);
            }
        }


        Matrix ans = new Matrix(arr);
        return ans;
    }

    //
    public Matrix multiplyMatrix(Matrix b) {
        double[][] arr = new double[this.getHeight()][b.getWidth()];
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = 0; col < b.getWidth(); col++) {
                //multiply rowth row with colth column
                arr[row][col] = multiplyCells(this, b, row, col);
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    public double multiplyCells(Matrix a, Matrix b, int row, int col) {
        double val = 0;
        for (int i = 0; i < a.getWidth(); i++) {
            val += a.getElement(row, i) * b.getElement(i, col);
        }
        return val;
    }

    public Matrix transpose() {
        double[][] arr = new double[this.getWidth()][this.getHeight()];
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = 0; col < this.getWidth(); col++) {
                arr[col][row] = this.getElement(row, col);
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    //add a column of ones before the matrix
    public Matrix addOnes() {
        Matrix ans;
        double[][] arr = new double[this.getHeight()][this.getWidth() + 1];
        for (int row = 0; row < this.getHeight(); row++) {
            arr[row][0] = 1;
            for (int col = 1; col < this.getWidth() + 1; col++) {
                arr[row][col] = this.getElement(row, col - 1);
            }
        }
        ans = new Matrix(arr);
        return ans;
    }

    //method which takes a .txt file and makes it into a matrix
    public static Matrix readTxt(File f, int height, int width) throws FileNotFoundException {
        Scanner sc = new Scanner(f);
        double[][] arr = new double[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                arr[row][col] = sc.nextDouble();
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    //method which returns a new matrix from the index of start and end columns
    public Matrix splitCol(int start, int end) {
        double[][] arr = new double[this.getHeight()][end - start + 1];
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = start; col <= end; col++) {
                arr[row][col] = this.getElement(row, col);
            }
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    public Matrix getCol(int colIndex) {
        double[][] arr = new double[this.getHeight()][1];
        for (int row = 0; row < this.getHeight(); row++) {
            arr[row][0] = this.getElement(row, colIndex);
        }
        Matrix ans = new Matrix(arr);
        return ans;
    }

    //Things to do:
    /*
    Make a matrix class using 2d arrays
    Have methods for making a new matrix, smth like add(element, row or column), or something like add(2d array)
     */
    @Override
    public String toString() {
        String s = "";
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = 0; col < this.getWidth(); col++) {
                Double d = this.getElement(row, col);
                String temp = d.toString();
                s += temp;
            }
            s += "\n";
        }
        return s;
    }

    public double[] getRow(int rowIndex){
        double[] ans = new double[this.getWidth()];
        for(int col = 0; col < this.getWidth(); col++){
            ans[col] = this.getElement(rowIndex, col);
        }
        return ans;
    }
    //adds row to the bottom of the matrix
    public void addRow(double[] rowVals){
        double[][] vals = new double[this.getHeight() + 1][this.getWidth()];
        for(int row = 0; row < vals.length; row++){
            for(int col = 0; col < this.getWidth(); col++){
                if(vals.length - 1 != row) {
                    vals[row][col] = this.getElement(row, col);
                }else{
                    vals[row][col] = rowVals[col];
                }
            }
        }
        this.matrix = vals;
        this.height = vals.length;
        this.width = vals[0].length;
    }
    public static Matrix addRow(double[] rowVals, Matrix b){
        Matrix ans;
        double[][] vals = new double[b.getHeight() + 1][b.getWidth()];
        for(int row = 0; row < vals.length; row++){
            for(int col = 0; col < b.getWidth(); col++){
                if(vals.length - 1 != row) {
                    vals[row][col] = b.getElement(row, col);
                }else{
                    vals[row][col] = rowVals[col];
                }
            }
        }
        ans = new Matrix(vals);
        return b;
    }
    public void removeRow(int rowIndex){
        int rowCount = 0;
        double[][] ans =  new double[this.getHeight() - 1][this.getWidth()];
        for(int row = 0; row < this.getHeight(); row++){
            if(row == rowIndex){
                continue;
            }else{
                for(int col = 0; col < this.getWidth(); col++){
                            ans[rowCount][col] = this.getElement(row, col);
                }
                rowCount++;
            }

        }
        this.matrix = ans;
        this.height = ans.length;
        this.width = ans[0].length;
    }

}

class Test{
    public static void main(String[] args) throws FileNotFoundException {
        double[][] first = {{1 , 2}, {3, 4}};
        double[][] second = {{5,6}, {7,8}};
//
//        Matrix data = Matrix.readTxt(new File("src/test.txt"), 199, 2);
//        data.printMatrix();
//        Matrix x = data.getCol(0);
//        Matrix y = data.getCol(1);
//
//        LinearRegressionModel l = new LinearRegressionModel(x, y);
//        l.makeTestSet(0.2);
//        Matrix weights = l.fit(5000, 0.01, true);
//        System.out.println("Final equation: "+ l.getEquation());
        Matrix f = new Matrix(first);
        Matrix s = new Matrix(second);
        System.out.println("----Original Matrix-----");
        f.printMatrix();
        Matrix one = f.transpose();
        System.out.println("----After transposing-----");
        one.printMatrix();
        Matrix two = f.multiplyMatrix(s);
        System.out.println("-----After multiplying");
        two.printMatrix();

//
    }
}
