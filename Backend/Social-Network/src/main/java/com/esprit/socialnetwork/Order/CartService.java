package com.esprit.socialnetwork.Order;

import com.esprit.socialnetwork.product.Product;
import com.esprit.socialnetwork.product.ProductRepository;
import com.esprit.socialnetwork.user.User;
import com.esprit.socialnetwork.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductRepository productDao;

    @Autowired
    private UserRepository userDao;

    public void deleteCartItem(Integer cartId) {
        cartDao.deleteById(cartId);
    }

    @Transactional
    public Cart addToCart(Long productId, Long userId) {
        Product product = productDao.findById(productId).orElse(null);
        User user = userDao.findById(userId).orElse(null);

        if (product == null || user == null) {
            return null; // Handle the case where product or user is not found
        }

        // Check if the product is already in the user's cart
        List<Cart> cartList = cartDao.findByUser(user);
        boolean productAlreadyInCart = cartList.stream()
                .anyMatch(cart -> Objects.equals(cart.getProduct().getId(), productId));

        if (productAlreadyInCart) {
            return null; // product is already in the cart
        }

        // Create a new cart and set the bidirectional relationship
        Cart cart = new Cart(product, user);
        user.getCarts().add(cart); // Make sure to properly manage the bidirectional relationship

        // Save the user, which will also save the new cart
        userDao.save(user);

        return cart;
    }


    public List<Cart> getCartDetails(Long id) {

        User user = userDao.findById(id).orElse(null);
        System.out.println("cart for useeer found: " + user);
        return cartDao.findByUser(user);
    }
}
