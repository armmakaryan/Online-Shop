package am.smartCode.jdbc.service.user.impl;

import am.smartCode.jdbc.exception.UserNotFoundException;
import am.smartCode.jdbc.exception.ValidationException;
import am.smartCode.jdbc.model.User;
import am.smartCode.jdbc.repository.user.UserRepository;
import am.smartCode.jdbc.service.user.UserService;
import am.smartCode.jdbc.util.constants.Message;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void register(User user) throws Exception {

        Connection connection = userRepository.getConnection();
        connection.setAutoCommit(false);
        try {
            Validation(user);
            userRepository.create(user);
            System.out.println(user.getName() + " you are registered successfully");
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new RuntimeException(Message.REGISTRATION_FAILED + ", " + e.getMessage());
        }
    }

    @Override
    public void login(String email, String password) throws Exception {
        Validation(email, password);
        User user = userRepository.get(email);
        if (user == null) {
            throw new UserNotFoundException(Message.USER_NOT_FOUND);
        }
        if (!Objects.equals(user.getPassword(), password)) {
            throw new ValidationException(Message.INVALID_PASSWORD);
        }
        System.out.println(user.getName() + Message.AUTHENTICATION_SUCCESS);
    }

    @Override
    public void changePassword(String username, String newPassword, String repeatPassword) {

        if (!newPassword.equals(repeatPassword)) {
            throw new ValidationException("Passwords not matches");
        }
        var usersByEmail = userRepository.findUsersByEmail("email");
        if (usersByEmail == null)
            throw new UserNotFoundException(Message.USER_NOT_FOUND);

        usersByEmail.setPassword(newPassword);
        try {
            userRepository.update(usersByEmail);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(String email, String password) throws Exception {
        Validation(email, password);
        Connection connection = userRepository.getConnection();
        if (!userRepository.get(email).getPassword().equals(password)) {
            throw new ValidationException(Message.INVALID_PASSWORD);
        }
        connection.setAutoCommit(false);
        try {
            userRepository.delete(email);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new RuntimeException("Deleting Failes" + ", " + e.getMessage());
        }
    }


    private void Validation(User user) throws Exception {
        if (user.getEmail() == null || user.getEmail().length() == 0) {
            throw new ValidationException(Message.BLANK_EMAIL);
        }
        if (!user.getEmail().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
                + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
            throw new Exception(Message.INVALID_EMAIL);
        }
        if (user.getPassword() == null) {
            throw new ValidationException(Message.BLANK_PASSWORD);
        }
        if (user.getPassword().length() < 8) {
            throw new ValidationException(Message.INVALID_LENGTH_OF_PASSWORD);
        }
    }

    private void Validation(String email, String password) throws Exception {
        if (email == null || password == null)
            throw new ValidationException(Message.EMAIL_OR_PASSWORD_IS_NULL);
    }
}
