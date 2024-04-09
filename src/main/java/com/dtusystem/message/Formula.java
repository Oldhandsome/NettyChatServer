package com.dtusystem.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Formula extends Message{
    Integer valveA;
    Integer valveB;
    Integer valveC;
    Integer proportionA;
    Integer proportionB;
    Integer proportionC;

    @Override
    public int getMessageCode() {
        return Message.FORMULA;
    }
}
