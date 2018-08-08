package cn.leon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {


    public static String now() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String now = simpleDateFormat.format(date);
        return now;
    }
}
