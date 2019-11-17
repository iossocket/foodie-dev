package com.iossocket.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	public static String getMD5Str(String strValue) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return Base64.encodeBase64String(md5.digest(strValue.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
