package com.example.test1.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JiNianRiUtils {

    private static String lianAiDay = null;
    private static String birthdy1 = null;
    private static String birthdy2 = null;

    static {
        try {
            lianAiDay = TxtTest.readTxt().getString("lianAiDate");
            birthdy1 = TxtTest.readTxt().getString("birthdy1");
            birthdy2 = TxtTest.readTxt().getString("birthdy2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getLianAi(){
        return calculationLianAi(lianAiDay);
    }
    public static int getBirthday_Jo(){
        try {
            //这里输入生日
            return calculationBirthday(birthdy1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int getBirthday_Hui(){
        try {
            //这里输入生日
            return calculationBirthday(birthdy2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 计算生日天数
    public static int calculationBirthday(String clidate) throws ParseException {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cToday = Calendar.getInstance(); // 存今天
        Calendar cBirth = Calendar.getInstance(); // 存生日
        cBirth.setTime(myFormatter.parse(clidate)); // 设置生日
        cBirth.set(Calendar.YEAR, cToday.get(Calendar.YEAR)); // 修改为本年
        int days;
        if (cBirth.get(Calendar.DAY_OF_YEAR) < cToday.get(Calendar.DAY_OF_YEAR)) {
            // 生日已经过了，要算明年的了
            days = cToday.getActualMaximum(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
            days += cBirth.get(Calendar.DAY_OF_YEAR);
        } else {
            // 生日还没过
            days = cBirth.get(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
        }
        // 输出结果
        if (days == 0) {
            return 0;
        } else {
            return days;
        }
    }

    // 计算天数
    public static int calculationLianAi(String date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int day = 0;
        try {
            long time = System.currentTimeMillis() - simpleDateFormat.parse(date).getTime();
            day = (int) (time / 86400000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

}
