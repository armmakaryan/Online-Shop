package am.smartCode.jdbc.controller;

import am.smartCode.jdbc.repository.user.impl.UserRepositoryJdbcImpl;
import am.smartCode.jdbc.service.user.UserService;
import am.smartCode.jdbc.service.user.impl.UserServiceImpl;
import am.smartCode.jdbc.util.CookieUtil;
import am.smartCode.jdbc.util.DatabaseConnection;
import am.smartCode.jdbc.util.constants.Path;
import am.smartCode.jdbc.util.constants.Strings;
import am.smartCode.jdbc.util.encoder.AESManager;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class StartServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String encodedString = CookieUtil.getCookieValueByName(req.getCookies(), Strings.REMEMBER);
        UserService userService = new UserServiceImpl(new UserRepositoryJdbcImpl(DatabaseConnection.getInstance()));
        try {
            if (encodedString != null) {
                String remember = AESManager.decrypt(encodedString);
                String email = remember.split(":")[0];
                String password = remember.split(":")[1];
                userService.login(email, password);
                Cookie cookie = new Cookie(Strings.REMEMBER, AESManager.encrypt(email + ":" + password));
                cookie.setMaxAge(360000);
                resp.addCookie(cookie);

                HttpSession session = req.getSession();
                session.setAttribute(Strings.EMAIL, email);
                req.getRequestDispatcher(Path.HOME_PAGE).forward(req, resp);
            }
            else {
                resp.sendRedirect(Path.LOGIN);
            }
        } catch (Exception e) {
            req.setAttribute(Strings.MESSAGE, e.getMessage());
            resp.setStatus(400);
            req.getRequestDispatcher(Path.LOGIN).forward(req, resp);
        }
    }
}