package com.bridgelabz.fundoonote.user.service;

import com.bridgelabz.fundoonote.user.dto.LoginDTO;
import com.bridgelabz.fundoonote.user.dto.UserDTO;
import com.bridgelabz.fundoonote.user.model.User;

import java.util.List;

public interface IUserService {


    List<User> getUser();

    User getUserDataById(String token);

    User registerUser(UserDTO userDTO);

    User verify(String token);

    User updateUser(String token, UserDTO userDTO);

    String deleteUser(String token);

    String Search(LoginDTO loginDTO);

//    String resetPassword(String email, UserDTO userDTO);
}
