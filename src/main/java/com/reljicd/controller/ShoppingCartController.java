package com.reljicd.controller;

import com.reljicd.controller.exception.NotEnoughProductsInStockException;
import com.reljicd.service.ProductService;
import com.reljicd.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Dusan on 21-May-17.
 */
@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/shoppingCart", method = RequestMethod.GET)
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("shoppingCart");
        modelAndView.addObject("products", shoppingCartService.getProductsInCart());
        modelAndView.addObject("total", shoppingCartService.getTotal().toString());
        return modelAndView;
    }

    @RequestMapping(value = "/shoppingCart/addProduct/{productId}", method = RequestMethod.GET)
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        shoppingCartService.addProduct(productService.findById(productId));
        return shoppingCart();
    }

    @RequestMapping(value = "/shoppingCart/removeProduct/{productId}", method = RequestMethod.GET)
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        shoppingCartService.removeProduct(productService.findById(productId));
        return shoppingCart();
    }

    @RequestMapping(value = "/shoppingCart/checkout", method = RequestMethod.GET)
    public ModelAndView checkout() {
        try {
            shoppingCartService.checkout();
        } catch (NotEnoughProductsInStockException e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
        return shoppingCart();
    }
}
