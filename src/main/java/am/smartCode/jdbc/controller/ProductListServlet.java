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
import java.util.List;

public class ProductListServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(DatabaseConnection.getInstance()));
        try {
            List<Product> allProducts = productService.getAllProducts();
            req.setAttribute(Strings.LIST,allProducts);
            req.getRequestDispatcher(Path.PRODUCT_PAGE).forward(req,resp);
        } catch (Exception e) {
            req.setAttribute(Strings.MESSAGE,e.getMessage());
            req.getRequestDispatcher(Path.PRODUCT_PAGE).forward(req,resp);
        }
    }
}