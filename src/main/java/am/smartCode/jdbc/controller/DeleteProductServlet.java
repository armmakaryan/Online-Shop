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

public class DeleteProductServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idstr = req.getParameter("id");
        int id = Integer.parseInt(idstr);
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        ProductRepository productRepository = new ProductRepositoryimpl(databaseConnection);
        ProductService productService = new ProductServiceImpl(productRepository);
        try {
            productService.deleteProduct(id);
            resp.sendRedirect("/product.jsp");
        }catch (Exception e){
            resp.sendRedirect("/deleteProduct.jsp");
        }
    }
}
