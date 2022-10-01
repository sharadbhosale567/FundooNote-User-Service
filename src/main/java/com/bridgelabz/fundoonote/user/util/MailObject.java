package com.bridgelabz.fundoonote.user.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class MailObject implements Serializable {
    private static final long serialVersionUID = 1L;
    String email;
    String subject;
    String message;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
