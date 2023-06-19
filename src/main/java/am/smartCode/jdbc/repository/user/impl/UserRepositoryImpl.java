package am.smartCode.jdbc.repository.user.impl;

import am.smartCode.jdbc.model.User;
import am.smartCode.jdbc.repository.user.UserRepository;
import am.smartCode.jdbc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final Connection connection;

    public UserRepositoryImpl(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
        try {
            this.connection.createStatement().executeUpdate(
                    """
                            CREATE TABLE IF NOT EXISTS users (
                            id bigserial primary key,
                            name varchar(255) not null,
                            lastname varchar(255) not null,
                            balance double precision not null,
                            email varchar(255) not null ,
                            password varchar(255) not null,
                            age integer not null
                            )
                            """);
        } catch (SQLException e) {
            System.out.println("Connection exception");
        }

    }

    @Override
    public void create(User user) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users (name,lastname, balance,email,password,age) VALUES (?,?,?,?,?,?)"
        );
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLastname());
        preparedStatement.setDouble(3, user.getBalance());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setInt(6, user.getAge());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void update(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE users SET name = ?, lastname = ?, balance = ?, email = ?, password = ?, age = ? WHERE id = ?"
        );

        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLastname());
        preparedStatement.setDouble(3, user.getBalance());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setInt(6, user.getAge());
        preparedStatement.setLong(7, user.getId());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public User get(Long id) throws SQLException {
        User user = new User();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from users WHERE id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            setUserFields(user, resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> usersList = new ArrayList<>();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from users");
        addUserToListFromResultSet(usersList, resultSet);
        return usersList;
    }


    @Override
    public List<User> findUsersByName(String name) throws SQLException {

        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM users WHERE lower(name) LIKE lower(concat('%',?,'%'))"
        );
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        addUserToListFromResultSet(users, resultSet);
        return users;
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE from users WHERE id = ?");
        preparedStatement.setLong(1, id);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    @Override
    public User findUsersByEmailAndPassword(String email, String password) throws Exception {
        User user = null;
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM users WHERE email = ? AND password = ?"
        );
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            user = new User();
            setUserFields(user,resultSet);
        }
        else
            throw new Exception("User not found");

        resultSet.close();
        preparedStatement.close();

        return user;
    }

    private void setUserFields(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setLastname(resultSet.getString("lastname"));
        user.setBalance(resultSet.getDouble("balance"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setAge(resultSet.getInt("age"));
    }

    private void addUserToListFromResultSet(List<User> usersList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            User user = new User();
            setUserFields(user, resultSet);
            usersList.add(user);
        }
    }
}