import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

class Polynomial{
    double[] coefficients;
    int[] exponents;

    public Polynomial(){
        coefficients = new double[]{0};
        exponents = new int[]{0};
    }

    public Polynomial(double[] coefficients, int[] exponents){
        this.coefficients = new double[coefficients.length];
        this.exponents = new int[exponents.length];
        for (int i = 0; i < coefficients.length; i++){
            this.coefficients[i] = coefficients[i];
        }
        for (int i = 0; i < exponents.length; i++){
            this.exponents[i] = exponents[i];
        }
    }

    public Polynomial(File file){
        try{
            Scanner sc = new Scanner(file);
            String line = sc.nextLine();
            String[] terms = line.split("(?=\\+)|(?=-)");
            coefficients = new double[terms.length];
            exponents = new int[terms.length];
            for (int i = 0; i < terms.length; i++){
                String[] parts = terms[i].split("x");
                coefficients[i] = Double.parseDouble(parts[0]);
                if (parts.length > 1)
                    exponents[i] = Integer.parseInt(parts[1]);
            }
            sc.close();
        }
        catch (FileNotFoundException e){}
    }

    public Polynomial add(Polynomial arg){
        //Finding the length of temp array (we make an array holding each power up to the highest power)
        int tempLength = 0;
        for (int i = 0 ; i < arg.exponents.length; i++){
            tempLength = Math.max(tempLength, arg.exponents[i]);
        }
        for (int i = 0 ; i < exponents.length; i++){
            tempLength = Math.max(tempLength, exponents[i]);
        }
        tempLength++; //For exp = 0 (constant)
        double[] tempCoefficients = new double[tempLength];

        //Add up the coefficients in the temp array
        for (int i = 0; i < arg.coefficients.length; i++){
            tempCoefficients[arg.exponents[i]] += arg.coefficients[i];
        }
        for (int i = 0; i < coefficients.length; i++){
            tempCoefficients[exponents[i]] += coefficients[i];
        }

        //Count the number of non-redundant terms
        int resultLength = 0;
        for (int i = 0; i < tempLength; i++){
            if (tempCoefficients[i] != 0) resultLength++;
        }

        //Transfer over the temp data to a proper result
        double[] resultCoefficients = new double[resultLength];
        int[] resultExponents = new int[resultLength];
        int ind = 0;
        for (int i = 0; i < tempLength; i++){
            if (tempCoefficients[i] != 0){
                resultCoefficients[ind] = tempCoefficients[i];
                resultExponents[ind] = i;
                ind++;
            }
        }

        Polynomial result = new Polynomial(resultCoefficients, resultExponents);
        return result;
    }

    public double evaluate(double x){
        double result = 0;
        double currentTerm;
        for (int i = 0; i < coefficients.length; i++){
            currentTerm = coefficients[i];
            for (int j = 0; j < exponents[i]; j++){
                currentTerm = currentTerm*x;
            }
            result += currentTerm;
        } 
        return result;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial arg){
        //Finding the length of temp array (we make an array holding each power up to the highest power)
        int tempLength1 = 0;
        int tempLength2 = 0;
        for (int i = 0 ; i < arg.exponents.length; i++){
            tempLength1 = Math.max(tempLength1, arg.exponents[i]);
        }
        for (int i = 0 ; i < exponents.length; i++){
            tempLength2 = Math.max(tempLength2, exponents[i]);
        }
        double[] tempCoefficients = new double[tempLength1 + tempLength2 + 1];

        //Multiply all the terms
        for (int i = 0; i < arg.coefficients.length; i++){
            for (int j = 0; j < coefficients.length; j++){
                tempCoefficients[arg.exponents[i] + exponents[j]] += arg.coefficients[i] * coefficients[j];
            }
        }

        //Count the number of non-redundant terms
        int resultLength = 0;
        for (int i = 0; i < tempLength1 + tempLength2 + 1; i++){
            if (tempCoefficients[i] != 0) resultLength++;
        }

        //Transfer over the temp data to a proper result
        double[] resultCoefficients = new double[resultLength];
        int[] resultExponents = new int[resultLength];
        int ind = 0;
        for (int i = 0; i < tempLength1 + tempLength2 + 1; i++){
            if (tempCoefficients[i] != 0){
                resultCoefficients[ind] = tempCoefficients[i];
                resultExponents[ind] = i;
                ind++;
            }
        }
        Polynomial result = new Polynomial(resultCoefficients, resultExponents);
        return result;
    }

    public void saveToFile(String file){
        try{
            PrintStream ps = new PrintStream(file);
            for (int i = 0; i < coefficients.length; i++){
                if (coefficients[i] > 0)
                    ps.print("+");
                if(exponents[i] == 0)
                    ps.print(coefficients[i]);
                else
                    ps.print(coefficients[i] + "x" + exponents[i]);
            }
            ps.close();
        }
        catch (FileNotFoundException e){}
    }
}