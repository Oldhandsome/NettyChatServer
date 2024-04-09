package com.dtusystem.message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@Getter
@Setter
public class Response extends Message {
    private boolean success;
    private String reason;

    public Response(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public int getMessageCode() {
        return RESPONSE;
    }
}
