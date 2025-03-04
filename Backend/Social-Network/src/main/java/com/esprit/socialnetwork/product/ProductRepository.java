package com.esprit.socialnetwork.product;

import com.esprit.socialnetwork.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByShop(Shop shop);
    List<Product> findByShopNot(Shop shop);
}
