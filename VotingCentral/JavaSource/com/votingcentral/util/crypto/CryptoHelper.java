/*
 * Created on Dec 14, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.crypto;

/**
 * @author harishn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.base64.Base64;

/**
 * @author harishn
 * 
 * A generic class that helps in "Symmetric" encrypting and decrypting of
 * Strings.
 * 
 * 1. When a String is encrypted using an instance of this class and if the
 * decrypt is called on the same instance, we should get back the original
 * String.
 * 
 * 2. A String is encrypted using this class, using a given transformation mode,
 * salt and secret key. If a new instance of this class created with the same
 * transformation mode, salt and secret key and the previously encrypted string
 * is decrypted, we should get the original string back.
 * 
 * Algorithms supported: DES, DESede and Blowfish.
 * 
 * Modes supported : ECB: Electronic Codebook Mode CBC: Cipher Block Chaining
 * Mode PCBC: Plaintext Cipher Block Chaining CFB: Cipher Feedback Mode OFB:
 * Output Feedback Mode
 * 
 * Padding types supported: NoPadding: no padding PKCS5Padding: padding scheme
 * described by RSA Labs SSL3Padding: padding scheme defined in SSL Protocol
 * 
 * Transformations of the following form are ONLY supported.
 * "algorithm/mode/padding" or "algorithm"
 * 
 * 
 *  
 */
public class CryptoHelper {

	private static Log log = LogFactory.getLog(CryptoHelper.class);

	//This key used in the PaypalHelper class too.
	//Should we genrate a new one ??
	private static final String ENC_DEC_KEY = "zbns3yOrCCyn0ymRvJHTTHw0uuz0Gnye";

	//get the default secret key
	private static final SecretKey S_SECRET_KEY = deriveTheDefaultSecretKey();

	private SecretKey m_secretKey = null;

	//Triple DES algorithm is the default we support
	private static final String S_DEFAULT_ALGORITHM = "DESede";

	private String m_algorithm = null;

	//CBC mode is the default
	private static final String S_DEFAULT_MODE = "CBC";

	private String m_mode = null;

	//PKCS5Padding is default
	private static final String S_DEFAULT_PADDING = "PKCS5Padding";

	private String m_padding = null;

	private String m_transformation = null;

	private Cipher m_ecipher = null;

	private Cipher m_dcipher = null;

	//default Salt
	private static final byte[] S_DEFAULT_SALT = { (byte) 0xc7, (byte) 0x73,
			(byte) 0x21, (byte) 0x8c, (byte) 0x7e, (byte) 0xc8, (byte) 0xee,
			(byte) 0x99 };

	private byte[] m_salt = null;

	/**
	 * Default implementation provides with the transformation
	 * DESede/CBC/PKCS5Padding using the dafault salt and key.
	 *  
	 */
	public CryptoHelper() {
		this(S_DEFAULT_ALGORITHM, S_DEFAULT_MODE, S_DEFAULT_PADDING,
				S_DEFAULT_SALT, S_SECRET_KEY);
	}

	/**
	 * 
	 * @param algorithm
	 * @param mode
	 * @param padding
	 * @param salt
	 * 
	 * @param key
	 * 
	 * Make sure your are passing in valid combinations. Transformations of the
	 * following form are ONLY supported. "algorithm/mode/padding" or
	 * "algorithm"
	 */
	public CryptoHelper(String algorithm, String mode, String padding,
			byte[] salt, SecretKey key) {

		try {
			m_algorithm = algorithm;
			m_mode = mode;
			m_padding = padding;
			m_transformation = deriveTransformation();
			m_salt = salt;
			m_secretKey = key;

			m_ecipher = Cipher.getInstance(deriveTransformation());
			m_dcipher = Cipher.getInstance(deriveTransformation());

			IvParameterSpec iv = new IvParameterSpec(salt);

			m_ecipher.init(Cipher.ENCRYPT_MODE, key, iv);
			m_dcipher.init(Cipher.DECRYPT_MODE, key, iv);

		} catch (java.security.InvalidKeyException e) {
			log.fatal("Error invalid key", e);
		} catch (NoSuchAlgorithmException e) {
			log.fatal("Error algorithm", e);
		} catch (NoSuchPaddingException e) {
			log.fatal("Error invalid padding", e);
		} catch (InvalidAlgorithmParameterException e) {
			log.fatal("Error invalid algorithm parameter", e);
		}
	}

	/**
	 * 
	 * @param str
	 * @return encryptedStr An encrypted string using the algorithm specified.
	 */
	public String encrypt(String str) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");
			// Encrypt
			byte[] enc = m_ecipher.doFinal(utf8);
			// Encode bytes to base64 to get a string
			return Base64.encode(enc);
		} catch (BadPaddingException e) {
			log.fatal("Bad padding", e);
		} catch (IllegalBlockSizeException e) {
			log.fatal("Illegal block size", e);
		} catch (UnsupportedEncodingException e) {
			log.fatal("unsupported encoding", e);
		}
		return "";
	}

	/**
	 * 
	 * @param str
	 *            to be encrypted
	 * @return A decrypted string
	 */
	public String decrypt(String str) {
		try {
			// Decode base64 to get bytes
			byte[] dec = Base64.decode(str);
			// Decrypt
			byte[] utf8 = m_dcipher.doFinal(dec);
			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (BadPaddingException e) {
			log.fatal("Bad padding", e);
		} catch (IllegalBlockSizeException e) {
			log.fatal("illegal block size", e);
		} catch (UnsupportedEncodingException e) {
			log.fatal("unsuppoted encoding", e);
		}
		return "";
	}

	/**
	 * @return algorithm being used by the encrypter/decrypter
	 */
	public String getAlgorithm() {
		return m_algorithm;
	}

	/**
	 * @return mode being used by the encrypter/decrypter
	 */
	public String getMode() {
		return m_mode;
	}

	/**
	 * @return padding type being used by the encrypter/decrypter
	 */
	public String getPadding() {
		return m_padding;
	}

	/**
	 * @return A transformation is of the form: "algorithm/mode/padding" or
	 *         "algorithm"
	 */
	public String getTransformation() {
		deriveTransformation();
		return m_transformation;
	}

	/**
	 * 
	 * @return A transformation is of the form: "algorithm/mode/padding" or
	 *         "algorithm"
	 */
	private String deriveTransformation() {
		if (m_transformation == null) {
			UnSyncStringBuffer buffer = new UnSyncStringBuffer();
			if (m_algorithm != null && m_algorithm.length() > 0) {
				buffer.append(m_algorithm);
			}
			if (m_mode != null && m_mode.length() > 0) {
				buffer.append("/");
				buffer.append(m_mode);
			}
			if (m_padding != null && m_padding.length() > 0) {
				buffer.append("/");
				buffer.append(m_padding);
			}
			m_transformation = buffer.toString();
		}
		return m_transformation;
	}

	/**
	 * 
	 * @return The default secret key is generated based on the default
	 *         algorithm DESede and uses the internal key
	 */
	private static SecretKey deriveTheDefaultSecretKey() {
		byte[] byteArr = Base64.decode(ENC_DEC_KEY);

		DESedeKeySpec keyspec = null;
		SecretKeyFactory kFac = null;
		SecretKey key = null;
		try {
			keyspec = new DESedeKeySpec(byteArr);
			kFac = SecretKeyFactory.getInstance(S_DEFAULT_ALGORITHM);
			key = kFac.generateSecret(keyspec);
		} catch (InvalidKeyException e) {
			//log and ignore
			log.fatal("Error invalid key", e);
		} catch (NoSuchAlgorithmException e) {
			log.fatal("Error algoritm", e);
		} catch (InvalidKeySpecException e) {
			log.fatal("Error invalid key spec", e);
		}

		return key;
	}

	/**
	 * @return if using the default implementation return the default salt, if
	 *         not return the salt the user gave us.
	 */
	public byte[] getSalt() {
		return m_salt;
	}
}