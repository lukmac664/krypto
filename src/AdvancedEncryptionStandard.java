import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;


import org.apache.commons.codec.binary.Base64;
public class AdvancedEncryptionStandard
{
    private Key encryptionKey;

    public AdvancedEncryptionStandard(Key encryptionKey)
    {
        this.encryptionKey = encryptionKey;
    }

    public String encrypt(String plainText) throws Exception
    {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

        return Base64.encodeBase64String(encryptedBytes);
    }

    public String decrypt(String encrypted) throws Exception
    {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        byte[] plainBytes = cipher.doFinal(Base64.decodeBase64(encrypted));

        return new String(plainBytes);
    }

    private Cipher getCipher(int cipherMode)
            throws Exception
    {
        String encryptionAlgorithm = "AES/CBC/PKCS5Padding";
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        IvParameterSpec iv = new IvParameterSpec("AODVNUASDNVVAOVF".getBytes("UTF-8"));
        cipher.init(cipherMode, this.encryptionKey, iv);

        return cipher;
    }
}
