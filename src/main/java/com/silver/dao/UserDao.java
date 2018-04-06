package com.silver.dao;
import com.silver.model.User;

public interface UserDao extends GenericDao<User> {


    User getUserByToken(String token);
}
