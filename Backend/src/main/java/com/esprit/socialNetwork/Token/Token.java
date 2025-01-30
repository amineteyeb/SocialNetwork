package com.esprit.socialNetwork.Token;

import com.esprit.socialNetwork.User.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Token {
    @Override
    public String toString() {
        return "Token{" +
                "Id=" + Id +
                ", token='" + token + '\'' +
                ", tokenType=" + tokenType +
                ", expired=" + expired +
                ", revoked=" + revoked +
                ", user=" + user +
                '}';
    }

    @Id
    @GeneratedValue
    private Integer Id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private Boolean expired;
    private Boolean revoked;
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
}
