package am.smartCode.jdbc.service.user.impl;

import am.smartCode.jdbc.model.User;
import am.smartCode.jdbc.repository.user.UserRepository;
import am.smartCode.jdbc.service.user.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void register(User user) throws Exception {
        validationForRegistration(user);
        userRepository.create(user);
        System.out.println(user.getName() + " you are registered successfully");
    }

    @Override
    public void login(String email, String password) throws Exception {
        validationForLogin(email, password);
        User loginedUser = userRepository.findUsersByEmailAndPassword(email, password);
        System.out.println(loginedUser.getName() + " you are logined successfully");
    }

    private void validationForRegistration(User user) throws Exception {
        if (user.getEmail() == null ||
            user.getEmail().length() == 0 ||
            !user.getEmail().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
                                     + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"))
            throw new Exception("something with email is wrong");
        if (user.getPassword() == null ||
            user.getPassword().length() < 8
        )
            throw new Exception("password validation is wrong");
    }

    private void validationForLogin(String email, String password) throws Exception{
        if (email == null || password == null)
            throw new Exception("email or password is null");
    }
}
