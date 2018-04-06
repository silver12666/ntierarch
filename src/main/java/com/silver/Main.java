package com.silver;

import com.silver.controller.CreateUserController;
import com.silver.dao.UserDaoImpl;
import com.silver.service.UserServiceImpl;

import java.util.function.Function;

public class Main {

    public static void main(String[] args) {

        CreateUserController controller2 = (CreateUserController) Function.identity()
                .compose(CreateUserController::new)
                .compose(UserServiceImpl::new)
                .compose(UserDaoImpl::new)
                .apply(Factory.getConnection());

        System.out.println("");
    }
}
