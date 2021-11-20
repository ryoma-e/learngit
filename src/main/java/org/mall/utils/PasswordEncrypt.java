package org.mall.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypt {
   /**
    * 使用SHA-256加密密码
    * @param str 待加密的UTF-8编码的密码串
    * @return 长度为64的字符串(16进制数)
    */
   public static String encrypt(String str) {
      MessageDigest messageDigest;
      String encodestr = "";
      try {
         messageDigest = MessageDigest.getInstance("SHA-256");
         messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
         encodestr = byte2Hex(messageDigest.digest());
      } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
      return encodestr;
   }

   /**
    * 将byte数组转换成16进制字符表示
    */
   private static String byte2Hex(byte[] bytes) {
      StringBuilder stringBuilder = new StringBuilder();
      String temp = null;
      for (byte aByte : bytes) {
         temp = Integer.toHexString(aByte & 0xFF);
         if (temp.length() == 1) {
            // 1个字节要用2位16进制数表示, 得到一位数要补0
            stringBuilder.append("0");
         }
         stringBuilder.append(temp);
      }
      return stringBuilder.toString();
   }
}
