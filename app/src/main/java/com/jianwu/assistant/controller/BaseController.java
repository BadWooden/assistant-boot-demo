package com.jianwu.assistant.controller;


import com.jianwu.assistant.comm.Const;
import com.jianwu.assistant.domain.AssistantUser;
import com.jianwu.assistant.domain.ResultResponse;
import com.jianwu.assistant.utils.Des3EncryptionUtil;
import com.jianwu.assistant.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }


    protected AssistantUser getUser() {

        return (AssistantUser) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
    }

    protected Long getSignId() {
        try {
            String token = getRequest().getHeader("token");
            return Long.valueOf(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    protected void removeUser() {
        getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
    }

    protected Long getUserId() {
        Long id = null;
        AssistantUser user = getUser();
        if (user != null) {
            id = user.getId();
        }
        return id;
    }

    protected ResultResponse checkLogin() {
        //      return ResultResponse.success("");
        Long id = getSignId();
        if (id == null) {
            return ResultResponse.error("500", "请登录后在请求！");
        } else {
            return ResultResponse.success(id);
        }
    }

    protected String getUserName() {
        String userName = "";
        AssistantUser user = getUser();
        if (user != null) {
            userName = user.getName();
        }
        return userName;
    }

    protected String getUserIp() {
        String value = getRequest().getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(value) && !"unknown".equalsIgnoreCase(value)) {
            return value;
        } else {
            return getRequest().getRemoteAddr();
        }
    }

    protected String getPwd(String salt, String password) {
        try {
            String pwd = MD5Util.digest(password, salt);
            return pwd;
        } catch (Exception e) {
            logger.error("密码加密异常：", e);
        }
        return null;
    }

    protected String cookieSign(String value) {
        try {
            value = value + Const.PASSWORD_KEY;
            String sign = Des3EncryptionUtil.encode(Const.DES3_KEY, value);
            return sign;
        } catch (Exception e) {
            logger.error("cookie签名异常：", e);
        }
        return null;
    }

}
