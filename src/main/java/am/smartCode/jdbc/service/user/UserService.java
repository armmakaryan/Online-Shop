package am.smartCode.jdbc.service.user;

import am.smartCode.jdbc.model.User;

public interface UserService {

    void register(User user) throws Exception;

    void login(String email, String password) throws Exception;

}
