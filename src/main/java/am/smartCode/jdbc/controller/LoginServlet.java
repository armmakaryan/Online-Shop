package am.smartCode.jdbc.controller;

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

public class LoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var email = req.getParameter("email");
        var password = req.getParameter("password");
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        UserRepository userRepository = new UserRepositoryImpl(databaseConnection);
        UserService userService = new UserServiceImpl(userRepository);
        try {
            userService.login(email, password);
            req.setAttribute("email", email);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
