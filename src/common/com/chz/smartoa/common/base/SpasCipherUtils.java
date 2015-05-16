package com.chz.smartoa.common.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class SpasCipherUtils {
	private static String algorithm = "DESede/ECB/PKCS5Padding";
	private static String cipherKeyFileName = "spas_cipher_key.dat";
	private static String encodeMode = "UTF-8";
	private static String confiPath = "/spas/";
	private static SecretKey key = null;

	static {
		InputStream f = null;
		ObjectInputStream b = null;
		try {
			f = SpasCipherUtils.class.getResourceAsStream(cipherKeyFileName);
			System.out.println("get Resource£º" + confiPath + cipherKeyFileName+ ":" + f);
			b = new ObjectInputStream(f);
			System.out.println("ObjectInputStream£º" + confiPath+ cipherKeyFileName);
			key = (SecretKey) b.readObject();
			System.out.println("read£º" + confiPath + cipherKeyFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (b != null)
					b.close();
				if (f != null)
					f.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static Cipher getCipher(int mode) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(mode, key);
		return cipher;
	}

	public static String encrypt(String info) throws Exception {
		if (info == null)
			return null;

		byte[] encInfo = getCipher(1).doFinal(info.getBytes(encodeMode));
		BigInteger bi = new BigInteger(encInfo);

		return bi.toString();
	}
	public static String decrypt(String info) throws Exception {
		if (info == null)
			return null;

		BigInteger bi = new BigInteger(info);
		byte[] decInfo = getCipher(2).doFinal(bi.toByteArray());

		return new String(decInfo, encodeMode);
	}
	public static void main(String[] args) throws Exception {
//		String info = "abc123";
		String info = "root";

		String ecn = encrypt(info);
		System.out.println("ÃÜÎÄ:" + encrypt(info));
		System.out.println("Ã÷ÎÄ:" + decrypt(ecn));
	}
}