package com.esprit.socialNetwork.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private boolean available;
    private Long shopId;
    private Long sellerId;
}
