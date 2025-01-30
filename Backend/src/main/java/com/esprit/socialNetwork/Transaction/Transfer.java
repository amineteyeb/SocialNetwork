package com.esprit.socialNetwork.Transaction;

import com.esprit.socialNetwork.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter

public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transferDate;

    // other fields and getters/setters
}
