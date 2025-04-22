package com.nnthienphuc.intelligentbookstoreecommercewebsite.interceptor;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Staff;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

public class AdminAuthorizeInterceptor implements HandlerInterceptor {
    private static final List<String> ADMIN_ONLY_PATHS = List.of("/admin/dashboard", "/admin/employee");
    private static final List<String> STAFF_ALLOWED_PATHS = List.of(
            "/admin/book", "/admin/category", "/admin/author",
            "/admin/promotion", "/admin/publisher", "/admin/customer",
            "/admin/orders", "/admin/account"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        String cpath = request.getContextPath();
        String url = request.getRequestURI().replaceFirst(cpath, "");

        // Nếu chưa đăng nhập
        if (staff == null) {
            session.setAttribute("AdminBackUrl", url);
            response.sendRedirect(cpath + "/admin/account/login");
            return false;
        }

        // Kiểm tra quyền
        String roleId = staff.getRole().getRoleId();
        if ("admin".equalsIgnoreCase(roleId)) {
            return true; // Admin có quyền truy cập tất cả
        } else if ("staff".equalsIgnoreCase(roleId)) {
            if (STAFF_ALLOWED_PATHS.contains(url)) {
                return true; // Staff chỉ được truy cập các URL cho phép
            } else if (ADMIN_ONLY_PATHS.contains(url)) {
                response.sendRedirect(cpath + "/admin/account/unauthorized");
                return false; // Staff không được truy cập các URL của admin
            }
        }

        // Nếu không phù hợp, trả về trang lỗi
        response.sendRedirect(cpath + "/admin/account/unauthorized");
        return false;
    }
}
