package com.javaacademy.services;

import com.javaacademy.entities.User;
import com.javaacademy.repositories.UserRepository;

import java.sql.Connection;
import java.util.Scanner;

/**
 * @author Amela Karasalihovic
 */
public class UserService {

    public void register(Scanner scanner, Connection connection) {
        boolean value = true;
        while (value) {
            UserRepository userRepository = new UserRepository();
            System.out.println("Please enter first name: ");
            String firstName = scanner.next();
            System.out.println("Please enter last name: ");
            String lastName = scanner.next();
            System.out.println("Please enter email: ");
            String email = scanner.next();
            System.out.println("Please enter password: ");
            String password = scanner.next();
            System.out.println("Please enter your budget: ");
            Double totalBalance = scanner.nextDouble();

            User checkUser = userRepository.findUserByEmail(connection, email);
            if (checkUser == null) {
                int id = getIdAndCheckIfItExists(connection);
                User user = new User(id, firstName, lastName, email, password, null, totalBalance);

                User savedUser = userRepository.registerUser(connection, user);
                if (savedUser == null) {
                    System.out.println("Please try again: ");
                    value = true;
                } else {
                    value = false;
                }
            } else {
                System.out.println("Please try again: ");
                value = true;
            }
        }
    }

    public boolean login(Scanner scanner, Connection connection) {
        UserRepository userRepository = new UserRepository();

        System.out.println("Please enter email: ");
        String email = scanner.next();
        System.out.println("Please enter password: ");
        String password = scanner.next();
        User user = userRepository.findUserByEmailAndPassword(connection, email, password);

        if(user == null) {
            System.out.println("You can't log into app!");
            return false;
        }
        return true;
    }

    private Integer getIdAndCheckIfItExists(Connection connection) {
        UserRepository userRepository = new UserRepository();
        while (true) {
            int id = (int) (Math.random() * 999 + 1);
            User user = userRepository.findUserById(connection, id);
            if (user == null) {
                return id;
            }
        }
    }
}
