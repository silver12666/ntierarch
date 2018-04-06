package com.silver.controller;

import com.silver.web.ViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    ViewModel process(HttpServletRequest req, HttpServletResponse resp);
}
