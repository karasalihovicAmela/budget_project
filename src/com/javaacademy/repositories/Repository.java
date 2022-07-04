package com.javaacademy.repositories;

import java.sql.Connection;

public interface Repository<Object> {

    void createTable(Connection connection);
    Object insert(Connection connection, Object object);
    void update(Connection connection, Object object);
    Object findById(Connection connection, Integer id);

}
