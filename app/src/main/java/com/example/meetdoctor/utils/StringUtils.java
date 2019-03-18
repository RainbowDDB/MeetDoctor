package com.example.meetdoctor.utils;

public class StringUtils {

    // 判断一个字符串是否都为数字
    public static boolean isNumber(String strNum) {
        return strNum.matches("[0-9]+");
    }

    /**
     * 格式化日期
     */
    public static String getFormatDate(int year, int month, int day) {
        String Month, Day;
        if (month < 10)
            Month = "0" + month;
        else
            Month = month + "";
        if (day < 10)
            Day = "0" + day;
        else
            Day = day + "";
        return year + "-" + Month + "-" + Day;
    }

    /**
     * 将字符串格式化成int数组
     * 如1999-03-04 --> {1999,3,4}
     */
    public static int[] spilt2num(String s) {
        if (s != null && !s.equals("")) {
            String[] strs = s.split("-");
            int[] result = new int[3];
            for (int i = 0; i < strs.length; i++) {
                result[i] = Integer.parseInt(strs[i]);
            }
            return result;
        } else {
            throw new RuntimeException("string to be partitioned is null or empty!");
        }

    }
}
