

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RSA {
	private static BigInteger e, n, d;

	private static void readDataFromPublicKey() throws FileNotFoundException{
		File key = new File("public.key");
		Scanner reader = new Scanner(key);	
		e = new BigInteger(reader.nextLine());
		n = new BigInteger(reader.nextLine());
		reader.close();
	}
	
	private static void readDataFromPrivateKey() throws FileNotFoundException{
		File key = new File("private.key");
		Scanner reader = new Scanner(key);
		d = new BigInteger(reader.nextLine());
		n = new BigInteger(reader.nextLine());
		reader.close();
	}
	
	public static void encrypt(String fileToEncrypt, String resultFile) {
		try {
			readDataFromPublicKey();
			FileInputStream fileInput = new FileInputStream(fileToEncrypt);
			BigInteger blockOfData = null;
			String readChars = "";
			BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));
			int byteOfData = fileInput.read();
			
			while (byteOfData != -1) {
				readChars += (char) byteOfData;
				blockOfData = new BigInteger(readChars.getBytes());
				if (blockOfData.compareTo(n) > 0) {
					readChars = readChars.substring(0, readChars.length() - 1);
					blockOfData = new BigInteger(readChars.getBytes());				
					writer.write(blockOfData.modPow(e, n).toString());
					readChars = "" + (char) byteOfData;
				}
				byteOfData = fileInput.read();
				if(byteOfData == -1){
					writer.write(new String(blockOfData.modPow(e, n).toString()));
				}
			}
			writer.close();
			fileInput.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Encryption done");

	}

	public static void decrypt2(String ciphertext, String resultFile) {
		/*
		 * Pobieranie klucza
		 */
		
		try {
			File key = new File("private.key");
			Scanner reader;
			reader = new Scanner(key);
			d = new BigInteger(reader.nextLine());
			n = new BigInteger(reader.nextLine());

			BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));
			FileInputStream fileInput = new FileInputStream(ciphertext);
			int r;

			/*
			 * Deszyfrowanie
			 */
			String content = new String(Files.readAllBytes(Paths.get(ciphertext)));
			String[] checked = content.split(" ");
			for (String o : checked) {
				BigInteger result = new BigInteger(o).modPow(d, n);
				writer.write(new String(result.toByteArray()));
			}
			writer.close();
			fileInput.close();
			System.out.println("Deszyfrowanie zakoñczone!");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public static void decrypt(String encryptedInputFile, String resultFile) {

		try {

			readDataFromPrivateKey();

			BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));

			FileInputStream fileInput = new FileInputStream(encryptedInputFile);

			String contentOfInputFile = new String(Files.readAllBytes(Paths.get(encryptedInputFile)));

			List<String> inputDataDividedIntoBlocks = divideIntoBlocksOfDate(contentOfInputFile);
			
			for (String block : inputDataDividedIntoBlocks) {

				BigInteger result = new BigInteger(block).modPow(d, n);

				writer.write(result.toByteArray().toString());

			}

			writer.close();

			fileInput.close();	

			System.out.println("Decryption done!");

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}
	
	
	private static List<String> divideIntoBlocksOfDate(String contentOfInputFile){
		
		

		List<String> inputDataDividedIntoBlocks = new ArrayList<String>();
		int index = 0;
		BigInteger blockOfData = null;
		String readChar= new String();
		while (index < contentOfInputFile.length()) {
			readChar += contentOfInputFile.charAt(index);
			blockOfData = new BigInteger(readChar.getBytes());
			//blockOfData = blockOfData.divide(new BigInteger("2"));
			if(blockOfData.compareTo(n) > 0){
				inputDataDividedIntoBlocks.add(readChar);
				readChar = new String();
			}
			
			index++;
		    //inputDataDividedIntoBlocks.add(contentOfInputFile.substring(index, Math.min(index + n.intValue(),contentOfInputFile.length())));
		    //index += n.intValue();
		}
		return inputDataDividedIntoBlocks;
	}

}