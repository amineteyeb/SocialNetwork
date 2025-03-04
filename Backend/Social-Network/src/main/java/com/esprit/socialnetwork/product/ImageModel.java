package com.esprit.socialnetwork.product;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    @Column(length=5000000)
    private  byte [] picByte;


    public ImageModel(String name, String type, byte[] picByte) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }


}
