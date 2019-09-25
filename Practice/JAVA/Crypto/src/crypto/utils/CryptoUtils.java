package crypto.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import crypto.CryptoConstants;

public class CryptoUtils {

	/**
	 * Convert UTF-8 char array to byte array
	 * 
	 * @param chars
	 * @return
	 */
	public static byte[] utf8CharsToBytes(char[] chars) {
		CharBuffer charBuffer = CharBuffer.wrap(chars);
		ByteBuffer byteBuffer = Charset.forName(CryptoConstants.UTF_8.v).encode(charBuffer);
		byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
				byteBuffer.position(), byteBuffer.limit());
		// clear all local array
		Arrays.fill(byteBuffer.array(), (byte) 0);
		return bytes;
	}
	/**
	 * Convert byte array to UTF-8 char array
	 * You should confirm the bytes are all invalid UTF-8 char
	 * or use bytesToBase64Chars instead
	 * 
	 * @param bytes
	 * @return
	 */
	public static char[] bytesToUtf8Chars (byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		CharBuffer charBuffer = Charset.forName(CryptoConstants.UTF_8.v).decode(byteBuffer);
		char[] chars = Arrays.copyOfRange(charBuffer.array(),
				charBuffer.position(), charBuffer.limit());
		// clear all local array
		Arrays.fill(charBuffer.array(), '*');
		return chars;
	}
	/**
	 * Convert any byte array to Base64 Encoded char array
	 * 
	 * @param bytes byte array contains any bytes
	 * @return Base64 Encoded char array
	 */
	public static char[] bytesToBase64Chars (byte[] bytes) {
		byte[] b64Bytes = Base64.getEncoder().encode(bytes);
		char[] b64Chars = bytesToUtf8Chars(b64Bytes);
		// clear all local array
		Arrays.fill(b64Bytes, (byte) 0);
		return b64Chars;
	}
	/**
	 * Convert Base64 Encoded char array to Base64 Decoded byte array
	 * 
	 * @param b64Chars Base64 Encoded char array
	 * @return byte array, Base64 Decoded, contains any bytes
	 */
	public static byte[] base64CharsToBytes (char[] b64Chars) {
		byte[] b64Bytes = utf8CharsToBytes(b64Chars);
		byte[] bytes = Base64.getDecoder().decode(b64Bytes);
		// clear all local array
		Arrays.fill(b64Bytes, (byte) 0);
		return bytes;
	}
	/**
	 * Convert byte array to Base64 Encoded String
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToBase64String (byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}
	/**
	 * Convert Base64 Encoded String to Base64 Decoded byte array
	 * 
	 * @param b64String
	 * @return
	 */
	public static byte[] base64StringToBytes (String b64String) {
		return Base64.getDecoder().decode(b64String);
	}
	/**
	 * Get md5 message digest from byte array
	 * 
	 * @param bytes byte array, assumed contains valid UTF-8 bytes
	 * @return the md5 message digest of bytes
	 * @throws Exception
	 */
	public static byte[] md5 (byte[] bytes) throws Exception {
		MessageDigest digest = MessageDigest.getInstance(CryptoConstants.MD5.v);
		return digest.digest(bytes);
	}
}
