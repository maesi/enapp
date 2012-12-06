package org.maesi.hslu.enapp.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Helper {

	public static String md5(String aPassword) {
		MessageDigest _messageDigest;
		try {
			_messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return aPassword;
		}
		byte[] hash = _messageDigest.digest(aPassword.getBytes());

		StringBuffer _stringBuffer = new StringBuffer();
		for (int i = 0; i < hash.length; ++i) {
			_stringBuffer.append(Integer.toHexString((hash[i] & 0xFF) | 0x100)
					.toLowerCase().substring(1, 3));
		}
		return _stringBuffer.toString();
	}
	
}
