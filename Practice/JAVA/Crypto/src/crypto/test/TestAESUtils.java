package crypto.test;

import java.util.Arrays;

import javax.crypto.Cipher;
import crypto.utils.AESUtils;
import crypto.utils.CryptoUtils;

public class TestAESUtils {
	private static String _src = "test 臺灣 No.1";

	public static void main (String[] args) throws Exception {
		TestEncryptDecryptWithKey();
		TestEncryptDecryptWithKeyIvSalt();
	}

	private static void TestEncryptDecryptWithKey() throws Exception {
		// generate key, size should be 128/192/256
		// you will want to store this key somewhere for decrypt later
		char[] key = AESUtils.generateKey(256);
		
		// get Cipher for Encrypt with key
		Cipher encCipher = AESUtils.getCipher(Cipher.ENCRYPT_MODE, key);
		// encrypt data
		String encrypted = AESUtils.encrypt(encCipher, _src);
		
		// get Cipher for Decrypt with key
		Cipher decCipher = AESUtils.getCipher(Cipher.DECRYPT_MODE, key);
		// decrypt the encrypted data
		String decrypted = AESUtils.decrypt(decCipher, encrypted);
		
		// output result
		System.out.println("### TestEncryptDecryptWithKey");
		System.out.println("\t### _src = "+_src);
		System.out.println("\t### key = "+new String(key));
		System.out.println("\t### encrypted = "+encrypted);
		System.out.println("\t### decrypted = "+decrypted);
		
		// clear key data
		Arrays.fill(key, '*');
		// output key again
		System.out.println("\t### key = "+new String(key));
	}
	private static void TestEncryptDecryptWithKeyIvSalt () throws Exception {
		// generate key, can be any length here
		// you will want to store the generated
		// Key, IV and Salt for Decryption later
		char[] key = CryptoUtils.getRandomBase64Chars(123);
		// generate random iv
		char[] iv = AESUtils.getRandomIv();
		// generate random salt
		char[] salt = CryptoUtils.getRandomBase64Chars(22);
		
		// get Cipher for Encrypt with Key, IV and Salt
		Cipher encCipher = AESUtils.getCipher(Cipher.ENCRYPT_MODE, key,
				iv, salt);
		// encrypt data
		String encrypted = AESUtils.encrypt(encCipher, _src);
		
		// get Cipher for Decrypt with Key, IV and Salt
		Cipher decCipher = AESUtils.getCipher(Cipher.DECRYPT_MODE, key,
				iv, salt);
		// decrypt the encrypted data
		String decrypted = AESUtils.decrypt(decCipher, encrypted);
		
		// output result
		System.out.println("### TestEncryptDecryptWithKeyIvSalt");
		System.out.println("\t### _src = "+_src);
		System.out.println("\t### key = "+new String(key));
		System.out.println("\t### iv = "+new String(iv));
		System.out.println("\t### salt = "+new String(salt));
		System.out.println("\t### encrypted = "+encrypted);
		System.out.println("\t### decrypted = "+decrypted);
		
		// clear Key, IV and Salt
		Arrays.fill(key, '*');
		Arrays.fill(iv, '*');
		Arrays.fill(salt, '*');
		// output key again
		System.out.println("\t### key = "+new String(key));
		System.out.println("\t### iv = "+new String(iv));
		System.out.println("\t### salt = "+new String(salt));
	}
}
