package com.bridgelabz.fundoonote.user.model;

import com.bridgelabz.fundoonote.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String firstName;
    private  String lastname;
    private String email;
    private String password;
    private String token;
    private boolean verify;
    private boolean isVerified;

    public User(UserDTO userDTO){
        this.firstName=userDTO.firstName;
        this.lastname=userDTO.lastName;
        this.email=userDTO.email;
        this.password=userDTO.password;
    }

    public User(int id,UserDTO userDTO){
        this.userId=id;
        this.firstName=userDTO.firstName;
        this.lastname=userDTO.lastName;
        this.email=userDTO.email;
        this.password=userDTO.password;
    }

}
