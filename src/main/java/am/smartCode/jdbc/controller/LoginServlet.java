package am.smartCode.jdbc.controller;

import am.smartCode.jdbc.repository.user.impl.UserRepositoryJdbcImpl;
import am.smartCode.jdbc.service.user.UserService;
import am.smartCode.jdbc.service.user.impl.UserServiceImpl;
import am.smartCode.jdbc.util.DatabaseConnection;
import am.smartCode.jdbc.util.constants.Path;
import am.smartCode.jdbc.util.constants.Strings;
import am.smartCode.jdbc.util.encoder.AESManager;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter(Strings.EMAIL);
        String password = req.getParameter(Strings.PASSWORD);
        String rememberMe = req.getParameter(Strings.REMEMBER_ME);
        UserService userService = new UserServiceImpl(new UserRepositoryJdbcImpl(DatabaseConnection.getInstance()));
        try {
            userService.login(email, password);
            if (rememberMe != null && rememberMe.equals(Strings.ON)) {
                Cookie cookie = new Cookie(Strings.REMEMBER, AESManager.encrypt(email + ":" + password));
                cookie.setMaxAge(360000);
                resp.addCookie(cookie);
            }
            HttpSession session = req.getSession();
            session.setAttribute(Strings.EMAIL, email);
            req.getRequestDispatcher(Path.HOME_PAGE).forward(req, resp);
        } catch (Exception e) {
            req.setAttribute(Strings.MESSAGE, e.getMessage());
            req.getRequestDispatcher(Path.LOGIN).forward(req, resp);
        }


    }
}