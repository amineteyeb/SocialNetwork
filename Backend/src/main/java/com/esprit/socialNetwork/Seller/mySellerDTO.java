package com.esprit.socialNetwork.Seller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Builder
@Setter
public class mySellerDTO implements Serializable {
    private long userid;
    private long shop_id;
     private  long seller_id;
}
