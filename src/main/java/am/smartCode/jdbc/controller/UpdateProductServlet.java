package am.smartCode.jdbc.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateProductServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idstr = req.getParameter("id");
        int id = Integer.parseInt(idstr);
        var category = req.getParameter("category");
        var name = req.getParameter("name");
        var publishedYear = req.getParameter("publishedYear");
        String pricestr = req.getParameter("price");
        Long price = Long.parseLong(pricestr);
    }
}
