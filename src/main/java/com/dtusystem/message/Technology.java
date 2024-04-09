package com.dtusystem.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Technology extends Message {
    Integer fanPressure;
    Integer sectorPressure;
    Integer muzzlePressure;

    @Override
    public int getMessageCode() {
        return Message.TECHNOLOGY;
    }
}
