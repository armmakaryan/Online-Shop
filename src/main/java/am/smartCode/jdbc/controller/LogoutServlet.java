package am.smartCode.jdbc.controller;

import am.smartCode.jdbc.util.CookieUtil;
import am.smartCode.jdbc.util.constants.Path;
import am.smartCode.jdbc.util.constants.Strings;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie remember = CookieUtil.getCookieByName(req.getCookies(), Strings.REMEMBER);
        remember.setMaxAge(0);
        resp.addCookie(remember);

        req.getSession().invalidate();
        req.getRequestDispatcher(Path.LOGIN).forward(req, resp);
    }
}