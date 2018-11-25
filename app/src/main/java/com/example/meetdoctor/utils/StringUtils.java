package com.example.meetdoctor.utils;

public class StringUtils {

    // 判断一个字符串是否都为数字
    public static boolean isNumber(String strNum) {
        return strNum.matches("[0-9]+");
    }
}
