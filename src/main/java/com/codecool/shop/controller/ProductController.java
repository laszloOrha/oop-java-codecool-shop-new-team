package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartItems;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        CartItems cartItems = CartItems.getInstance();

        String categoryID = req.getParameter("dropdown");
        if (categoryID == null){
            categoryID = "1";
        }
        Integer parsedId = Integer.parseInt(categoryID);

        String cartProductId = req.getParameter("cart_item_id");
        if (cartProductId != null) {
            System.out.println(cartProductId);
            Integer cartProductIdInt = Integer.parseInt(cartProductId);
            cartItems.add(cartProductIdInt);
            System.out.println(cartItems.getAll());
            System.out.println(cartItems.getAll().get(0).getQuantity());
        }

        //Map params = new HashMap<>();
        //params.put("category", productCategoryDataStore.find(1));
        //params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//      context.setVariables(params);
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.find(parsedId));
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(parsedId)));

        engine.process("product/index.html", context, resp.getWriter());
    }

}
