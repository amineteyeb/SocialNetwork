package com.esprit.socialNetwork.Seller;

import com.esprit.socialNetwork.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findSellerByUser(Optional<User> user);
}
