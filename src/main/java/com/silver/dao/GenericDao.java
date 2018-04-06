package com.silver.dao;

public interface GenericDao<T> {

    T create(T t);

    T findById(Long id) throws Exception;

    T update(T t);

    T delete(T t);

    T findByEmail(String email);

}
