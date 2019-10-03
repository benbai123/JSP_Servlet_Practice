package crypto.test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import javax.crypto.Cipher;

import crypto.utils.RSAUtils;

public class TestRSAUtils {
	private static char[] _publicKeyChars;
	private static char[] _privateKeyChars;
	private static String _src = "test 臺灣 No.1";
	public static void main (String[] args) throws Exception {
		testGenerateKeypair();
		testEncDecPubPri();
		testEncDecPriPub();
		testSignVerify();
	}

	private static void testGenerateKeypair() throws Exception {
		KeyPair keyPair = RSAUtils.generateKey(1024);
		_publicKeyChars = RSAUtils.getBase64PublicKeyChars(keyPair);
		_privateKeyChars = RSAUtils.getBase64PrivateKeyChars(keyPair);
		System.out.println("### testGenerateKeypair");
		System.out.println("\t publicKey "+new String(_publicKeyChars));
		System.out.println("\t privateKey "+new String(_privateKeyChars));
	}
	
	/**
	 * Encrypt by publicKey then decrypt by privateKey.
	 * @throws Exception 
	 */
	private static void testEncDecPubPri() throws Exception {
		PublicKey publicKey = RSAUtils.getPublicKeyFromBase64Chars(_publicKeyChars);
		PrivateKey privateKey = RSAUtils
				.getPrivateKeyFromBase64Chars(_privateKeyChars);
		Cipher encCipher = RSAUtils.getCipher(Cipher.ENCRYPT_MODE, publicKey);
		Cipher decCipher = RSAUtils.getCipher(Cipher.DECRYPT_MODE, privateKey);
		String encrypted = RSAUtils.encrypt(encCipher, _src);
		String decrypted = RSAUtils.decrypt(decCipher, encrypted);
		System.out.println("### testEncDecPubPri");
		System.out.println("\t encrypted "+encrypted);
		System.out.println("\t decrypted "+decrypted);
	}

	/**
	 * Encrypt by privateKey then decrypt by publicKey.
	 */
	private static void testEncDecPriPub() throws Exception {
		PublicKey publicKey = RSAUtils.getPublicKeyFromBase64Chars(_publicKeyChars);
		PrivateKey privateKey = RSAUtils
				.getPrivateKeyFromBase64Chars(_privateKeyChars);
		Cipher encCipher = RSAUtils.getCipher(Cipher.ENCRYPT_MODE, privateKey);
		Cipher decCipher = RSAUtils.getCipher(Cipher.DECRYPT_MODE, publicKey);
		String encrypted = RSAUtils.encrypt(encCipher, _src);
		String decrypted = RSAUtils.decrypt(decCipher, encrypted);
		System.out.println("### testEncDecPriPub");
		System.out.println("\t encrypted "+encrypted);
		System.out.println("\t decrypted "+decrypted);
	}
	/**
	 * Sign data with privateKey, verify it with publicKey, original data and sign
	 * 
	 * @throws Exception
	 */
	private static void testSignVerify () throws Exception {
		PublicKey publicKey = RSAUtils.getPublicKeyFromBase64Chars(_publicKeyChars);
		PrivateKey privateKey = RSAUtils
				.getPrivateKeyFromBase64Chars(_privateKeyChars);
		Signature signSignature = RSAUtils.getSignSignature(privateKey);
		Signature verifySignature = RSAUtils.getVerifySignature(publicKey);
		char[] data = _src.toCharArray();
		char[] sign = RSAUtils.sign(signSignature, data);
		boolean verify = RSAUtils.verify(verifySignature, data, sign);
		System.out.println("### testSignVerify");
		System.out.println("\t data "+new String(data));
		System.out.println("\t sign "+new String(sign));
		System.out.println("\t verified "+verify);
	}
}
