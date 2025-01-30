package com.esprit.socialNetwork.Shop;

import com.esprit.socialNetwork.Seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findById(long shop_id);
    Shop findByOwner(Seller seller);

}
