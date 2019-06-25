package com.yonyou.multlingual.client.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringConvertUtil {

    public static String chinese2Unicode(String str) {
        if (isContainChinese(str))
        {
            return string2Unicode(str);
        }
        return str;
    }

    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
