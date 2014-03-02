import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Crypto {
	
	private static final String TAG = "[HawkEye][Crypto]";
	private static final String ENCRYPTION_STANDARD = "AES";
	private static final String ENCYPTION_ALGORITHM = "SHA1PRNG";

	public static byte[] generateKey(byte[] randomNumberSeed) {
		SecretKey secretKey = null;
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance(ENCRYPTION_STANDARD);
			SecureRandom random = SecureRandom.getInstance(ENCYPTION_ALGORITHM);
			random.setSeed(randomNumberSeed);
			keyGen.init(256, random);
			secretKey = keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No such algorithm exception");
		} 
		return secretKey.getEncoded();
	}
	
	public static byte[] encrypt(byte[] key, byte[] data) {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, ENCRYPTION_STANDARD);
		Cipher cipher;
		byte[] cipherText = null;
		try {
			cipher = Cipher.getInstance(ENCRYPTION_STANDARD);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			cipherText = cipher.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		} catch (InvalidKeyException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
		}
		return cipherText;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String pin = "123456";
		byte[] key = generateKey(pin.getBytes());
		String data = "Hello, this is unencrypted text that could possibly contain important imformation. Please encrypt this!";
		//byte[] cipherText = encrypt(key, data);
		
		System.out.println(pin.getBytes());
		System.out.println(Base64.encodeToString(data, Base64.DEFAULT););
	}

}
