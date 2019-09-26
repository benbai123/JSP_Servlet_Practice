package crypto.impl;

import java.util.Arrays;
import javax.crypto.Cipher;

import crypto.utils.AESUtils;
import crypto.utils.CryptoUtils;
/**
 * Class for convenient AES encrypt/decrypt, reuse Cipher and
 * provide clear method
 * 
 * Will always get Cipher with Key, IV and Salt, automatically
 * generate them if needed
 * 
 * @author benbai123
 *
 */
public class AESImpl {
	private char[] _key;
	private char[] _iv;
	private char[] _salt;
	private Cipher _encCipher;
	private Cipher _decCipher;
	
	/**
	 * Encrypt data
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String encrypt (String data) throws Exception {
		return AESUtils.encrypt(_encCipher, data);
	}
	/**
	 * Decrypt Data
	 * 
	 * @param encryptedData
	 * @return
	 * @throws Exception
	 */
	public String decrypt (String encryptedData) throws Exception {
		return AESUtils.decrypt(_decCipher, encryptedData);
	}
	// getters for Key, IV and Salt
	public char[] getKey () {
		return _key;
	}
	public char[] getIv () {
		return _iv;
	}
	public char[] getSalt () {
		return _salt;
	}
	// getters for instance
	public static AESImpl getInstance () throws Exception {
		AESImpl inst = new AESImpl();
		inst.generateKey();
		inst.generateIvFromKey();
		inst.generateSaltFromKey();
		inst.init();
		return inst;
	}
	public static AESImpl getInstance (char[] key) throws Exception {
		AESImpl inst = new AESImpl();
		inst._key = key;
		inst.generateIvFromKey();
		inst.generateSaltFromKey();
		inst.init();
		return inst;
	}
	public static AESImpl getInstance (char[] key, char[] iv) throws Exception {
		AESImpl inst = new AESImpl();
		inst._key = key;
		inst._iv = iv;
		inst.generateSaltFromKey();
		inst.init();
		return inst;
	}
	public static AESImpl getInstance (char[] key, char[] iv, char[] salt) throws Exception {
		AESImpl inst = new AESImpl();
		inst._key = key;
		inst._iv = iv;
		inst._salt = salt;
		inst.init();
		return inst;
	}
	private void generateKey () throws Exception {
		_key = AESUtils.generateKey();
	}
	private void generateIvFromKey () throws Exception {
		// generate iv from key
		byte[] md5Bytes = CryptoUtils.md5(_key);
		_iv = CryptoUtils.bytesToBase64Chars(md5Bytes);
		// clear local array
		Arrays.fill(md5Bytes, (byte) 0);
	}
	private void generateSaltFromKey () throws Exception {
		// generate salt from key
		// use sha1 to make sure different with IV
		byte[] saltBytes = CryptoUtils.sha1(_key);
		_salt = CryptoUtils.bytesToBase64Chars(saltBytes);
		// clear local array
		Arrays.fill(saltBytes, (byte) 0);
	}
	/**
	 * init Ciphers for Encryption/Decryption
	 * 
	 * @throws Exception
	 */
	private void init () throws Exception {
		byte[] ivBytes = CryptoUtils.md5(_iv);
		byte[] saltBytes = CryptoUtils.md5(_salt);
		_encCipher = AESUtils.getCipher(Cipher.ENCRYPT_MODE,
				_key, ivBytes, saltBytes);
		_decCipher = AESUtils.getCipher(Cipher.DECRYPT_MODE,
				_key, ivBytes, saltBytes);
		// clear local array
		Arrays.fill(ivBytes, (byte) 0);
		Arrays.fill(saltBytes, (byte) 0);
	}
	/**
	 * Clear Key, IV and Salt
	 */
	public void clear () {
		Arrays.fill(_key, '*');
		Arrays.fill(_iv, '*');
		Arrays.fill(_salt, '*');
	}
}
