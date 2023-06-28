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
import java.io.IOException;

public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = (String)req.getSession().getAttribute(Strings.EMAIL);
        String newPassword = req.getParameter(Strings.NEW_PASSWORD);
        String repeatPassword = req.getParameter(Strings.REPEAT_PASSWORD);
        UserService userService = new UserServiceImpl(new UserRepositoryJdbcImpl(DatabaseConnection.getInstance()));
        try {
            userService.updateUser(email,newPassword,repeatPassword);
            resp.sendRedirect(Path.HOME_PAGE);
        } catch (Exception e) {
            req.setAttribute(Strings.MESSAGE,e.getMessage());
            req.getRequestDispatcher(Path.CHANGE_PASSWORD).forward(req,resp);
        }
    }
}