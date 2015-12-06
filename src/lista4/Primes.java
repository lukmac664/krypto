package lista4;
import java.security.SecureRandom;
public class Primes {

	public static void main(String[] args) {
		if(args.length != 2){
			System.out.println("Wrong number of arguments");
			return;
		}
		int lenghtInBits = Integer.parseInt(args[0]); 
		int numberOfSearchedPrimes = Integer.parseInt(args[1]); 
		SecureRandom secureRandom = new SecureRandom();
		Thread thread[] = new Thread[numberOfSearchedPrimes];
		for(int i = 0; i < numberOfSearchedPrimes; i++){
			thread[i] = new Thread(new PrimeThread(lenghtInBits,i,secureRandom));
			thread[i].start();
		}
	}
}
