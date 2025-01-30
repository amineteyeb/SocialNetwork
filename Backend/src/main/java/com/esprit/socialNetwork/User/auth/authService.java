package com.esprit.socialNetwork.User.auth;

import com.esprit.socialNetwork.Client.Client;
import com.esprit.socialNetwork.Client.ClientRepository;
import com.esprit.socialNetwork.Seller.Seller;
import com.esprit.socialNetwork.Seller.SellerRepository;
import com.esprit.socialNetwork.Shop.Shop;
import com.esprit.socialNetwork.Shop.ShopRepository;
import com.esprit.socialNetwork.Token.Token;
import com.esprit.socialNetwork.Token.TokenRepository;
import com.esprit.socialNetwork.Token.TokenType;
import com.esprit.socialNetwork.User.UserRepository;
import com.esprit.socialNetwork.User.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
@RequiredArgsConstructor
public class authService {
    private final ShopRepository shopRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final com.esprit.socialNetwork.User.Config.jwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .passwd(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .agreedTerms(false)
                .build();
        var savedUser = repository.save(user);
        if (request.getRole() == RoleEnum.SELLER) {
            // Create a Seller object and link it to the user
            Seller seller = Seller.builder()
                    .user(savedUser)
                    .build();

            // Save the Seller
            var savedSeller= sellerRepository.save(seller);
            Shop shop = Shop.builder()
                    .owner(savedSeller)
                    .build();
            var savedShop= shopRepository.save(shop);
            // You can create a Shop object here if needed and save it.
        } else if (request.getRole() == RoleEnum.CLIENT) {
            // Create a Client object and link it to the user
            Client client = Client.builder()
                    .user(savedUser)
                    .build();

            // Save the Client
            clientRepository.save(client);
        }



        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,  jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    public User getuser(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();


        return user;
    }
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.Bearer)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

   private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


}


