package com.esprit.socialNetwork.Product;

import com.esprit.socialNetwork.Shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByShop(Shop shop);
    List<Product> findByShopNot(Shop shop);
}
