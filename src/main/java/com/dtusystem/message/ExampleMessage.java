package com.dtusystem.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExampleMessage extends Message {
    int code;
    String message;

    @Override
    public int getMessageCode() {
        return Message.EXAMPLE_MESSAGE;
    }
}
