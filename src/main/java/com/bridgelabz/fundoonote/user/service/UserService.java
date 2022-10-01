package com.bridgelabz.fundoonote.user.service;

import com.bridgelabz.fundoonote.user.configuration.RabbitMQSender;
import com.bridgelabz.fundoonote.user.dto.LoginDTO;
import com.bridgelabz.fundoonote.user.dto.UserDTO;
import com.bridgelabz.fundoonote.user.exception.GlobalException;
import com.bridgelabz.fundoonote.user.model.User;
import com.bridgelabz.fundoonote.user.repository.UserRepository;
import com.bridgelabz.fundoonote.user.util.MailObject;
import com.bridgelabz.fundoonote.user.util.TokenUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RabbitMQSender rabbitMQSender;
    @Autowired
    private MailObject mailObject;
    @Autowired
    private RabbitTemplate template;

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserDataById(String token) {
        long id = tokenUtil.decodeToken(token);
        return userRepository.findById(id).orElseThrow(() -> new GlobalException("User Id With " +
                id + " Does Not Exist"));
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        User userData = new User(userDTO);
        userRepository.save(userData);
        String token = tokenUtil.createToken(userData.getUserId());
        userData.setToken(token);
        mailObject.setEmail(userData.getEmail());
        mailObject.setMessage("registration Verification link" +token );
        mailObject.setSubject("Verification");
        rabbitMQSender.send(mailObject);

        return userRepository.save(userData);
    }

    @Override
    public User verify(String token) {
        User user = this.getUserDataById(token);
        user.setVerify(true);
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(String token, UserDTO userDTO) {
        long id = tokenUtil.decodeToken(token);
        if (userRepository.findById(id).isPresent()){
            User user = new User(userDTO);
            userRepository.save(user);
            return user;
        }
        else {
            throw new GlobalException("Id not found ");
        }
    }

    @Override
    public String deleteUser(String token) {
        long ID = tokenUtil.decodeToken(token);
        Optional<User> user = userRepository.findById(ID);
        if (user.isPresent()){
            userRepository.deleteById(ID);
            return "Data Deleted";
        }
        throw new GlobalException("User id " + ID +" is not found");
    }

    @Override
    public String Search(LoginDTO loginDTO) {
        User userDetails=userRepository.find(loginDTO.getEmail(), loginDTO.getPassword());
        if(userDetails == null)
        {
            throw new GlobalException(loginDTO.getEmail() + " not found!");
        }
        return "USer Logged in Successfully";
    }

//    @Override
//    public String resetPassword(String email, UserDTO userDTO) {
//        User registrationDetails = userRepository.findPass(email);
//        if (registrationDetails != null) {
//            registrationDetails.setPassword(userDTO.getPassword());
//            userRepository.save(registrationDetails);
//            return "Password Updated";
//        } else {
//            return "Wrong Email Id Provide !!!";
//        }
//    }
}
