package am.smartCode.jdbc.service.user.impl;

import am.smartCode.jdbc.exception.UserNotFoundException;
import am.smartCode.jdbc.model.User;
import am.smartCode.jdbc.repository.user.UserRepository;
import am.smartCode.jdbc.service.user.UserService;
import am.smartCode.jdbc.util.constants.Message;
import am.smartCode.jdbc.util.encoder.MD5Encoder;
import lombok.RequiredArgsConstructor;

import javax.validation.ValidationException;
import javax.validation.constraints.*;
import java.sql.SQLException;
import java.util.Objects;

@RequiredArgsConstructor
public class UserServiceJpaImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void register(@NotNull @NotBlank @NotEmpty String name,
                         @NotNull @NotBlank @NotEmpty String lastname,
                         @NotNull @NotBlank @NotEmpty String email,
                         @Size(min = 8) @NotNull @NotBlank @NotEmpty String password,
                         @Positive int age,
                         @PositiveOrZero long balance) throws SQLException {
        if (userRepository.get(email) != null) {
            throw new ValidationException(Message.EMAIL_IS_NOT_AVAILABLE);
        }
        User user = new User();
        user.setName(name);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(MD5Encoder.encode(password));
        user.setAge(age);
        user.setBalance(balance);
        userRepository.create(user);
    }

    @Override
    public void login(@NotNull @NotBlank @NotEmpty String email,
                      @Size(min = 8) @NotEmpty @NotBlank @NotNull String password) throws SQLException {
        User user = userRepository.get(email);
        if (user == null) {
            throw new UserNotFoundException(Message.USER_NOT_FOUND);
        }
        if (!Objects.equals(user.getPassword(), MD5Encoder.encode(password))) {
            throw new am.smartCode.jdbc.exception.ValidationException(Message.INVALID_PASSWORD);
        }

    }

    @Override
    public void deleteUser(@NotNull @NotBlank @NotEmpty String email,
                           @Size(min = 8) @NotEmpty @NotBlank @NotNull String password) throws SQLException {
        if (!userRepository.get(email).getPassword().equals(MD5Encoder.encode(password))) {
            throw new ValidationException(Message.INVALID_PASSWORD);
        } else {
            userRepository.delete(userRepository.get(email).getId());
        }
    }

    @Override
    public void updateUser(@NotNull @NotEmpty @NotBlank String email,
                           @NotEmpty @NotBlank @NotNull @Size(min = 8) String newPassword,
                           @NotEmpty @NotBlank @NotNull @Size(min = 8) String repeatPassword) throws SQLException {
        if (!Objects.equals(newPassword, repeatPassword)) {
            throw new ValidationException(Message.PASSWORD_NOT_MATCHES);
        } else {
            User user = userRepository.get(email);
            user.setPassword(newPassword);
            userRepository.update(user);
        }
    }
}
