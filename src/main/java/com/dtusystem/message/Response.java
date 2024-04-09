package com.dtusystem.message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@Getter
@Setter
public class Response <T> extends Message {
    private boolean success;
    private String reason;
    private T data;

    public Response(boolean success, String reason, T data) {
        this.success = success;
        this.reason = reason;
        this.data = data;
    }

    @Override
    public int getMessageCode() {
        return RESPONSE;
    }
}
