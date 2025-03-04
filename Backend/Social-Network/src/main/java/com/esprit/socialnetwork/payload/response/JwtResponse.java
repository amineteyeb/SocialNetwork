package com.esprit.socialnetwork.payload.response;


import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtResponse {
    private String id;
    private String token;
    private String refreshToken;
    private List<String> roles;
    private Boolean firstLoggedIn;
}
