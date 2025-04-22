package com.nnthienphuc.intelligentbookstoreecommercewebsite.interceptor;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            session.setAttribute("back-url", request.getRequestURI());
            response.sendRedirect("account/login");
            return false;
        }
        return true;
    }
}
