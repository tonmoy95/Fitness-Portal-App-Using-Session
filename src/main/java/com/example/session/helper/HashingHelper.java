package com.example.session.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This is a hashing algorithm where the password is hashed or encrypted using a
 * salt value which is then stored in database.
 * 
 * @author Tonmoy
 *
 */

@Component
public class HashingHelper {

	private static final Logger logger = LoggerFactory.getLogger(HashingHelper.class);

	/**
	 * Hashes the original password that the user provides during registration.
	 * 
	 * @param passwordToHash It is the password which is hashed.
	 * @param salt           It is the salt value used in hashing to generate the
	 *                       hashed password.
	 * @return The hashed password is returned as a string.
	 * 
	 */
	public String getSecurePassword(String passwordToHash, byte[] salt) {
		try {
			// Get a SHA-512 MessageDigest
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			// Add the salt to the message digest
			md.update(salt);
			// Get the hash's bytes
			byte[] bytes = md.digest(passwordToHash.getBytes());
			md.reset();
			BigInteger bi = new BigInteger(1, bytes);
			String hex = bi.toString(16);
			int paddingLength = (bytes.length * 2) - hex.length();
			if (paddingLength > 0) {
				return String.format("%0" + paddingLength + "d", 0) + hex;
			}
			return hex;
		} catch (NoSuchAlgorithmException e) {
			logger.info("Error In HashningHelper Class : " + e);
			return null;
		}
	}

	/**
	 * The salt value is randomly generated which is used in password for hashing.
	 * 
	 * @return The salt value is returned as a byte array.
	 * 
	 */
	// Add salt
	public byte[] getSalt() throws NoSuchProviderException {
		try {
			// Always use a SecureRandom generator
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			// Create array for salt
			byte[] salt = new byte[16];
			// Get a random salt
			sr.nextBytes(salt);
			// return salt
			return salt;

		} catch (NoSuchAlgorithmException e) {
			logger.error("Error In getSalt Method : " + e);
			return null;
		}
	}

	/**
	 * The fromHex method is used in conversion of string to byte array.
	 * 
	 * @param hex It is the string which needs to be converted to byte array.
	 * @return The string is returned as a byte array.
	 */
	public byte[] fromHex(String hex) {
		byte[] val = new byte[hex.length() / 2];
		for (int i = 0; i < val.length; i++) {
			int index = i * 2;
			int j = Integer.parseInt(hex.substring(index, index + 2), 16);
			val[i] = (byte) j;
		}
		return val;
	}

	/**
	 * The toHex method is used in conversion of byte array to string.
	 * 
	 * @param array It is the byte array which needs to be converted to string.s
	 * @return The byte array is returned as a string.
	 */
	public String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

}
