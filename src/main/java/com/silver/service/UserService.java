package com.silver.service;

import com.silver.model.User;

import java.util.Optional;

public interface UserService {

    User create(User user);

    Optional<String> checkUser(String email, String password);
}
