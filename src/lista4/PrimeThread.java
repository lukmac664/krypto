package lista4;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimeThread implements Runnable {
	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = new BigInteger("2");
	private static final BigInteger THREE = new BigInteger("3");
	int k, id;
	byte bytes[];
	private SecureRandom secureRandom;

	boolean prime = false;

	public PrimeThread(int k, int id, SecureRandom secureRandom) {
		this.k = k / 8;
		this.id = id;
		this.secureRandom = secureRandom;
		bytes = new byte[k / 8];
	}

	@Override
	public void run() {
		while (true) {
			secureRandom.nextBytes(bytes);
			BigInteger randomPrime = new BigInteger(bytes);
			randomPrime = randomPrime.abs();
			if (checkPrimary(randomPrime)) {
				System.out.println(id + ") " + randomPrime.toString());
				break;
			}
		}
	}

	public boolean checkPrimary(BigInteger randomPrime) {
		if ((randomPrime.compareTo(THREE) == 1) && (randomPrime.mod(TWO).equals(ONE))) {
			return testIfPrimeUsingRabinMillerMethod(randomPrime);
		}
		return false;
	}
	
	
	public boolean testIfPrimeUsingRabinMillerMethod(BigInteger randomPrime) {
		int k = 0;
		BigInteger a, v, i;
		BigInteger s = new BigInteger(randomPrime.subtract(ONE).toString());
		BigInteger t = ZERO;

		while ((s.mod(TWO).compareTo(ZERO) == 0)) {
			s = s.divide(TWO);
			t = t.add(ONE);
		}
		while (k < 128) {
			do {
				a = new BigInteger(Integer.toString(secureRandom.nextInt()));
			} while ((a.compareTo(TWO) != 1) && (a.compareTo(randomPrime.subtract(ONE)) != -1));

			v = a.modPow(s, randomPrime);

			if (!v.equals(ONE)) {
				i = ZERO;
				while (!v.equals(randomPrime.subtract(ONE))) {
					if (i.equals(t.subtract(ONE))) {
						return false;
					} else {
						v = v.modPow(TWO, randomPrime);
						i = i.add(ONE);
					}
				}
			}
			k = k + 2;
		}
		return true;
	}
}
