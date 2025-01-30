package com.esprit.socialNetwork.Order;

import com.esprit.socialNetwork.Product.Product;
import com.esprit.socialNetwork.Product.ProductRepository;
import com.esprit.socialNetwork.User.User;
import com.esprit.socialNetwork.User.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {

    private static final String ORDER_PLACED = "Placed";

    private static final String KEY = "rzp_test_AXBzvN2fkD4ESK";
    private static final String KEY_SECRET = "bsZmiVD7p1GMo6hAWiy4SHSH";
    private static final String CURRENCY = "DT";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductRepository productDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private CartDao cartDao;

    public List<OrderDetail> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        if(status.equals("All")) {
            orderDetails.addAll(orderDetailDao.findAll());
        } else {
            orderDetails.addAll(orderDetailDao.findByOrderStatus(status));
        }


         return orderDetails;
    }

    public List<OrderDetail> getOrderDetails(Long id) {

        User user = userDao.findById(id).orElse(null);

        return orderDetailDao.findByUser(user);
    }

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout,Long id) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o: productQuantityList) {
            Product product = productDao.findById(Long.valueOf(o.getProductId())).orElse(null);


            User user = userDao.findById(id).orElse(null);

            assert product != null;
            OrderDetail orderDetail = new OrderDetail(
                  orderInput.getFullName(),
                  orderInput.getFullAddress(),
                  orderInput.getContactNumber(),
                  orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user,
                    orderInput.getTransactionId()
            );

            // empty the cart.
            if(!isSingleProductCheckout) {
                List<Cart> carts = cartDao.findByUser(user);
                carts.forEach(x -> cartDao.deleteById(x.getCartId()));
            }

            orderDetailDao.save(orderDetail);
        }
    }

    public void markOrderAsDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderId).orElse(null);

        if(orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }

    }

    public TransactionDetails createTransaction(Double amount) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount * 100) );
            jsonObject.put("currency", CURRENCY);

            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);

            Order order = razorpayClient.orders.create(jsonObject);

            return prepareTransactionDetails(order);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(Order order) {
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        return new TransactionDetails(orderId, currency, amount, KEY);
    }
}
