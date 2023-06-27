package am.smartCode.jdbc.controller;

import am.smartCode.jdbc.repository.product.impl.ProductRepositoryImpl;
import am.smartCode.jdbc.service.product.ProductService;
import am.smartCode.jdbc.service.product.impl.ProductServiceImpl;
import am.smartCode.jdbc.util.DatabaseConnection;
import am.smartCode.jdbc.util.constants.Path;
import am.smartCode.jdbc.util.constants.Strings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idstr = req.getParameter(Strings.ID);
        long id=0;
        try {
            id = Long.parseLong(idstr);
        } catch (Exception ignored) {
        }
        ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(DatabaseConnection.getInstance()));
        try {
            productService.deleteProduct(id);
            req.setAttribute(Strings.ID,id);
            req.getRequestDispatcher(Path.DELETE_PRODUCT).forward(req,resp);
        } catch (Exception e) {
            req.setAttribute(Strings.MESSAGE,e.getMessage());
            req.getRequestDispatcher(Path.DELETE_PRODUCT).forward(req,resp);
        }
    }
}