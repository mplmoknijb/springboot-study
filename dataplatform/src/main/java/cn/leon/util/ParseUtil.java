package cn.leon.util;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/16 16:00
 */
@Component
public class ParseUtil {
    public String parseType(String clazzType) {
        switch (clazzType) {
            case "java.util.Date":
                return "date";
            case "java.lang.Integer":
                return "integer";
            case "java.lang.Boolean":
                return "boolean";
            case "java.lang.String":
                return "text";
            default:
                return "";
        }
    }

    public static SimpleDateFormat datePattern(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
