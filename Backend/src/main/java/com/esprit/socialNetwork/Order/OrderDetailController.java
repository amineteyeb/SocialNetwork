package com.esprit.socialNetwork.Order;


import com.esprit.socialNetwork.User.User;
import com.esprit.socialNetwork.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;

    @PostMapping({"/placeOrder/{isSingleProductCheckout}"})
    public void placeOrder(@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
            @RequestBody OrderInput orderInput,@CurrentSecurityContext(expression="authentication?.name") String username) {
        User user = userService.getUser(username);
        orderDetailService.placeOrder(orderInput, isSingleProductCheckout,user.getId());
    }


    @GetMapping({"/getOrderDetails"})
    public List<OrderDetail> getOrderDetails(@CurrentSecurityContext(expression="authentication?.name") String username) {
        User user = userService.getUser(username);
        return orderDetailService.getOrderDetails(user.getId());
    }


    @GetMapping({"/getAllOrderDetails/{status}"})
    public List<OrderDetail> getAllOrderDetails(@PathVariable(name = "status") String status) {
        return orderDetailService.getAllOrderDetails(status);
    }


    @GetMapping({"/markOrderAsDelivered/{orderId}"})
    public void markOrderAsDelivered(@PathVariable(name = "orderId") Integer orderId) {
        orderDetailService.markOrderAsDelivered(orderId);
    }


    @GetMapping({"/createTransaction/{amount}"})
    public TransactionDetails createTransaction(@PathVariable(name = "amount") Double amount) {
        return orderDetailService.createTransaction(amount);
    }
}
