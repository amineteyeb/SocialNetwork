package com.esprit.socialNetwork.Product;

import com.esprit.socialNetwork.Order.Cart;
import com.esprit.socialNetwork.Order.CartDao;
import com.esprit.socialNetwork.Seller.SellerRepository;
import com.esprit.socialNetwork.Seller.SellerService;
import com.esprit.socialNetwork.Shop.Shop;
import com.esprit.socialNetwork.Shop.ShopRepository;
import com.esprit.socialNetwork.User.User;
import com.esprit.socialNetwork.User.UserRepository;
import com.esprit.socialNetwork.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements  IProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SellerService ss;
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> GetMyroducts(Long userid) {
        var dto = ss.getMySellerDTO(userid);
      Shop shop =shopRepository.findByOwner(sellerRepository.findById(dto.getSeller_id()).orElseThrow());
          return productRepository.findByShop(shop);

    }
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getOtherProducts(Long userId) {
        // Get the user's shop
        var dto = ss.getMySellerDTO(userId);
        Shop shop = shopRepository.findByOwner(sellerRepository.findById(dto.getSeller_id()).orElseThrow());

        // Find products that don't belong to the user's shop
        return productRepository.findByShopNot(shop);
    }

    public Product getProductDetailsById(Integer productId) {
        return productRepository.findById(Long.valueOf(productId)).orElse(null);
    }

    public void deleteProductDetails(Integer productId) {
        productRepository.deleteById(Long.valueOf(productId));
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId,String username) {
        if(isSingleProductCheckout && productId != 0) {
            // we are going to buy a single product

            List<Product> list = new ArrayList<>();
            Product product = productRepository.findById(Long.valueOf(productId)).orElse(null);
            list.add(product);
            return list;
        } else {
            // we are going to checkout entire cart
            User user = userService.getUser(username);

            List<Cart> carts = cartDao.findByUser(user);

            return carts.stream().map(Cart::getProduct).collect(Collectors.toList());
        }
    }

}
