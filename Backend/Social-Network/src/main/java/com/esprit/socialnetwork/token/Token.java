package com.esprit.socialnetwork.token;

import com.esprit.socialnetwork.user.User;
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
        return "token{" +
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
