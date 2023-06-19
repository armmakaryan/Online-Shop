package am.smartCode.jdbc.controller;

import am.smartCode.jdbc.model.User;
import am.smartCode.jdbc.repository.user.UserRepository;
import am.smartCode.jdbc.repository.user.impl.UserRepositoryImpl;
import am.smartCode.jdbc.service.user.UserService;
import am.smartCode.jdbc.service.user.impl.UserServiceImpl;
import am.smartCode.jdbc.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String agestr = req.getParameter("age");
        int age = Integer.parseInt(agestr);
        String balancestr = req.getParameter("balance");
        long balance = Long.parseLong(balancestr);
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        UserRepository userRepository = new UserRepositoryImpl(databaseConnection);
        UserService userService = new UserServiceImpl(userRepository);
        User user = new User(name,lastname, balance, email, password, age);
        try {
            userService.register(user);
        } catch (Exception e) {
            resp.sendRedirect("register.html");
        }
        resp.sendRedirect("/login.html");

    }
}
