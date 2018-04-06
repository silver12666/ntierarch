package com.silver.controller;

import com.silver.service.UserService;
import com.silver.web.ViewModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.silver.util.ApplicationConstants.TOKEN;
import static com.silver.web.Methods.GET;

public class LoginUserController implements Controller{

    private UserService userService;

    public LoginUserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ViewModel process(HttpServletRequest req, HttpServletResponse resp) {
        ViewModel vm = new ViewModel("login");
        if (req.getMethod().equals(GET.toString())) {
            return vm;
        }
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String token = userService.checkUser(email, password).orElse("unauthorized");

        resp.addCookie(new Cookie(TOKEN, token));
        return vm;
    }
}
