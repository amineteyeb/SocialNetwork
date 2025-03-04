package com.esprit.socialnetwork.shop;

import com.esprit.socialnetwork.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findById(long shop_id);
    Shop findByOwner(Seller seller);

}
