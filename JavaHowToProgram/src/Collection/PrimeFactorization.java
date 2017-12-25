package Collection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unchecked")
public class PrimeFactorization {
	private static ArrayList<Integer> primeDictionary;
	private static Scanner input;

	static {		
		input = new Scanner(System.in);
		Path p = Paths.get("PrimeDictionary");

		if (Files.exists(p)) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("PrimeDictionary"))) {
				primeDictionary = (ArrayList<Integer>) ois.readObject();
				System.out.println("Dictionary loaded from files: "+primeDictionary);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Prime Dictionary File cannot be loaded.");
				e.printStackTrace();
			}
		}

		//file does not exist or cannot be loaded
		if (primeDictionary == null) { 
			primeDictionary = new ArrayList<>();
			// initiating a few small prime number to the queue
			Integer[] firstFewPrimes = new Integer[] { 2, 3, 5, 7, 11, 13, 17, 19, 23 };
			primeDictionary.addAll(Arrays.asList(firstFewPrimes));
		}
	}

	public static void main(String args[]) {
		try {
			List<Integer> lstPrimeFactors;

			while (true) {
				System.out.printf("Enter an integer to factorize (non-integer value to terminate): ");
				int n = input.nextInt();
				lstPrimeFactors = primeFactorize(n);

				System.out.printf("%nThe prime factor of %d are: ", n);
				for (Integer i : lstPrimeFactors) {
					System.out.printf("%d ", i);
				}
				System.out.println();
			}
		} catch (InputMismatchException e) {
			System.out.println("Non-integer entered, program terminating...");
		} finally {
			input.close();
			try (FileOutputStream fos = new FileOutputStream("PrimeDictionary");
					ObjectOutputStream os = new ObjectOutputStream(fos)) {
				os.writeObject(primeDictionary);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Determine if a number n is prime
	private static boolean isPrime(int n) {
		boolean isPrime = true;

		// check if the existing collection contains n
		if (primeDictionary.contains(n))
			return true;
		else if (Collections.max(primeDictionary) > n) { // n is smaller than
															// the largest prime
															// in the collection
			return false;
		} else if (n < 1 || n % 2 == 0 || n % 3 == 0)
			return false;
		else {
			// perform checking algorithm if n is greater than the largest prime
			// in the collection
			int i = 5;

			while (i * i < n) {
				if (n % i == 0 || n % (i + 2) == 0) {
					isPrime = false; // not a prime number, return immediately
				}
				i += 6;
			}
		}

		return isPrime;
	}

	private static ArrayList<Integer> primeFactorize(int n) {
		n = Math.abs(n); // consider positive values only
		ArrayList<Integer> primeFactors = new ArrayList<>();

		if (isPrime(n)) {
			primeFactors.add(1);
			primeFactors.add(n);
		} else {
			// find all prime numbers upto n
			addPrimesUpToN(n);

			for (int i = 0; i < primeDictionary.size(); i++) {
				int prime = primeDictionary.get(i);
				int remainder = n % prime;
				if (remainder == 0)
					primeFactors.add(prime);
			}
		}

		return primeFactors;
	}

	// find and add all primes upto n to the collections
	private static void addPrimesUpToN(int n) {
		int max = Collections.max(primeDictionary);
		if (max >= n) { // all primes upto n is already in the collection
			return;
		} else {
			for (int i = max; i <= n; i++) {
				if (isPrime(i)) {
					primeDictionary.add(i);
				}
			}
		}
	}
}
