package am.smartCode.jdbc.controller;

import am.smartCode.jdbc.repository.product.ProductRepository;
import am.smartCode.jdbc.repository.product.impl.ProductRepositoryimpl;
import am.smartCode.jdbc.service.product.ProductService;
import am.smartCode.jdbc.service.product.impl.ProductServiceImpl;
import am.smartCode.jdbc.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateProductServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var category = req.getParameter("category");
        var name = req.getParameter("name");
        var publishedYear = req.getParameter("publishedYear");
        String pricestr = req.getParameter("price");
        long price = Long.parseLong(pricestr);
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        ProductRepository productRepository = new ProductRepositoryimpl(databaseConnection);
        ProductService productService = new ProductServiceImpl(productRepository);
        try{
            productService.createProduct(category,name,publishedYear,price);
            resp.sendRedirect("/product.html");
        }catch (Exception e){
            resp.sendRedirect("/createProduct");
        }

    }
}
