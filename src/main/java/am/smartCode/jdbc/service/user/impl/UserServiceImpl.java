package am.smartCode.jdbc.service.user.impl;

import am.smartCode.jdbc.exception.UserNotFoundException;
import am.smartCode.jdbc.exception.ValidationException;
import am.smartCode.jdbc.model.User;
import am.smartCode.jdbc.repository.user.UserRepository;
import am.smartCode.jdbc.service.user.UserService;
import am.smartCode.jdbc.util.constants.Message;
import am.smartCode.jdbc.util.encoder.MD5Encoder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(String name, String lastname, String email, String password, int age, long balance) throws SQLException {

        Connection connection = userRepository.getConnection();
        connection.setAutoCommit(false);

        Validation(email, password, age, balance);
        if (userRepository.get(email) != null){
            throw new ValidationException(Message.EMAIL_IS_NOT_AVAILABLE);
        }
        try {  User user = new User();
            user.setName(name);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setPassword(MD5Encoder.encode(password));
            user.setAge(age);
            user.setBalance(balance);
            userRepository.create(user);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new RuntimeException(Message.REGISTRATION_FAILED + ", " + e.getMessage());
        }


    }

    @Override
    public void login(String email, String password) throws SQLException {
        Validation(email, password);
        User user = userRepository.get(email);
        if (user == null) {
            throw new UserNotFoundException(Message.USER_NOT_FOUND);
        }
        if (!Objects.equals(user.getPassword(), MD5Encoder.encode(password))) {
            throw new ValidationException(Message.INVALID_PASSWORD);
        }
    }

    @Override
    public void deleteUser(String email, String password) throws SQLException {
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

    @Override
    public void updateUser(String email, String newPassword, String repeatPassword) throws SQLException {
        if (email == null || email.isEmpty()) {
            throw new ValidationException(Message.USER_NOT_FOUND);
        }
        if (!Objects.equals(newPassword, repeatPassword)) {
            throw new ValidationException(Message.PASSWORD_NOT_MATCHES);
        }
        if (newPassword == null || newPassword.isEmpty() || repeatPassword == null || repeatPassword.isEmpty()) {
            throw new ValidationException(Message.BLANK_PASSWORD);
        }
        Connection connection = userRepository.getConnection();
        connection.setAutoCommit(false);
        try {
            userRepository.updateByEmail(email, MD5Encoder.encode(newPassword));
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new RuntimeException("Changing password failed" + ", " + e.getMessage());
        }
    }


    private void Validation(String email, String password) {
        if (email == null || email.isEmpty()) {
            throw new ValidationException(Message.BLANK_EMAIL);
        }
        if (password == null || password.isEmpty()) {
            throw new ValidationException(Message.BLANK_PASSWORD);
        }
        if (password.length() < 8) {
            throw new ValidationException(Message.INVALID_LENGTH_OF_PASSWORD);
        }
        if (!Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches()) {
            throw new ValidationException(Message.INVALID_EMAIL);
        }
    }

    private void Validation(String email, String password, int age, long balance) {
        Validation(email, password);
        if (age <= 0) {
            throw new ValidationException(Message.INVALID_AGE);
        }
        if (balance < 0) {
            throw new ValidationException(Message.INVALID_BALANCE);
        }


    }

}