import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {0,0,6,5};
		int[] e1 = {2,1,0,3};
		Polynomial p1 = new Polynomial(c1, e1);
		double [] c2 = {0,-2,0,0,-9};
		int[] e2 = {0,1,2,3,4};
		Polynomial p2 = new Polynomial(c2, e2);
		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		Polynomial m = p1.multiply(p2);
		System.out.print("m = ");
		for (int i = 0; i < m.coefficients.length; i++){
			System.out.print(m.coefficients[i]);
			System.out.print("x^" + m.exponents[i]);
		}
		System.out.println();
		
		System.out.println("m(-0.36827) = " + m.evaluate(-0.36827));
		System.out.println("m(-0.60571) = " + m.evaluate(-0.60571));
		System.out.println("m(-1.06266) = " + m.evaluate(-1.06266));

		double[] c3 = {5, -3, 7};
		int[] e3 = {0, 2, 8};
		Polynomial p3 = new Polynomial(c3, e3);
		p3.saveToFile("testFile.txt");
		File f = new File("testFile.txt");
		Polynomial fp = new Polynomial(f);
		System.out.println("fp(0.1) = " + fp.evaluate(0.1));
	}
}