

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyGenerator {
	
	public static void generateKey(int bits, int quanity) {

		BigInteger lengthOfModule = BigInteger.ONE;
		BigInteger phi = BigInteger.ONE;  
		BigInteger p[] = new BigInteger[quanity];
		SecureRandom secureRandom = new SecureRandom();
		for (int i = 0; i < quanity; i++) {
			p[i] = new BigInteger(bits, 100, secureRandom);
			lengthOfModule = lengthOfModule.multiply(p[i]);
			phi = p[i].subtract(BigInteger.ONE).multiply(phi);
		}
		BigInteger e = getPrime(phi, secureRandom);
		BigInteger lenghtInBits = e.modInverse(phi);	

		try {
			PrintWriter publicKey = new PrintWriter("public.key");
			publicKey.println(e.toString());
			publicKey.println(lengthOfModule.toString());

			PrintWriter privateKey = new PrintWriter("private.key");
			privateKey.println(lenghtInBits.toString());
			privateKey.println(lengthOfModule.toString());

			publicKey.close();
			privateKey.close();
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}
	}

	private static BigInteger getPrime(BigInteger phi, SecureRandom secureRandom) {
		int length = phi.bitLength() - 1;
		BigInteger e = BigInteger.probablePrime(length, secureRandom);
		while (!(phi.gcd(e)).equals(BigInteger.ONE)) {
			e = BigInteger.probablePrime(length, secureRandom);
		}
		return e;
	}
}