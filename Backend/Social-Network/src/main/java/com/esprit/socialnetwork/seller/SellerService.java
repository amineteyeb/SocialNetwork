package com.esprit.socialnetwork.seller;

import com.esprit.socialnetwork.shop.Shop;
import com.esprit.socialnetwork.shop.ShopRepository;
import com.esprit.socialnetwork.user.User;
import com.esprit.socialnetwork.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService implements  ISellerService{
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    public mySellerDTO getMySellerDTO(Long userid) {
        Optional<User> user =  userRepository.findById(userid);

            var seller= sellerRepository.findSellerByUser(user);
            Shop shop =shopRepository.findByOwner(seller);
        return mySellerDTO.builder().seller_id(seller.getId()).shop_id(shop.getId()).userid(userid).build();

    }
}
