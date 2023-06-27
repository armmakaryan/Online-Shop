package am.smartCode.jdbc.controller;

import am.smartCode.jdbc.repository.user.impl.UserRepositoryImpl;
import am.smartCode.jdbc.service.user.UserService;
import am.smartCode.jdbc.service.user.impl.UserServiceImpl;
import am.smartCode.jdbc.util.CookieUtil;
import am.smartCode.jdbc.util.DatabaseConnection;
import am.smartCode.jdbc.util.constants.Path;
import am.smartCode.jdbc.util.constants.Strings;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAccountServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = (String) req.getSession().getAttribute(Strings.EMAIL);
        String password = req.getParameter(Strings.PASSWORD);
        UserService userService = new UserServiceImpl(new UserRepositoryImpl(DatabaseConnection.getInstance()));
        try {
            userService.deleteUser(email, password);
            Cookie remember = CookieUtil.getCookieByName(req.getCookies(), Strings.REMEMBER);
            if (remember != null) {
                remember.setMaxAge(0);
                resp.addCookie(remember);
            }
            req.getSession().invalidate();
            resp.sendRedirect(Path.LOGIN);
        } catch (Exception e) {
            req.setAttribute(Strings.MESSAGE, e.getMessage());
            req.getRequestDispatcher(Path.DELETE_ACCOUNT).forward(req, resp);
        }
    }
}