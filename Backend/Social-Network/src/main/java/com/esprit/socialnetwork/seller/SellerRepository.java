package com.esprit.socialnetwork.seller;

import com.esprit.socialnetwork.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findSellerByUser(Optional<User> user);
}
