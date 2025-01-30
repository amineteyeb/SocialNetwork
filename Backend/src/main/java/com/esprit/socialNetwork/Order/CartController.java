package com.esprit.socialNetwork.Order;


import com.esprit.socialNetwork.User.User;
import com.esprit.socialNetwork.User.UserRepository;
import com.esprit.socialNetwork.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController {
@Autowired
    private  UserService userService;
    @Autowired
    private  CartService cartService;
    @Autowired
    private  UserRepository userRepository;


    @GetMapping("/addToCart/{productId}/{userID}")
    public Cart addToCart(@PathVariable(name = "productId") Long productId,@PathVariable(name = "userID") Long username) {
        System.out.println(username);
        User user = userRepository.findById(username).orElse(null);

       // User user = null;
        System.out.println("User found: " + user);
        if (user != null) {
           // System.out.println("User found: " + user.toString());
            return cartService.addToCart(productId, user.getId());
        } else {
            System.out.println("User not found");
            // Handle the case when the user is not found
            return handleUserNotFound();
        }
    }


    private Cart handleUserNotFound() {
        // Implement your logic for handling user not found
        return new Cart();
    }


    @DeleteMapping({"/deleteCartItem/{cartId}"})
    public void deleteCartItem(@PathVariable(name = "cartId") Integer cartId) {
        cartService.deleteCartItem(cartId);
    }


    @GetMapping({"/getCartDetails/{userID}"})
    public List<Cart> getCartDetails(@PathVariable(name = "userID") Long username) {

        User user = userRepository.findById(username).orElse(null);
System.out.println("*****************************************this is user "+user.toString());
        return cartService.getCartDetails(user.getId());
    }
}
