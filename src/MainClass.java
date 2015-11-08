
import java.io.FileInputStream;
import java.security.*;


public class MainClass {

  public static void main(String[] args) throws Exception {
	  
	  
	
	//String alias = "aeskey2";
    //String keystoreFilename = "/home/ukasz/workspace/AES/bin/keystore.jck";
	String keystoreFilename = args[0];
	String alias = args[1];
	//char[] password = "mac64c".toCharArray();
	char[] password = System.console().readPassword();
	
    FileInputStream fIn = new FileInputStream(keystoreFilename);
    KeyStore keystore = KeyStore.getInstance("JCEKS");
    keystore.load(fIn, password);
    Key key = keystore.getKey(alias, password); 

    String plainText = "Hello World!";
    AdvancedEncryptionStandard advancedEncryptionStandard = new AdvancedEncryptionStandard(key);
    String cipherText = advancedEncryptionStandard.encrypt(plainText);
    String decryptedCipherText = advancedEncryptionStandard.decrypt(cipherText);

    System.out.println(plainText);
    System.out.println(cipherText);
    System.out.println(decryptedCipherText);
  }
}
