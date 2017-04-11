package utils;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;



//封装RSA的工具类，方便加密解密的操作
public final class RSAUtils
{
	private static String RSA = "RSA";

	/**
	 * 随机生成RSA密钥对(默认密钥长度为1024)
	 * 
	 * @return
	 */
	public static KeyPair generateRSAKeyPair()
	{
		return generateRSAKeyPair(1024);
	}

	/**
	 * 随机生成RSA密钥对
	 * 
	 * @param keyLength
	 *            密钥长度，范围：512～2048<br>
	 *            一般1024
	 * @return
	 */
	public static KeyPair generateRSAKeyPair(int keyLength)
	{
		try
		{
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
			kpg.initialize(keyLength);
			return kpg.genKeyPair();
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用公钥加密 <br>
	 * 每次加密的字节数，不能超过密钥的长度值减去11
	 * 
	 * @param data
	 *            需加密数据的byte数据
	 * @param publicKey
	 *            公钥
	 * @return 加密后的byte型数据
	 */
	public static byte[] encryptData(byte[] data, PublicKey publicKey)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			// 编码前设定编码方式及密钥
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			// 传入编码数据并返回编码结果
			return cipher.doFinal(data);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用私钥解密
	 * 
	 * @param encryptedData
	 *            经过encryptedData()加密返回的byte数据
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey)
	{
		try
		{
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(encryptedData);
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
	 * 
	 * @param keyBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException,
			InvalidKeySpecException
	{
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 通过私钥byte[]将公钥还原，适用于RSA算法
	 * 
	 * @param keyBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException,
			InvalidKeySpecException
	{
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 使用N、e值还原公钥
	 * 
	 * @param modulus
	 * @param publicExponent
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey getPublicKey(String modulus, String publicExponent)
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 使用N、d值还原私钥
	 * 
	 * @param modulus
	 * @param privateExponent
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey getPrivateKey(String modulus, String privateExponent)
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static PublicKey loadPublicKey(String publicKeyStr) throws Exception
	{
		try
		{
			byte[] buffer = Base64Utils.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e)
		{
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e)
		{
			throw new Exception("公钥非法");
		} catch (NullPointerException e)
		{
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从字符串中加载私钥<br>
	 * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
	 * 
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception
	{
		try
		{
			byte[] buffer = Base64Utils.decode(privateKeyStr);
			// X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e)
		{
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e)
		{
			throw new Exception("私钥非法");
		} catch (NullPointerException e)
		{
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static PublicKey loadPublicKey(InputStream in) throws Exception
	{
		try
		{
			return loadPublicKey(readKey(in));
		} catch (IOException e)
		{
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e)
		{
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 * 
	 * @param in
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public static PrivateKey loadPrivateKey(InputStream in) throws Exception
	{
		try
		{
			return loadPrivateKey(readKey(in));
		} catch (IOException e)
		{
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e)
		{
			throw new Exception("私钥输入流为空");
		}
	}

	/**
	 * 读取密钥信息
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static String readKey(InputStream in) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String readLine = null;
		StringBuilder sb = new StringBuilder();
		while ((readLine = br.readLine()) != null)
		{
			if (readLine.charAt(0) == '-')
			{
				continue;
			} else
			{
				sb.append(readLine);
				sb.append('\r');
			}
		}

		return sb.toString();
	}

	/**
	 * 打印公钥信息
	 * 
	 * @param publicKey
	 */
	public static void printPublicKeyInfo(PublicKey publicKey)
	{
		RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
		System.out.println("----------RSAPublicKey----------");
		System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
		System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
		System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
		System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
	}

	public static void printPrivateKeyInfo(PrivateKey privateKey)
	{
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
		System.out.println("----------RSAPrivateKey ----------");
		System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
		System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
		System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
		System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());

	}


	//将xml字符串转为公钥
	public static PublicKey decodePublicKeyFromXml(String xml) {

		xml = xml.replaceAll("\r", "").replaceAll("\n", "");

		BigInteger modulus = new BigInteger(1, Base64Helper.decode(StringHelper

				.GetMiddleString(xml, "<Modulus>", "</Modulus>")));

		BigInteger publicExponent = new BigInteger(1,

				Base64Helper.decode(StringHelper.GetMiddleString(xml,

						"<Exponent>", "</Exponent>")));



		RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus,

				publicExponent);



		KeyFactory keyf;

		try {

			keyf = KeyFactory.getInstance("RSA");

			return keyf.generatePublic(rsaPubKey);

		} catch (Exception e) {

			return null;

		}

	}


	//将xml字符串转为私钥
	public static PrivateKey decodePrivateKeyFromXml(String xml) {

		xml = xml.replaceAll("\r", "").replaceAll("\n", "");

		BigInteger modulus = new BigInteger(1, Base64Helper.decode(StringHelper

				.GetMiddleString(xml, "<Modulus>", "</Modulus>")));

		BigInteger publicExponent = new BigInteger(1,

				Base64Helper.decode(StringHelper.GetMiddleString(xml,

						"<Exponent>", "</Exponent>")));

		BigInteger privateExponent = new BigInteger(1,

				Base64Helper.decode(StringHelper.GetMiddleString(xml, "<D>",

						"</D>")));

		BigInteger primeP = new BigInteger(1, Base64Helper.decode(StringHelper

				.GetMiddleString(xml, "<P>", "</P>")));

		BigInteger primeQ = new BigInteger(1, Base64Helper.decode(StringHelper

				.GetMiddleString(xml, "<Q>", "</Q>")));

		BigInteger primeExponentP = new BigInteger(1,

				Base64Helper.decode(StringHelper.GetMiddleString(xml, "<DP>",

						"</DP>")));

		BigInteger primeExponentQ = new BigInteger(1,

				Base64Helper.decode(StringHelper.GetMiddleString(xml, "<DQ>",

						"</DQ>")));

		BigInteger crtCoefficient = new BigInteger(1,

				Base64Helper.decode(StringHelper.GetMiddleString(xml,

						"<InverseQ>", "</InverseQ>")));



		RSAPrivateCrtKeySpec rsaPriKey = new RSAPrivateCrtKeySpec(modulus,

				publicExponent, privateExponent, primeP, primeQ,

				primeExponentP, primeExponentQ, crtCoefficient);



		KeyFactory keyf;

		try {

			keyf = KeyFactory.getInstance("RSA");

			return keyf.generatePrivate(rsaPriKey);

		} catch (Exception e) {

			return null;

		}

	}

	//将私钥转为xml
	public static String encodePrivateKeyToXml(PrivateKey key) {

		if (!RSAPrivateCrtKey.class.isInstance(key)) {

			return null;

		}

		RSAPrivateCrtKey priKey = (RSAPrivateCrtKey) key;

		StringBuilder sb = new StringBuilder();



		sb.append("<RSAKeyValue>");

		sb.append("<Modulus>")

				.append(Base64Helper.encode(priKey.getModulus().toByteArray()))

				.append("</Modulus>");

		sb.append("<Exponent>")

				.append(Base64Helper.encode(priKey.getPublicExponent()

						.toByteArray())).append("</Exponent>");

		sb.append("<P>")

				.append(Base64Helper.encode(priKey.getPrimeP().toByteArray()))

				.append("</P>");

		sb.append("<Q>")

				.append(Base64Helper.encode(priKey.getPrimeQ().toByteArray()))

				.append("</Q>");

		sb.append("<DP>")

				.append(Base64Helper.encode(priKey.getPrimeExponentP()

						.toByteArray())).append("</DP>");

		sb.append("<DQ>")

				.append(Base64Helper.encode(priKey.getPrimeExponentQ()

						.toByteArray())).append("</DQ>");

		sb.append("<InverseQ>")

				.append(Base64Helper.encode(priKey.getCrtCoefficient()

						.toByteArray())).append("</InverseQ>");

		sb.append("<D>")

				.append(Base64Helper.encode(priKey.getPrivateExponent()

						.toByteArray())).append("</D>");

		sb.append("</RSAKeyValue>");

		return sb.toString();

	}

	/**

	 * 根据指定私钥对数据进行签名(默认签名算法为"SHA1withRSA")

	 *

	 * @param data

	 *            要签名的数据

	 * @param priKey

	 *            私钥

	 * @return

	 */

	public static byte[] signData(byte[] data, PrivateKey priKey) {

		return signData(data, priKey, "SHA1withRSA");

	}



	/**

	 * 根据指定私钥和算法对数据进行签名

	 *

	 * @param data

	 *            要签名的数据

	 * @param priKey

	 *            私钥

	 * @param algorithm

	 *            签名算法

	 * @return

	 */

	public static byte[] signData(byte[] data, PrivateKey priKey,

								  String algorithm) {

		try {

			Signature signature = Signature.getInstance(algorithm);

			signature.initSign(priKey);

			signature.update(data);

			return signature.sign();

		} catch (Exception ex) {

			return null;

		}

	}



	/**

	 * 用指定的公钥进行签名验证(默认签名算法为"SHA1withRSA")

	 *

	 * @param data

	 *            数据

	 * @param sign

	 *            签名结果

	 * @param pubKey

	 *            公钥

	 * @return

	 */

	public static boolean verifySign(byte[] data, byte[] sign, PublicKey pubKey) {

		return verifySign(data, sign, pubKey, "SHA1withRSA");

	}



	/**

	 *

	 * @param data 数据

	 * @param sign 签名结果

	 * @param pubKey 公钥

	 * @param algorithm 签名算法

	 * @return

	 */

	public static boolean verifySign(byte[] data, byte[] sign,

									 PublicKey pubKey, String algorithm) {

		try {

			Signature signature = Signature.getInstance(algorithm);

			signature.initVerify(pubKey);

			signature.update(data);

			return signature.verify(sign);

		} catch (Exception ex) {

			return false;

		}

	}


}
