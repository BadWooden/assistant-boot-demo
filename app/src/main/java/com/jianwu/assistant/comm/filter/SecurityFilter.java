package com.jianwu.assistant.comm.filter;


import com.jianwu.assistant.comm.Const;
import com.jianwu.assistant.domain.AssistantUser;
import com.jianwu.assistant.manager.AssistantUserManager;


import com.jianwu.assistant.utils.Des3EncryptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SecurityFilter implements Filter {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Set<String> GreenUrlSet = new HashSet<String>();

    @Autowired
    private AssistantUserManager userManager;

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        GreenUrlSet.add("/assistant/login");
    }

    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) srequest;
        String uri = request.getRequestURI();
        if (request.getSession().getAttribute(Const.LOGIN_SESSION_KEY) == null) {
            check(srequest, sresponse, filterChain, request, uri);
        } else {
            try {
                filterChain.doFilter(srequest, sresponse);
            } catch (Exception e) {
                check(srequest, sresponse, filterChain, request, uri);
            }
        }
    }

    private void check(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain, HttpServletRequest request, String uri) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        if (containsSuffix(uri) || GreenUrlSet.contains(uri) || containsKey(uri)) {
            logger.debug("don't check  url , " + request.getRequestURI());
            filterChain.doFilter(srequest, sresponse);
            return;
        } else if (cookies != null) {
            boolean flag = true;
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(Const.LOGIN_SESSION_KEY)) {
                    if (StringUtils.isNotBlank(cookie.getValue())) {
                        flag = false;
                    } else {
                        break;
                    }
                    String value = getUserId(cookie.getValue());
                    if (userManager == null) {
                        try {
                            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                            userManager = factory.getBean(AssistantUserManager.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (userManager != null && StringUtils.isNotBlank(value)) {
                        AssistantUser yxdUser = userManager.getById(value);
                        if (yxdUser != null) {
                            logger.info("userId :" + yxdUser.getId());
                            request.getSession().setAttribute(Const.LOGIN_SESSION_KEY, yxdUser);
                            filterChain.doFilter(srequest, sresponse);
                            return;
                        }
                    }
                }
            }
            if (flag) {
                HttpServletResponse response = (HttpServletResponse) sresponse;
                response.sendRedirect("/#/login");
            }
        } else {
            //跳转到登陆页面

            HttpServletResponse response = (HttpServletResponse) sresponse;
            response.sendRedirect("/#/login");

        }
    }


    /**
     * @param url
     * @return
     * @author neo
     * @date 2016-5-4
     */
    private boolean containsSuffix(String url) {
        if (url.endsWith(".js")
                || url.endsWith(".css")
                || url.endsWith(".jpg")
                || url.endsWith(".gif")
                || url.endsWith(".png")
                || url.endsWith(".html")
                || url.endsWith(".eot")
                || url.endsWith(".svg")
                || url.endsWith(".ttf")
                || url.endsWith(".woff")
                || url.endsWith(".ico")
                || url.endsWith(".woff2")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param url
     * @return
     * @author neo
     * @date 2016-5-4
     */
    private boolean containsKey(String url) {
        if (url.startsWith("/swagger")
                || url.startsWith("/v2/api-docs")
                || url.startsWith("/webjars")
                || url.startsWith("/configuration")
                ) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    public String codeToString(String str) {
        String strString = str;
        try {
            byte tempB[] = strString.getBytes("ISO-8859-1");
            strString = new String(tempB);
            return strString;
        } catch (Exception e) {
            return strString;
        }
    }


    public String getUserId(String value) {
        try {
            String userId = Des3EncryptionUtil.decode(Const.DES3_KEY, value);
            userId = userId.substring(0, userId.indexOf(Const.PASSWORD_KEY));
            return userId;
        } catch (Exception e) {
            logger.error("解析cookie异常：", e);
        }
        return null;
    }
}