package com.jianwu.assistant.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * 加密类
 * Created by tookbra on 2016/3/12.
 */
public class MD5Util {
	private static SecureRandom random = new SecureRandom();


	/**
	 * 生成随机的Byte[]作为salt.
	 * @return byte数组的大小
	 */
	public static String generateSalt() {
		byte[] bytes = new byte[18];
		random.nextBytes(bytes);
		bytes = Base64.encodeBase64(bytes);
		return new String(bytes, Charset.forName("UTF-8")).substring(0, 18);
	}

	/**
	 * md5加密
	 * @param passWord 密码
	 * @param salt 盐
	 * @return md5
	 */
	public static String digest(String passWord, String salt) {
		if (StringUtils.isBlank(passWord)) {
			return null;
		}
		return digest(passWord + salt);
	}

	/**
	 * md5加密
	 * @param value
	 *              需加密字符串
	 * @return md5
	 */
	public static String digest(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return DigestUtils.md5DigestAsHex(value.getBytes());
	}

	/**
	 * 验证密码是否正确
	 * @param rawPass 加密前密码
	 * @param encPass 加密后密码
	 * @param salt 盐
	 * @return
	 */
	public static boolean verify(String rawPass, String encPass, String salt) {
		if (StringUtils.isBlank(rawPass)) {
			return false;
		}
		System.out.println(rawPass + salt);
		System.out.println(encPass);
		System.out.println(MD5Util.digest(rawPass + salt));
		return MD5Util.digest(rawPass + salt).equals(encPass);
	}
}
