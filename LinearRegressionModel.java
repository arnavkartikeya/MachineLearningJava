public class LinearRegressionModel {
    public Matrix inputs;
    public Matrix outputs;
    public LinearRegressionModel(Matrix x, Matrix y){
        this.outputs = y;
        this.inputs = x.addOnes();
//        this.inputs.printMatrix();
    }
    public Matrix gradientDescent(int numIterations,double alpha){
        double[][] weight = {{0},{0}};
        Matrix hyp;
        Matrix err;
        Matrix temp;
        Matrix temp2;
        Matrix temp3;
        Matrix weights = new Matrix(weight);
        for(int i = 0; i < numIterations; i++){
            hyp = inputs.multiplyMatrix(weights);
            err = hyp.subtractMatrix(outputs);
            temp = inputs.transpose();
            temp3 = temp.multiplyMatrix(err);
            temp2 = temp3.scalarDivision(inputs.getHeight());
            temp2 = temp2.scalarMultiplication((double)(alpha));
            weights = weights.subtractMatrix(temp2);
        }
        return weights;
    }

}
