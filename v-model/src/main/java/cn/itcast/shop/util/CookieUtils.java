package cn.itcast.shop.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public final class CookieUtils {

    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期 以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String domain, String path, String name,
                                 String value, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }


    /**
     * 根据cookie名称读取cookie
     *
     * @param request
     * @param cookieName1,cookieName2
     * @return map<cookieName,cookieValue>
     */

    public static Map<String, String> readCookie(HttpServletRequest request, String... cookieNames) {
        Map<String, String> cookieMap = new HashMap<String, String>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                String cookieValue = cookie.getValue();
                for (int i = 0; i < cookieNames.length; i++) {
                    if (cookieNames[i].equals(cookieName)) {
                        cookieMap.put(cookieName, cookieValue);
                    }
                }
            }
        }
        return cookieMap;

    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;

        for (int i = 0; i < cookieList.length; i++) {
            if (cookieList[i].getName().equals(cookieName)) {

                retValue = cookieList[i].getValue();

                break;
            }
        }
        return retValue;
    }


    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

}
