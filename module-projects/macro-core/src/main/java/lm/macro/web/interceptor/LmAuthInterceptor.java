package lm.macro.web.interceptor;

import lm.macro.security.LmUserDetailsHelper;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LmAuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = super.preHandle(request, response, handler);

        request.setAttribute("user", LmUserDetailsHelper.getUser());

        return result;
    }
}
