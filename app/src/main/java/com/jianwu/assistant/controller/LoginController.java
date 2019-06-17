package com.jianwu.assistant.controller;


import com.jianwu.assistant.comm.Const;
import com.jianwu.assistant.domain.AssistantUser;
import com.jianwu.assistant.domain.ResultResponse;
import com.jianwu.assistant.manager.AssistantUserManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/assistant")
public class LoginController extends BaseController {

    @Autowired
    AssistantUserManager userManager;


    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResultResponse login(AssistantUser request, HttpServletResponse response) {
        AssistantUser assistantUser = userManager.login(request);
        if (assistantUser != null) {
            Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, cookieSign(assistantUser.getId().toString()));
            cookie.setMaxAge(Const.COOKIE_TIMEOUT);
            cookie.setPath("/");
            response.addCookie(cookie);
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, assistantUser);
            return ResultResponse.success(assistantUser, "登录成功！");
        } else {
            return ResultResponse.error("500", "登录失败！");
        }
    }


    /**
     * 登出
     *
     * @return
     */
    @ApiOperation(value = "退出登录接口", notes = "退出登录接口", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)

    @RequestMapping(value = "/loginOut", method = RequestMethod.POST, produces = "application/json")
    public ResultResponse loginOut() {
        try {
            removeUser();
            return ResultResponse.success("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultResponse.error("500", "登出失败");
        }
    }

}
