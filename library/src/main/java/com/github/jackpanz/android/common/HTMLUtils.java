package com.github.jackpanz.android.common;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class HTMLUtils {

    public static String ENCODING = "UTF-8";

    public static String encodeURIComponent(String s) {
        return encodeURIComponent(s, ENCODING);
    }

    public static String encodeURIComponent(String s, String encoding) {
        // Encode URL
        if (s == null) {
            s = "";
        }
        try {
            s = URLEncoder.encode(s, encoding);
        } catch (Exception e) {
        }
        s = s.replaceAll("\\+", "%20");
        s = s.replaceAll("%2B", "+");
        return s;
    }

    public static String decodeURIComponent(String s) {
        return decodeURIComponent(s, ENCODING);
    }

    public static String decodeURIComponent(String s, String encoding) {
        s = s.replaceAll("%u[0-9a-fA-F]{4}", "");
        s = s.replaceAll("\\+", "%2B");
        s = s.replaceAll("%20", "+");
        try {
            s = URLDecoder.decode(s, encoding);
        } catch (Exception e) {
        }
        return s;
    }

}
