package com.esprit.socialnetwork.client;

import com.esprit.socialnetwork.user.User;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
