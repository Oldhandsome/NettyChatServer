package com.dtusystem.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class LoginRequest extends Message {
    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int getMessageCode() {
        return LOGIN_REQUEST;
    }
}
