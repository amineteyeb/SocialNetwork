package com.esprit.socialnetwork.user;

import com.esprit.socialnetwork.client.Client;
import com.esprit.socialnetwork.Order.Cart;
import com.esprit.socialnetwork.seller.Seller;
import com.esprit.socialnetwork.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "_user")

public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String passwd;
    private String imageUrl;
    private Boolean enabled = false;
    private String verificationCode;
    private String passwordToken;
    private Boolean agreedTerms;
    @Enumerated(EnumType.STRING)
    private  RoleEnum role;
    @Enumerated(EnumType.STRING)
    
    private  ThemeEnum theme ;
   @JsonIgnore
   @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Seller seller;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cart> carts;

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", usrname='" + username + '\'' +


                '}';
    }

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Client client;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




}