package com.silver.model;

import com.silver.orm.Constraints;
import com.silver.orm.DBTable;
import com.silver.orm.SQLLong;
import com.silver.orm.SQLString;

@DBTable(name = "users")
public class User {

    @SQLLong(column = "id", constraints =
    @Constraints(primaryKey = true, allowNull = false, unique = true))
    private Long id;

    @SQLString(value = 30, column = "name")
    private String name;

    @SQLString(value = 125, column = "email")
    private String email;

    @SQLString(value = 50, column = "password")
    private String password;

    private String token;

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
