package com.esprit.socialNetwork.Seller;

import com.esprit.socialNetwork.User.User;
import com.esprit.socialNetwork.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MyShop")
public class SellerRestController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private UserService userService;


    @GetMapping("/getdetailsbyuser")
    public mySellerDTO getDetails(@CurrentSecurityContext(expression="authentication?.name")
                                       String username) {
        User user = userService.getUser(username);

        return sellerService.getMySellerDTO(user.getId());
    }
}
