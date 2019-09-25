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
	AES, UTF_8("UTF-8"),
	AES_CBC_PKCS5PADDING("AES/CBC/PKCS5PADDING"), MD5;
	
	public final String v;
	private CryptoConstants () {
		v = name();
	}
	private CryptoConstants (String str) {
		v = str;
	}
}
