import java.util.*;
import java.util.LinkedHashSet;
public class LinearRegressionModel {
    public Matrix inputs;
    public Matrix outputs;
    public Matrix weights;
    public Matrix testInputs;
    public Matrix testOutputs;
    public Matrix trainInputs;
    public Matrix trainOutputs;
    public LinearRegressionModel(Matrix x, Matrix y){
        this.outputs = y;
        this.inputs = x.addOnes();
//        this.inputs.printMatrix();
    }
    public Matrix fit(int numIterations, double alpha, boolean doPrintLoss){
        Matrix hyp;
        Matrix err;
        Matrix temp;
        Matrix temp2;
        Matrix temp3;
        Matrix weights = initializeWeights();
        for(int i = 0; i < numIterations; i++){
            hyp = trainInputs.multiplyMatrix(weights);
            err = hyp.subtractMatrix(trainOutputs);
            temp = trainInputs.transpose();
            temp3 = temp.multiplyMatrix(err);
            temp2 = temp3.scalarDivision(this.trainInputs.getHeight());
            temp2 = temp2.scalarMultiplication((double)(alpha));
            weights = weights.subtractMatrix(temp2);
            if(i%100 == 0 && doPrintLoss){
                System.out.println("Loss at " + i + "th iteration: " + cost(weights));
            }
        }
        this.weights = weights;
        return weights;
    }

    //implement a cost function
//    public double cost(Matrix err){
//        double sumSquared = 0;
//        for(int row = 0; row < err.getHeight(); row++){
//            sumSquared += Math.pow(err.getElement(row, 0), 2);
//        }
//        return (double)(sumSquared/err.getHeight());
//    }
    public double cost(Matrix weights){
        Matrix hyp = this.testInputs.multiplyMatrix(weights);
        Matrix err = hyp.subtractMatrix(this.testOutputs);

        double sumSquared = 0;
        for(int row = 0; row < err.getHeight(); row++){
            sumSquared += Math.pow(err.getElement(row, 0), 2);
        }
        return (double)(sumSquared/err.getHeight());
    }
    public String getEquation(){
        String ans = "";
        for(int row = 0; row < weights.getHeight(); row++){
            Double d = weights.getElement(row, 0);
            if(row == 0){
                ans += d.toString() + " + ";
            }else if(row != weights.getHeight() - 1){
                ans += d.toString() + "x_" + row + " + ";
            }else{
                ans += d.toString() + "x_" + row;
            }
        }
        return ans;
    }
    public double predict(Matrix val){
        val = val.addOnes();
        Matrix ans = val.multiplyMatrix(weights);
        return ans.getElement(0, 0);
    }
    //create an initialize weight method for more than just 2 features
    public Matrix initializeWeights(){
        int numWeights = inputs.getWidth();
        double[][] ans = new double[numWeights][1];
        for(int row = 0; row < numWeights; row++){
            ans[row][0] = 0;
        }
        Matrix a = new Matrix(ans);
        return a;
    }
    //method which randomly selects values for training and test dataset
    //testRatio is
    public void makeTestSet(double testRatio){
        //creating the randomly chosen rows (no duplicates using hashset)
        Set<Integer> set = new LinkedHashSet<Integer>();

        int amountTest = (int)(testRatio * this.inputs.getHeight());
        Random r = new Random();
        while(set.size() < amountTest){
            set.add(r.nextInt(this.inputs.getHeight()));
        }
        Integer[] dataPointsTest = set.toArray(new Integer[set.size()]);
       // System.out.println("-------Ranodm integer values------");

        Collections.sort(Arrays.asList(dataPointsTest));
//        for(int i = 0; i < dataPointsTest.length; i++){
//            System.out.println(dataPointsTest[i]);
//        }
        double[][] test = new double[1][this.inputs.getWidth()];
        this.testInputs = new Matrix(test);
        this.trainInputs = this.inputs;
        for(int row = 0; row < dataPointsTest.length; row++){
            testInputs.addRow(this.inputs.getRow(dataPointsTest[row] - row));
            trainInputs.removeRow(dataPointsTest[row] - row);
        }
        //printing out indexes

        double[][] t = new double[1][1];
        this.testOutputs = new Matrix(t);
        this.trainOutputs = this.outputs;
        for(int row = 0; row < dataPointsTest.length; row++){
            testOutputs.addRow(this.outputs.getRow(dataPointsTest[row] - row));
            trainOutputs.removeRow(dataPointsTest[row] - row);
        }
        this.testInputs.removeRow(0);
        this.testOutputs.removeRow(0);
//        System.out.println("-----original train matrix-----");
//        inputs.printMatrix();
//        System.out.println("-----new train matrix-----");
//        trainInputs.printMatrix();
//        System.out.println("-----new test matrix-----");
//        testInputs.printMatrix();
//        System.out.println("-----new output train matrix-----");
//        trainOutputs.printMatrix();
//        System.out.println("-----new output test matrix-----");
//        testOutputs.printMatrix();
        //testInputs.printMatrix();
    }

}
