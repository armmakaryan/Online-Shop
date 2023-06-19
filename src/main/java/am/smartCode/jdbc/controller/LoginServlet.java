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
            userService.login(email,password);
            resp.sendRedirect("/index.html");
        }catch (Exception e) {
            resp.getWriter().write("<!DOCTYPE html>\n" +
                                   "<html lang=\"en\">\n" +
                                   "<head>\n" +
                                   "    <meta charset=\"UTF-8\">\n" +
                                   "    <title>Login Page</title>\n" +
                                   "</head>\n" +
                                   "<body>\n" +
                                   "\n" +
                                   "    <h2>Wrong email or password</h2>\n" +
                                   "    <form method=\"post\" action=\"/login\">        \n" +
                                   "            email: <input type=\"text\" name=\"email\"/><br><br>\n" +
                                   "            password: <input type=\"password\" name=\"password\"/><br><br>\n" +
                                   "            <input type=\"submit\"/>\n" +
                                   "    </form>\n" +
                                   "</body>\n" +
                                   "</html>");
        }



    }
}
