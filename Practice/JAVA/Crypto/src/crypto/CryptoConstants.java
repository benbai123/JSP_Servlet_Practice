package crypto;

/**
 * Use enum for String Constants,
 * 
 * it is more convenient than lots of 
 * public static final String xxx = "xxx";
 * 
 * @author benbai123
 *
 */
public enum CryptoConstants {
	// crypto algorithms
	AES,  AES_CBC_PKCS5PADDING("AES/CBC/PKCS5PADDING"), PBKDF2WithHmacSHA256,
	RSA, SHA256withRSA,
	// message digest hash algorithms
	MD5, SHA_1("SHA-1"), SHA_256("SHA-256"),
	
	// encoding
	UTF_8("UTF-8")
	;
	
	public final String v;
	private CryptoConstants () {
		v = name();
	}
	private CryptoConstants (String str) {
		v = str;
	}
}
