package am.smartCode.jdbc.controller;

import am.smartCode.jdbc.model.Product;
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

public class GetProductServlet extends HttpServlet {

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
            Product product = productService.getProduct(id);
            req.setAttribute(Strings.PRODUCT,product);
            req.getRequestDispatcher(Path.GET_PRODUCT).forward(req,resp);
        } catch (Exception e) {
            req.setAttribute(Strings.MESSAGE,e.getMessage());
            req.getRequestDispatcher(Path.GET_PRODUCT).forward(req,resp);
        }
    }
}