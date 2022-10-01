package com.bridgelabz.fundoonote.user.controller;

import com.bridgelabz.fundoonote.user.dto.LoginDTO;
import com.bridgelabz.fundoonote.user.dto.ResponseDTO;
import com.bridgelabz.fundoonote.user.dto.UserDTO;
import com.bridgelabz.fundoonote.user.model.User;
import com.bridgelabz.fundoonote.user.service.IUserService;
import com.bridgelabz.fundoonote.user.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    IUserService iUserService;

    @Autowired
    TokenUtil tokenUtil;

    List<User> userList= new ArrayList<>();

    @RequestMapping("getAll")
    public ResponseEntity<ResponseDTO> getUser() {
        userList = iUserService.getUser();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", userList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getUser/{token}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable String token) {
        User user = iUserService.getUserDataById(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call For ID Successful", user);
        return new ResponseEntity<ResponseDTO>(responseDTO , HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<ResponseDTO> registerUser( @RequestBody UserDTO userDTO) {
        User user = iUserService.registerUser(userDTO);
        ResponseDTO responseDTO= new ResponseDTO("Created User Successfully", user);
        return new ResponseEntity<ResponseDTO>(responseDTO , HttpStatus.OK);
    }

    @PutMapping("/updateUser/{token}")
    public ResponseEntity<ResponseDTO> updateUserData(@PathVariable String token,@RequestBody UserDTO userDTO) {
        User user = iUserService.updateUser(token,userDTO);
        ResponseDTO responseDTO= new ResponseDTO("Updated User Details Successfully", user);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{token}")
    public ResponseEntity <ResponseDTO> deleteUserData(@PathVariable String token) {
        iUserService.deleteUser(token);
        ResponseDTO responseDTO= new ResponseDTO("Deleted Successfully", "Deleted id: "+token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("/loginCheck")
    public ResponseEntity<ResponseDTO> getEmailAndPass(@RequestBody LoginDTO loginDTO)
    {
        ResponseDTO respDTO= new ResponseDTO("Get Call For User Successful", iUserService.Search(loginDTO));
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token){
        ResponseDTO responseDTO = new ResponseDTO("User verified successfully", iUserService.verify(token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

//    @PostMapping("/forgotPass")
//    public ResponseEntity<ResponseDTO> forgotPass(@RequestParam String email,@RequestBody UserDTO userDTO){
//        ResponseDTO responseDTO=new ResponseDTO("Your Password is ",iUserService.resetPassword(email,userDTO));
//        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
//    }
}
