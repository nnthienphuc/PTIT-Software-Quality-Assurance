package com.nnthienphuc.intelligentbookstoreecommercewebsite.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class CookieService {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    public Cookie create(String name, String value, int days) {
        String encodedValue = Base64.getEncoder().encodeToString(value.getBytes());
        Cookie cookie = new Cookie(name, encodedValue);
        cookie.setMaxAge(days * 24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return cookie;
    }

    public Cookie read(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(name)) {
                    String decodedValue = new String(Base64.getDecoder().decode(cookie.getValue()));
                    cookie.setValue(decodedValue);
                    return cookie;
                }
            }
        }
        return null;
    }

    public void delete(String name) {
        this.create(name, "", 0);
    }
}
