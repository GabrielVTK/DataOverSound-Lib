package br.com.dataoversound.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProtocolModel {

    private String numberSymbol;

    private String message;

    private String parityBit;

    public String getProtocolBits() {
        return this.numberSymbol + this.message + this.parityBit;
    }

}
