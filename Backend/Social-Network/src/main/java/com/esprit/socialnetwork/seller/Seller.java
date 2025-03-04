package com.esprit.socialnetwork.seller;

import com.esprit.socialnetwork.shop.Shop;
import com.esprit.socialnetwork.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user")
    private User user;
    @JsonIgnore
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Shop shop;
}
