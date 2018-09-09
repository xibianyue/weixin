package com.wx.util;

import java.util.Arrays;

public class CheckUtil {
    private static final String TOKEN = "xibianyueToken";
    public static boolean checkSignature(String signature, String timestamp, String nonce){
        String[] arr = new String[]{TOKEN, timestamp, nonce};
        // 排序
        Arrays.sort(arr);
        // 生成字符串
        StringBuffer content = new StringBuffer();
        for (String s :
                arr) {
            content.append(s);
        }
        // SHA1加密
        String temp = SHA1.encode(content.toString());

        return temp.equals(signature);
    }

}
