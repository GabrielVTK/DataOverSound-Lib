package br.com.dataoversound.components.error.detection;

import java.util.Objects;

public class ParityBitComponent {

    public String calculate(String bits) {
        int count = 0;

        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                count++;
            }
        }

        return count % 2 == 0 ? "00" : "01";
    }

    public boolean validate(String bits, String bitParity) {
        return Objects.equals(this.calculate(bits), bitParity);
    }

}
