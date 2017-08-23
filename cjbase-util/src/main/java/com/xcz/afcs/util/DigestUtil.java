package com.xcz.afcs.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;


public class DigestUtil {
  
	 public static  final String  DIGEST_SPLIT="|";
	 private static  MessageDigest getDigest(String algorithm){
		 try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	 }
	 private static MessageDigest getMd5Digest() {  
         return getDigest("MD5");  
     }  
	 
	 private static MessageDigest getShaDigest(){
		 return getDigest("SHA");  
	 }
	 
	 private static MessageDigest getSha256Digest() {  
         return getDigest("SHA-256");  
     }  
	 private static MessageDigest getSha384Digest() {  
         return getDigest("SHA-384");  
     }  
	 
	 private static MessageDigest getSha512Digest() {  
         return getDigest("SHA-512");  
     }  

     public static String MD5(byte[] data) {  
    	 byte[] digestData= getMd5Digest().digest(data);  
    	 return Hex.encodeHexString(digestData);  
     }

	public static String MD5(String data) {
		if (StringUtils.isBlank(data)) {
			return "";
		}
		byte[] digestData= getMd5Digest().digest(data.getBytes());
		return Hex.encodeHexString(digestData);
	}
   

     public static String SHA(byte[] data) {  
    	 byte[] digestData=  getShaDigest().digest(data);  
    	 return Hex.encodeHexString(digestData);
     }  

     public static String SHA256(byte[] data) {  
    	 byte[] digestData= getSha256Digest().digest(data);  
    	 return Hex.encodeHexString(digestData);
     }  
   

     public static String SHA384(byte[] data) {  
    	 byte[] digestData=  getSha384Digest().digest(data);  
    	 return Hex.encodeHexString(digestData);  
     }  

     public static String SHA512(byte[] data) {  
    	 byte[] digestData=  getSha512Digest().digest(data);  
    	 return Hex.encodeHexString(digestData);  
     } 
     
     
     public static String getCombineDigest(String  agreementDigest,String dateStr,String userName,String IDNumber) {
    	String digestStr="";
 		byte[] b=null;
 		try {
 			b= (agreementDigest+DIGEST_SPLIT+ dateStr +DIGEST_SPLIT+userName+DIGEST_SPLIT+ IDNumber).getBytes("UTF-8");
 			digestStr = DigestUtil.SHA256(b);
 		} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
 		}
 		return digestStr;
 	 
     }
     
     public static String getAgreementDigest(String  plainTxt) {
     	String digestStr="";
  		byte[] b=null;
  		try {
  			b = plainTxt.getBytes("UTF-8");
  			digestStr=DigestUtil.SHA256(b);
  		} catch (UnsupportedEncodingException e) {
  			e.printStackTrace();
  		}
  		return digestStr;
      } 
     

     /**
 	 * 对字符串中的各个字符按照一定的方式进行调换位置组成新的字符串
 	 * @param str 原始字符串
 	 * @param key 调换方式
 	 * @return
 	 */
 	public static String shiftEncode(String str, int[] key) {
 		char[] chr = str.toCharArray();
 		for (int i=0;i<chr.length;i++) {
 			swap(chr, i, key[i%key.length]%chr.length);
 		}
 		return String.valueOf(chr);
 	}
 	/**
 	 * 对字符串中的各个字符按照一定的方式进行调换位置组成新的字符串
 	 * @param str 原始字符串
 	 * @param key 调换方式
 	 * @return
 	 */
 	public static String shiftEncode(String str, String key) {
 		return shiftEncode(str, toIntArray(key));
 	}

 	/**
 	 * 对已经通过一定方式调换位置的字符进行还原，还原成原始字符串
 	 * @param str 调换后的字符串
 	 * @param key 调换方式
 	 * @return
 	 */
 	public static String shiftDecode(String str, int[] key) {
 		char[] chr = str.toCharArray();
 		for (int i=chr.length-1;i>=0;i--) {
 			swap(chr, i, key[i%key.length]%chr.length);
 		}
 		return String.valueOf(chr);
 	}
 	/**
 	 * 对已经通过一定方式调换位置的字符进行还原，还原成原始字符串
 	 * @param str 调换后的字符串
 	 * @param key 调换方式
 	 * @return
 	 */
 	public static String shiftDecode(String str, String key) {
 		return shiftDecode(str, toIntArray(key));
 	}
 	/** 
 	 * 字符串转的int数组
 	 */
 	public static int[] toIntArray(String str) {
 		int[] arr = new int[str.length()];
 		for (int i=0;i<str.length();i++) {
 			arr[i] = str.charAt(i);
 		}
 		return arr;
 	}
 	/**
 	 * 交换字符数组中两个元素的位置
 	 * @param chr
 	 * @param x
 	 * @param y
 	 */
 	public static void swap(char[] chr, int x, int y) {
 		if (x < chr.length && y < chr.length && x != y) {
 			char e = chr[x];
 			chr[x] = chr[y];
 			chr[y] = e;
 		}
 	}
 	
	public static void main(String[] args) {
//		int[] y = new int[]{2,0,1,5,12,3,13,31,48};
		String key = "鼎有财2016";
		String origin = "1234567890abcdefghijklmnopq";
		String encoded = shiftEncode(origin, key);
		String decoded = shiftDecode(encoded, key);
		System.out.println(origin);
		System.out.println(encoded);
		System.out.println(decoded);
	}
}
