package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * create by YuWen
 */
@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = "mmall.com";
    private final static String COOKIE_NAME= "mmall_login_token";


    /**
     * X:domain="happymmall."
     *
     * a 站点:A,happymmall.com            cookie:domain=A.happymmall.com; path="/"
     * b 站点:B.happymmall.com            cookie:domain=B.happymmall.com; path="/"
     * c 站点:A.happymmall.com/test/cc    cookie:domain=A.happymmall.com; path="/test/cc"
     * d 站点:A.happymmall.com/test/dd    cookie:domain=A.happymmall.com; path="/test/dd"
     * e 站点:A.happymmall.com/test       cookie:domain=A.happymmall.com; path="/test"
     *
     * a和 b 因为二级域名不同所以cookie不能共享
     * c和 d cookie不能共享  但是c 和 d 可以共享 a 的cookie, 也可以共享e 的cookie
     *
     * @param response
     * @param token
     */

    //写入登录cookie
    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");        //代表设置为根目录
        cookie.setHttpOnly(true);

        //-1代表永久不过期,单位是秒,如果不设置此属性cookie就不会写入硬盘,而是写在内存,只在当前页面有效
        cookie.setMaxAge(60 * 60 * 24 * 365);
        log.info("write cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());
        response.addCookie(cookie);
    }

    //读取登录cookie
    public static String readLoginCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookieItem : cookies) {
                log.info("read cookieName:{} cookieValue:{}", cookieItem.getName(), cookieItem.getValue());
                if (StringUtils.equals(cookieItem.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{} cookieValue:{}", cookieItem.getName(), cookieItem.getValue());
                    return cookieItem.getValue();
                }
            }
        }
        return null;
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookieItem : cookies) {
                if (StringUtils.equals(cookieItem.getName(), COOKIE_NAME)) {
                    cookieItem.setDomain(COOKIE_DOMAIN);
                    cookieItem.setPath("/");
                    cookieItem.setMaxAge(0);    //设置成0代表删除此cookie
                    log.info("del cookieName:{} cookieValue:{}", cookieItem.getName(), cookieItem.getValue());
                    response.addCookie(cookieItem);
                    return;
                }
            }
        }
    }




}
