package crypto.test;

import static org.junit.Assert.fail;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import crypto.utils.CryptoUtils;

/**
 * Test case for CryptoUtils, JUnit flavor
 * 
 * @author benbai123
 *
 */
public class TestCryptoUtilsJunit {
	private static Logger logger = Logger.getLogger(TestCryptoUtilsJunit.class.getName());
	/**
	 * Probably need to initial environment, etc
	 */
	@BeforeClass
	public static void beforeClass () {
		logger.info("\t\tbeforeClass");
	}
	/**
	 * Probably need to reset environment, etc
	 */
	@AfterClass
	public static void afterClass () {
		logger.info("\t\t afterClass");
	}
	/**
	 * Probably need to preset some status of some instance, etc
	 */
	@Before
	public void beforeTest () {
		logger.info("\t\t beforeTest");
	}
	/**
	 * Probably need to reset/clear some status of some instance, etc
	 */
	@After
	public void afterTest () {
		logger.info("\t\t afterTest");
	}
	@Test
	public void testGetRandomBytes () {
		try {
			byte[] bytes = CryptoUtils.getRandomBytes(3);
			String bytesContent = getBytesContent(bytes);
			logger.info("\n\t testGetRandomBytes "+bytesContent+"\n");
		} catch (Exception e) {
			fail("testGetRandomBytes has problem "+e.getMessage());
		}
	}
	@Test
	public void testUtf8CharsToBytes() {
		try {
			byte[] bytes = CryptoUtils.utf8CharsToBytes("臺灣 No.1".toCharArray());
			String bytesContent = getBytesContent(bytes);
			logger.info("\n\t testUtf8CharsToBytes "+bytesContent+"\n");
		} catch (Exception e) {
			fail("testUtf8CharsToBytes has problem "+e.getMessage());
		}
	}
	
	@Test
	public void testBytesToUtf8Chars () {
		String[] bitStrings = new String[] {"11101000", "10000111", "10111010",
				"11100111", "10000001", "10100011", "00100000", "01001110",
				"01101111", "00101110", "00110001"};
		byte[] bytes = getBytesFromBitStrings(bitStrings);
		try {
			logger.info("\n\t testBytesToUtf8Chars "+new String(CryptoUtils.bytesToUtf8Chars(bytes))+"\n");
		} catch (Exception e) {
			fail("testBytesToUtf8Chars has problem "+e.getMessage());
		}
	}
	@Test
	public void testBytesToBase64Chars () {
		String[] bitStrings = new String[] {"11101000", "10000111", "10111010"};
		byte[] bytes = getBytesFromBitStrings(bitStrings);
		try {
			logger.info("\n\t testBytesToBase64Chars "+new String(CryptoUtils.bytesToBase64Chars(bytes))+"\n");
		} catch (Exception e) {
			fail("testBytesToBase64Chars has problem "+e.getMessage());
		}
	}
	@Test
	public void testBase64CharsToBytes () {
		try {
			char[] b64Chars = "6Ie6".toCharArray();
			byte[] bytes = CryptoUtils.base64CharsToBytes(b64Chars);
			String bytesContent = getBytesContent(bytes);
			logger.info("\n\t testBase64CharsToBytes "+bytesContent+"\n");
		} catch (Exception e) {
			fail("testBase64CharsToBytes has problem "+e.getMessage());
		}
	}
	
	@Test
	public void testBytesToBase64String () {
		String[] bitStrings = new String[] {"11101000", "10000111", "10111010"};
		byte[] bytes = getBytesFromBitStrings(bitStrings);
		try {
			logger.info("\n\t testBytesToBase64String "+CryptoUtils.bytesToBase64String(bytes)+"\n");
		} catch (Exception e) {
			fail("testBytesToBase64String has problem "+e.getMessage());
		}
	}
	
	@Test
	public void testBase64StringToBytes () {
		try {
			byte[] bytes = CryptoUtils.base64StringToBytes("6Ie6");
			String bytesContent = getBytesContent(bytes);
			logger.info("\n\t testBase64StringToBytes "+bytesContent+"\n");
		} catch (Exception e) {
			fail("testBase64StringToBytes has problem "+e.getMessage());
		}
	}
	
	@Test
	public void testSha256 () {
		try {
			char[] chars = new char[] {'s', 'h', 'a', '2', '5', '6'};
			byte[] bytes = CryptoUtils.sha256(chars);
			String bytesContent = getBytesContent(bytes);
			logger.info("\n\t testSha256 "+bytesContent+"\n");
		} catch (Exception e) {
			fail("testSha256 has problem "+e.getMessage());
		}
	}
	
	@Test
	public void testSha1 () {
		try {
			char[] chars = new char[] {'s', 'h', 'a', '1'};
			byte[] bytes = CryptoUtils.sha1(chars);
			String bytesContent = getBytesContent(bytes);
			logger.info("\n\t testSha1 "+bytesContent+"\n");
		} catch (Exception e) {
			fail("testSha1 has problem "+e.getMessage());
		}
	}
	
	@Test
	public void testMd5 () {
		try {
			char[] chars = new char[] {'m', 'd', '5'};
			byte[] bytes = CryptoUtils.md5(chars);
			String bytesContent = getBytesContent(bytes);
			logger.info("\n\t testMd5 from chars "+bytesContent+"\n");
			bytes = CryptoUtils.md5(CryptoUtils.utf8CharsToBytes(chars));
			bytesContent = getBytesContent(bytes);
			logger.info("\n\t testMd5 from bytes "+bytesContent+"\n");
		} catch (Exception e) {
			fail("testMd5 has problem "+e.getMessage());
		}
	}
//	encrypt skip, tested in TestAESUtils/TestRSAUtils
//	decrypt skip, tested in TestAESUtils/TestRSAUtils
	private static String getBytesContent (byte[] bytes) {
		StringBuilder sb = new StringBuilder("\n\t bytes: ");
		int cnt = 0;
		for (byte b : bytes) {
			sb.append(Integer.toBinaryString(b & 255 | 256).substring(1))
				.append(" ");
			cnt++;
			if (cnt%3 == 0) {
				sb.append("\n\t\t");
			}
		}
		return sb.toString();
	}
	private static byte[] getBytesFromBitStrings (String[] bitStrings) {
		byte[] bytes = new byte[bitStrings.length];
		for (int i = 0; i < bitStrings.length; i++) {
			bytes[i] = (byte) Integer.parseInt(bitStrings[i], 2);
		}
		return bytes;
	}
}
