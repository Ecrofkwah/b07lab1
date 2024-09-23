class Polynomial{
    double[] coefficients;

    public Polynomial(){
        coefficients = new double[]{0};
    }

    public Polynomial(double[] array){
        coefficients = new double[array.length];
        for (int i = 0; i < array.length; i++){
            coefficients[i] = array[i];
        }
    }

    public Polynomial add(Polynomial arg){
        double[] resultArray = new double[Math.max(coefficients.length, arg.coefficients.length)];
        int ind = 0;
        for (; ind < coefficients.length && ind < arg.coefficients.length; ind++){
            resultArray[ind] = coefficients[ind] + arg.coefficients[ind];
        }
        for (; ind < coefficients.length; ind++){
            resultArray[ind] = coefficients[ind];
        }
        for (; ind < arg.coefficients.length; ind++){
            resultArray[ind] = arg.coefficients[ind];
        }
        Polynomial result = new Polynomial(resultArray);
        return result;
    }

    public double evaluate(double x){
        double result = 0;
        double currentTerm;
        for (int i = 0; i < coefficients.length; i++){
            currentTerm = coefficients[i];
            for (int j = 0; j < i; j++){
                currentTerm = currentTerm*x;
            }
            result += currentTerm;
        }
        return result;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }
}