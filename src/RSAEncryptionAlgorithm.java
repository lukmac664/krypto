

public class RSAEncryptionAlgorithm {

	public static void main(String[] args) {
		if(args.length < 3){
			System.out.println("To generate keys: prog gen k d)");
			System.out.println("To encrypt file: prog enc fileToEncrypt resultFile");
			System.out.println("To decrypt file: prog dec encryptedFile resultFile");
		}
		if (args[0].equals("gen")) {
			KeyGenerator.generateKey(Integer.parseInt(args[2]), Integer.parseInt(args[1]));
			System.out.println("Keys generated");
			return;
		} else if (args[0].equals("enc")) {
			RSA.encrypt(args[1], args[2]);
		} else if (args[0].equals("dec")) {
			RSA.decrypt(args[1], args[2]);
		}
	}
}