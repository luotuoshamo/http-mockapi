package com.wjh.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {
    public static Map<String, String> headers(HttpServletRequest request) {
        Map<String, String> map = new HashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            map.put(s, request.getHeader(s));
        }
        return map;
    }

    public static Map<String, String> params(HttpServletRequest request) {
        Map<String, String> map = new HashMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            String parameter = request.getParameter(s);
            map.put(s, parameter);
        }
        return map;
    }
}
