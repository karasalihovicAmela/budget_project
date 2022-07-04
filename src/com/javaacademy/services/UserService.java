package com.javaacademy.services;

import com.javaacademy.entities.User;
import com.javaacademy.repositories.UserRepository;
import com.javaacademy.utils.IdGenerator;

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
            String password = "";
            boolean pass = false;
            while (pass != true) {
                System.out.println("Please enter password: (Password needs to contain min 8 characters, at least 1 upper case, 1 number and 1 symbol)");
                password = scanner.next();
                pass = validatePassword(password);
            }

            String passwordConfirm = "";
            boolean passConfirm = false;
            while (passConfirm != true) {
                System.out.println("Please confirm password: ");
                passwordConfirm = scanner.next();
                passConfirm = passwordConfirm.equals(password);
            }

            User checkUser = userRepository.findUserByEmail(connection, email);
            if (checkUser == null) {
                int id = IdGenerator.getIdAndCheckIfItExists(connection, userRepository);
                User user = new User(id, firstName, lastName, email, password, false);

                User savedUser = userRepository.insert(connection, user);
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

    public User login(Scanner scanner, Connection connection) {
        UserRepository userRepository = new UserRepository();

        System.out.println("Please enter email: ");
        String email = scanner.next();
        System.out.println("Please enter password: ");
        String password = scanner.next();

        User user = userRepository.findUserByEmailAndPassword(connection, email, password);
        if (user == null) {
            System.out.println("You can't log into app!");
            return null;
        } else {
            user.setLoggedIn(true);
            userRepository.update(connection, user);
        }

        return user;
    }

    public void logout(Connection connection) {
        UserRepository userRepository = new UserRepository();
        User user = userRepository.getLoggedInUser(connection);
        user.setLoggedIn(false);
        userRepository.update(connection, user);
    }

    public User getLoggedInUser(Connection connection) {
        return new UserRepository().getLoggedInUser(connection);
    }

    private boolean validatePassword(String password) {
        int count = 0;
        int countBigLetter = 0;
        int countNumbers = 0;
        int countChar = 0;
        if (password.length() >= 8) {
            count++;
        }

        for (char letter : password.toCharArray()) {
            if (Character.isUpperCase(letter)) {
                countBigLetter++;
            }
            if (Character.isDigit(letter)) {
                countNumbers++;
            }
            if (!Character.isDigit(letter) && !Character.isLetter(letter)) {
                countChar++;
            }
        }

        if (countBigLetter >= 1) {
            count++;
        }
        if (countNumbers >= 1) {
            count++;
        }
        if (countChar >= 1) {
            count++;
        }

        if (count >= 4) {
            return true;
        }
        return false;
    }
}
