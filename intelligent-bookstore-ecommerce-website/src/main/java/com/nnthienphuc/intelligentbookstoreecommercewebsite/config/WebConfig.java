package com.nnthienphuc.intelligentbookstoreecommercewebsite.config;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.interceptor.AdminAuthorizeInterceptor;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.interceptor.AuthorizeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public AuthorizeInterceptor authorizeInterceptor() {
        return new AuthorizeInterceptor();
    }

    @Bean
    public AdminAuthorizeInterceptor adminAuthorizeInterceptor() {
        return new AdminAuthorizeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Interceptor cho User
        registry.addInterceptor(authorizeInterceptor())
                .addPathPatterns("/user/cart")
                .excludePathPatterns("/user/account/login", "/user/account/register");

        // Interceptor cho Admin
        registry.addInterceptor(adminAuthorizeInterceptor())
                .addPathPatterns("/admin/book", "/admin/category", "/admin/author",
                        "/admin/promotion", "/admin/publisher", "/admin/customer",
                        "/admin/orders", "/admin/account", "/admin/dashboard", "/admin/employee") // Chặn tất cả đường dẫn của admin
                .excludePathPatterns("/admin/account/login", "/admin/account/register"); // Cho phép các đường dẫn public

    }
}