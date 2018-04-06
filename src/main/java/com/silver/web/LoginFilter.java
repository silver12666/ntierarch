package com.silver.web;

import com.silver.Factory;
import com.silver.dao.UserDao;
import com.silver.dao.UserDaoImpl;
import com.silver.model.User;
import com.silver.util.ApplicationConstants;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class LoginFilter implements Filter {
    private UserDao userDao;
    private final String protectedUri = "/servlet/profile";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = new UserDaoImpl(Factory.getConnection());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (httpServletRequest.getRequestURI().equals(protectedUri) && cookies!= null) {
            String token = null;
            for (Cookie cookie : cookies) {
                String name =  cookie.getName().toUpperCase();
                if (name.equals(ApplicationConstants.TOKEN)) {
                    token = cookie.getValue();
                    User user = userDao.getUserByToken(token);
                    httpServletRequest.setAttribute("UserId", String.valueOf(user.getId()));
                }
            }
            if (token == null) {
                httpServletRequest.getRequestDispatcher("/servlet/login").forward(httpServletRequest, servletResponse);
            }
        }
        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
