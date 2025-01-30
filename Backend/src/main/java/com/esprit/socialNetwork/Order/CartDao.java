package com.esprit.socialNetwork.Order;


import com.esprit.socialNetwork.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao extends JpaRepository<Cart, Integer > {
    public List<Cart> findByUser(User user);
}
