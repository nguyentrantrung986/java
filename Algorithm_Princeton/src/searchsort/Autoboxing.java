package searchsort;

/**
 * Java autoboxing and equals(). Consider two double values a and b and their
 * corresponding <tt>Double</tt> values x and y. Find values such that (a==b) is
 * true but x.equals(y) is false. Find values such that (a==b) is false but
 * x.equals(y) is true.
 **/
public class Autoboxing {
	public static void main(String[]args){
		double a = 33;
		double b = 33;
		Double boxA = new Double(a);
		Double boxB = new Double(b);
		System.out.println("a = b = 33: "+(a==b));
		System.out.println("boxA.equals(boxB) (33): "+(boxA.equals(boxB)));
		
		a = 0.0;
		b = -0.0;
		boxA = new Double(a);
		boxB = new Double(b);
		System.out.println("a = b: "+(a==b));
		System.out.println("boxA.equals(boxB): "+(boxA.equals(boxB)));
		
		a = Double.NaN;
		b = Double.NaN;
		boxA = new Double(a);
		boxB = new Double(b);
		System.out.println("a = b: "+(a==b));
		System.out.println("boxA.equals(boxB): "+(boxA.equals(boxB)));
	}
}
