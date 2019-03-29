package com.example.meetdoctor.utils;

import java.util.Calendar;

/**
 * 时间日期操作类(内容不全待补充)
 * 避免使用全局静态Calendar变量,因为会有set的问题出现
 */
public class DateUtils {
    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1;
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取某年某月的天数
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
