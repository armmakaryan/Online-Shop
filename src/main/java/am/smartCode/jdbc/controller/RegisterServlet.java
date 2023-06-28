package am.smartCode.jdbc.controller;

import am.smartCode.jdbc.repository.user.impl.UserRepositoryJdbcImpl;
import am.smartCode.jdbc.service.user.UserService;
import am.smartCode.jdbc.service.user.impl.UserServiceImpl;
import am.smartCode.jdbc.util.DatabaseConnection;
import am.smartCode.jdbc.util.constants.Path;
import am.smartCode.jdbc.util.constants.Strings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(Strings.NAME);
        String lastname = req.getParameter(Strings.LASTNAME);
        String email = req.getParameter(Strings.EMAIL);
        String password = req.getParameter(Strings.PASSWORD);
        String agestr = req.getParameter(Strings.AGE);
        int age = 0;
        try {
            age = Integer.parseInt(agestr);
        } catch (Exception ignored) {
        }
        String balancestr = req.getParameter(Strings.BALANCE);
        long balance = 0;
        try {
            balance = Long.parseLong(balancestr);
        } catch (Exception ignored) {
        }
        UserService userService = new UserServiceImpl(new UserRepositoryJdbcImpl(DatabaseConnection.getInstance()));
        try {
            userService.register(name, lastname, email, password, age, balance);
            HttpSession session = req.getSession();
            session.setAttribute(Strings.EMAIL, email);
            req.getRequestDispatcher(Path.HOME_PAGE).forward(req, resp);
        } catch (Exception e) {
            req.setAttribute(Strings.MESSAGE, e.getMessage());
            req.getRequestDispatcher(Path.REGISTER).forward(req, resp);
        }
    }
}