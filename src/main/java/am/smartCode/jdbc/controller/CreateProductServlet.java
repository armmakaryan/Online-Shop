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

public class CreateProductServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String category = req.getParameter(Strings.CATEGORY);
        String name = req.getParameter(Strings.NAME);
        String pricestr = req.getParameter(Strings.PRICE);
        long price = 0;
        try {
            price = Long.parseLong(pricestr);
        } catch (Exception ignored) {
        }
        ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(DatabaseConnection.getInstance()));
        try {
            productService.createProduct(category, name,price);
            req.setAttribute(Strings.NAME, name);
            req.getRequestDispatcher(Path.CREATE_PRODUCT).forward(req, resp);
        } catch (Exception e) {
            req.setAttribute(Strings.MESSAGE, e.getMessage());
            req.getRequestDispatcher(Path.CREATE_PRODUCT).forward(req, resp);

        }
    }
}