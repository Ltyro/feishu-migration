package com.lark.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Value("${lark.url}")
    private String url;

    public LoginInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (StringUtils.equalsAny(request.getRequestURI(), new CharSequence[]{"/feishu-login", "/begin", "/feishu-new", "/feishu-old", "/feishu-session-check"})) {
            return true;
        } else if (session.getAttribute("old_user_open_id") == null && session.getAttribute("new_user_open_id") == null) {
            logger.info("未登陆重定向至/begin");
            if (StringUtils.equalsAny(request.getRequestURI(), new CharSequence[]{"/userStatus", "/feishu-notification"})) {
                response.getWriter().write("notLogin");
            } else {
                response.sendRedirect(this.url + "/begin");
            }

            return false;
        } else {
            return true;
        }
    }
}
