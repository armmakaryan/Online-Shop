package am.smartCode.jdbc.service.user;

import java.sql.SQLException;

public interface UserService {

    void register(String name, String lastname, String email, String password, int age, long balance) throws SQLException;

    void login(String email, String password) throws SQLException;

    void deleteUser(String email,String password) throws SQLException;
    void updateUser(String email,String newPassword, String repeatPassword) throws SQLException;

}