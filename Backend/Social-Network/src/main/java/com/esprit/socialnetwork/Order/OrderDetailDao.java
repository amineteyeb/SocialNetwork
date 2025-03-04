package com.esprit.socialnetwork.Order;


import com.esprit.socialnetwork.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetail, Integer> {
    public List<OrderDetail> findByUser(User user);

    public List<OrderDetail> findByOrderStatus(String status);
}
