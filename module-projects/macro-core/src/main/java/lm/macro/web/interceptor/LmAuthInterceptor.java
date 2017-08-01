package lm.macro.web.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LmAuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = super.preHandle(request, response, handler);

//        URI u = new URI(request.getRequestURL().toString());
//
//        if (!u.getPath().startsWith("/login")) {
//            LmUser user = (LmUser) request.getSession().getAttribute("user");
//
//            if (user == null || !user.isLoggedIn()) {
//                response.sendRedirect("/login");
//                return false;
//            }
//        }

        return result;
    }
}
