package com.itheima.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    // 获取字符串的MD5哈希值
    public static String getMD5String(String input) {
        try {
            // 创建MD5加密算法实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将输入字符串转换为字节数组
            byte[] messageDigest = md.digest(input.getBytes());

            // 将字节数组转换为十六进制的字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不存在", e);
        }
    }

    // 测试方法
    public static void main(String[] args) {
        String input = "password123";
        String md5 = Md5Util.getMD5String(input);
        System.out.println("MD5哈希值: " + md5);
    }
}
