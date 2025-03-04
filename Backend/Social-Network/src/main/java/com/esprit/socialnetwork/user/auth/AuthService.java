package com.esprit.socialnetwork.user.auth;

import com.esprit.socialnetwork.client.Client;
import com.esprit.socialnetwork.client.ClientRepository;
import com.esprit.socialnetwork.seller.Seller;
import com.esprit.socialnetwork.seller.SellerRepository;
import com.esprit.socialnetwork.shop.Shop;
import com.esprit.socialnetwork.shop.ShopRepository;
import com.esprit.socialnetwork.token.Token;
import com.esprit.socialnetwork.token.TokenRepository;
import com.esprit.socialnetwork.token.TokenType;
import com.esprit.socialnetwork.user.UserRepository;
import com.esprit.socialnetwork.user.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ShopRepository shopRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private  final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final com.esprit.socialnetwork.user.config.JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    private final Random rand = new Random();
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
            // Create a seller object and link it to the user
            Seller seller = Seller.builder()
                    .user(savedUser)
                    .build();

            // Save the seller
            var savedSeller= sellerRepository.save(seller);
            Shop shop = Shop.builder()
                    .owner(savedSeller)
                    .build();
           shopRepository.save(shop);
            // You can create a shop object here if needed and save it.
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

        return  repository.findByEmail(request.getEmail())
                .orElseThrow(()-> new AuthenticationCredentialsNotFoundException("Wrong credentials, Please double check email/password"));
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


    public String generateCode() {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            builder.append(rand.nextInt(10));
        }

        return builder.toString();
    }


    public String encodePwd(String newPwd) {
        return passwordEncoder.encode(newPwd);
    }


    public  boolean comparePwd(String plain, String encoded) {
        return passwordEncoder.matches(plain, encoded);
    }


    public String generateRandomPassword() {
        // Define the characters allowed in the random password
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}|;:',<.>/?";

        // Generate a random password of length 12
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            password.append(characters.charAt(rand.nextInt(characters.length())));
        }

        return password.toString();

    }


}


