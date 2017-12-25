package Algorithm;

public class KaratsubaMultiplication {
	public long karatsubaMultiply(int x, int y) {
		int xLength = String.valueOf(x).length();
		int yLength = String.valueOf(y).length();
		int digitCounts = Math.max(xLength, yLength);
		int multiplier = 1;
		int m = digitCounts/2;
		
		if(x==0||y==0){
			return 0;
		}
		else if (digitCounts > 1) {
			// make 2 numbers equal in digit length
			if (xLength < digitCounts){
				multiplier = (int) Math.pow(10, digitCounts - xLength);
				x = x * multiplier;
			}
			else if (yLength < digitCounts) {
				multiplier = (int) Math.pow(10, digitCounts - yLength);
				y = y * multiplier;
			}			
			
			//break down each n-digit number into two numbers of n/2 digits
			int a = Integer.parseInt(String.valueOf(x).substring(0, m));
			int b = Integer.parseInt(String.valueOf(x).substring(m,digitCounts));
			int c = Integer.parseInt(String.valueOf(y).substring(0, m));
			int d = Integer.parseInt(String.valueOf(y).substring(m,digitCounts));
			
			long ac = karatsubaMultiply(a, c);
			long bd = karatsubaMultiply(b, d);
			long adPlusBc = karatsubaMultiply(a+b, c+d) - ac - bd;
			//for odd number digit, digitCount is odd so a & c will have 1 less digit than b & d
			//we have to multiply ac with 10 to power m+1 (digitCount mod 2 equal 1), which is the length of b & d
			long result = (long)(ac * Math.pow(10, 2*(m+digitCounts%2)) + adPlusBc * Math.pow(10,m+digitCounts%2) + bd)/multiplier;
			return result;
		}

		return x*y;
	}
	
	public static void main(String[] args){
		KaratsubaMultiplication obj = new KaratsubaMultiplication();
		System.out.println(obj.karatsubaMultiply(123444, 5678));
	}
}
