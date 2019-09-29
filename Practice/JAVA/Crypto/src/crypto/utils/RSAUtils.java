package crypto.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

import crypto.CryptoConstants;

/**
 * RSA Generate keys, get Cipher, Encrypt/Decrypt
 * 
 * @author benbai123
 *
 */
public class RSAUtils {
	/**
	 * Generate 2048 bits RSA KeyPair
	 * 
	 * @return
	 * @throws Exception
	 */
	public static KeyPair generateKey () throws Exception {
		return generateKey(2048);
	}
	/**
	 * Generate any size of bits RSA KeyPair
	 * 
	 * @param size the size of KeyPair in bits
	 * @return
	 * @throws Exception
	 */
	public static KeyPair generateKey (int size) throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(CryptoConstants.RSA.v);
		keyGen.initialize(size);
		return keyGen.genKeyPair();
	}
	/**
	 * Get Base64 encoded chars of publicKey from KeyPair
	 * 
	 * @param keyPair
	 * @return
	 */
	public static char[] getBase64PublicKeyChars (KeyPair keyPair) {
		return CryptoUtils.bytesToBase64Chars(keyPair.getPublic().getEncoded());
	}
	/**
	 * Get Base64 encoded chars of privateKey from KeyPair
	 * 
	 * @param keyPair
	 * @return
	 */
	public static char[] getBase64PrivateKeyChars (KeyPair keyPair) {
		return CryptoUtils.bytesToBase64Chars(keyPair.getPrivate().getEncoded());
	}
	/**
	 * Get PublicKey from Base64 encoded chars
	 * 
	 * @param base64Chars
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKeyFromBase64Chars (char[] base64Chars)
			throws Exception {
		byte[] bytes = CryptoUtils.base64CharsToBytes(base64Chars);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
		// clear local array
		Arrays.fill(bytes, (byte) 0);
		return KeyFactory.getInstance(CryptoConstants.RSA.v).generatePublic(spec);
	}
	/**
	 * Get PrivateKey from Base64 encoded chars
	 * 
	 * @param base64Chars
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyFromBase64Chars(char[] base64Chars)
		throws Exception {
		byte[] bytes = CryptoUtils.base64CharsToBytes(base64Chars);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
		// clear local array
		Arrays.fill(bytes, (byte) 0);
		return KeyFactory.getInstance(CryptoConstants.RSA.v).generatePrivate(spec);
	}
	/**
	 * Get Cipher for Encryption/Decryption with PublicKey/PrivateKey
	 * 
	 * @param mode Cipher.ENCRYPT_MODE (1) or Cipher.DECRYPT_MODE (2)
	 * @param key RSA PublicKey/PrivateKey
	 * @return
	 * @throws Exception
	 */
	public static Cipher getCipher (int mode, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(CryptoConstants.RSA.v);
		cipher.init(mode, key);
		return cipher;
	}
	/**
	 * Encrypt with provided Cipher
	 * 
	 * @param encCipher
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt (Cipher encCipher, String data) throws Exception {
		return CryptoUtils.encrypt(encCipher, data);
	}
	/**
	 * Decrypt with provided Cipher
	 * 
	 * @param decCipher
	 * @param encryptedData
	 * @return
	 * @throws Exception
	 */
	public static String decrypt (Cipher decCipher, String encryptedData) throws Exception {
		return CryptoUtils.decrypt(decCipher, encryptedData);
	}
}
