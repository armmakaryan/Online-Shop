package am.smartCode.jdbc.service.user;

import am.smartCode.jdbc.model.User;

public interface UserService {

    void register(User user) throws Exception;

    void login(String email, String password) throws Exception;

    void changePassword(String username, String newPassword, String repeatPassword);

    void deleteAccount(String email, String password) throws Exception;
}
