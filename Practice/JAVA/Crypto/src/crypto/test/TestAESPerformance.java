package crypto.test;

import javax.crypto.Cipher;

import crypto.test.helper.PerformanceTestHelper;
import crypto.utils.AESUtils;
import crypto.utils.CryptoUtils;

public class TestAESPerformance {
	private static char[] _key;
	private static char[] _iv;
	private static char[] _salt;
	public static void main (String[] args) throws Exception {
		// init Key, IV and Salt first
		// generate Key
		_key = AESUtils.generateKey(256);
		// generate random iv
		_iv = AESUtils.getRandomIv();
		// generate random salt
		_salt = CryptoUtils.getRandomBase64Chars(8);
		
		testCommonAESCBCPKCS5PADDING();
		testPBKDF2WithHmacSHA256();
	}

	private static void testCommonAESCBCPKCS5PADDING() throws Exception {
		testCreateInstanceEachTimeCommon();
		testReuseInstanceCommon();
	}

	private static void testCreateInstanceEachTimeCommon() throws Exception {
		new PerformanceTestHelper("testCreateInstanceEachTimeCommon") {
			long now = System.currentTimeMillis();
			@Override
			public void run() throws Exception {
				// you can even try generate key each time
				// will still much faster than testCreateInstanceEachTimePBK
//				_key = AESUtils.generateKey(256);
				// get Cipher for Encrypt with key
				Cipher encCipher = AESUtils.getCipher(Cipher.ENCRYPT_MODE, _key);
				// encrypt data
				String encrypted = AESUtils.encrypt(encCipher, "test"+now);
				// print 10th char
				System.out.print(encrypted.charAt(10));
				now++;
			}
		}.setRuns(100)
		.setShouldWarmUp(true)
		.doTest()
		.showResult();
	}

	private static void testReuseInstanceCommon() throws Exception {
		new PerformanceTestHelper("testReuseInstanceCommon") {
			long now = System.currentTimeMillis();
			Cipher encCipher = null;
			@Override
			public void run() throws Exception {
				if (encCipher == null) {
					// get Cipher for Encrypt with key
					encCipher = AESUtils.getCipher(Cipher.ENCRYPT_MODE, _key);
				}
				// encrypt data
				String encrypted = AESUtils.encrypt(encCipher, "test"+now);
				// print 10th char
				System.out.print(encrypted.charAt(10));
				now++;
			}
			@Override
			/**
			 * Do not call run method or the first getCipher will skipped 
			 */
			public void warmup () throws Exception {
				// get Cipher
				Cipher c = AESUtils.getCipher(Cipher.ENCRYPT_MODE, _key);
				// encrypt data
				String encrypted = AESUtils.encrypt(c, "test");
				// print 10th char
				System.out.print(encrypted.charAt(10));
			}
		}.setRuns(100)
		.setShouldWarmUp(true)
		.doTest()
		.showResult();
	}

	private static void testPBKDF2WithHmacSHA256() throws Exception {
		testCreateInstanceEachTimePBK();
		testReuseInstancePBK();
	}

	private static void testCreateInstanceEachTimePBK() throws Exception {
		new PerformanceTestHelper("testCreateInstanceEachTimePBK") {
			long now = System.currentTimeMillis();
			@Override
			public void run() throws Exception {
				// get Cipher for Encrypt with Key, IV and Salt
				Cipher encCipher = AESUtils.getCipher(Cipher.ENCRYPT_MODE, _key,
						_iv, _salt);
				// encrypt data
				String encrypted = AESUtils.encrypt(encCipher, "test"+now);
				// print 10th char
				System.out.print(encrypted.charAt(10));
				now++;
			}
		}.setRuns(100)
		.setShouldWarmUp(true)
		.doTest()
		.showResult();
	}

	private static void testReuseInstancePBK() throws Exception {
		new PerformanceTestHelper("testReuseInstancePBK") {
			long now = System.currentTimeMillis();
			Cipher encCipher = null;
			@Override
			public void run() throws Exception {
				if (encCipher == null) {
					// get Cipher for Encrypt with key
					encCipher = AESUtils.getCipher(Cipher.ENCRYPT_MODE, _key,
							_iv, _salt);
				}
				// encrypt data
				String encrypted = AESUtils.encrypt(encCipher, "test"+now);
				// print 10th char
				System.out.print(encrypted.charAt(10));
				now++;
			}
			@Override
			/**
			 * Do not call run method or the first getCipher will skipped 
			 */
			public void warmup () throws Exception {
				// get Cipher
				Cipher c = AESUtils.getCipher(Cipher.ENCRYPT_MODE, _key,
						_iv, _salt);
				// encrypt data
				String encrypted = AESUtils.encrypt(c, "test");
				// print 10th char
				System.out.print(encrypted.charAt(10));
			}
		}.setRuns(100)
		.setShouldWarmUp(true)
		.doTest()
		.showResult();
	}
}
