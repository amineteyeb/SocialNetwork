package com.esprit.socialNetwork.Product;

import com.esprit.socialNetwork.Seller.Seller;
import com.esprit.socialNetwork.Seller.SellerRepository;
import com.esprit.socialNetwork.Shop.Shop;
import com.esprit.socialNetwork.Shop.ShopRepository;
import com.esprit.socialNetwork.User.User;
import com.esprit.socialNetwork.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("product")
public class ProductRestController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserService userService;
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/myshop")
    public List<Product> getMyProducts(@CurrentSecurityContext(expression="authentication?.name")
    String username) {
        User user = userService.getUser(username);

        return productService.GetMyroducts(user.getId());
    }

    @GetMapping("/products")
    public List<Product> getProductsOwner(@CurrentSecurityContext(expression="authentication?.name")
                                       String username) {
        User user = userService.getUser(username);

        return productService.getOtherProducts(user.getId());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping(value = "/add",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addProduct(@RequestPart("product") ProductDTO productDto, @RequestPart ("imageFile")MultipartFile[] file)  {
        Shop shop = shopRepository.findById(productDto.getShopId()).orElse(null);
        Seller seller = sellerRepository.findById(productDto.getSellerId()).orElse(null);

        Product product = Product.builder()
                .name(productDto.getName())
                .shop(shop)
                .available(true)

                .price(productDto.getPrice())
                .description(productDto.getDescription())

                // Set other attributes
                .build();


       // return ResponseEntity.ok(productService.addProduct(product));
        try { Set<ImageModel> images = uploadImages(file);
            product.setProductImages(images);
           return  productService.addProduct(product);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return  null;
        }
    }
public  Set<ImageModel> uploadImages(MultipartFile[] multipartFiles ) throws IOException {
    Set<ImageModel> imageModels = new HashSet<>();
    for (MultipartFile file : multipartFiles) {
ImageModel imageModel = new ImageModel(
        file.getOriginalFilename(),
        file.getContentType(),
        file.getBytes()
);
        imageModels.add(imageModel);

    }
    return imageModels;
    }

  /*  @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product existingProduct = productService.getProductById(id);

        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(productDto.getName());

            Shop shop = shopService.getShopById(productDto.getShopId());
            updatedProduct.setShop(shop);

            // Update other attributes

            Product savedProduct = productService.addProduct(updatedProduct);
            return ResponseEntity.ok(savedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping({"/getProductDetailsById/{productId}"})
    public Product getProductDetailsById(@PathVariable("productId") Integer productId) {
        return productService.getProductDetailsById(productId);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping({"/deleteProductDetails/{productId}"})
    public void deleteProductDetails(@PathVariable("productId") Integer productId) {
        productService.deleteProductDetails(productId);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping({"/getProductDetails/{isSingleProductCheckout}/{productId}"})
    public List<Product> getProductDetails(@PathVariable(name = "isSingleProductCheckout" ) boolean isSingleProductCheckout,
                                           @PathVariable(name = "productId")  Integer productId,@CurrentSecurityContext(expression="authentication?.name") String username) {
        return productService.getProductDetails(isSingleProductCheckout, productId,username);
    }
}
