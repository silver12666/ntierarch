package com.silver.controller;


import com.silver.model.User;
import com.silver.service.UserService;
import com.silver.web.ViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUserController implements Controller{

    private UserService userService;

    public CreateUserController(UserService userService) {
        this.userService = userService;
    }

    public User create(User user) {
        return userService.create(user);
    }

    @Override
    public ViewModel process(HttpServletRequest req, HttpServletResponse resp) {
        return null;
    }
}
