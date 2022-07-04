package com.javaacademy.utils;

import com.javaacademy.repositories.Repository;

import java.sql.Connection;

public class IdGenerator {

    public static Integer getIdAndCheckIfItExists(Connection connection, Repository<?> repository) {
        while (true) {
            int id = (int) (Math.random() * 999 + 1);
            Object o = repository.findById(connection, id);
            if (o == null) {
                return id;
            }
        }
    }


}
