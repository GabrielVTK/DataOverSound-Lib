package br.com.dataoversound.utils;

import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;

public class QPSKUtils {

    public static List<Complex> convertBitsToComplex(String bits) {
        List<Complex> symbols = new ArrayList<>();

        // Mapeamento de cada par de bits para um símbolo QPSK
        for (int i = 0; i < bits.length() - 1; i += 2) {
            String pair = bits.substring(i, i + 2);
            Complex symbol = mapBitsToQPSKSymbol(pair);
            symbols.add(symbol);
        }

        return symbols;
    }

    private static Complex mapBitsToQPSKSymbol(String bits) {
        switch (bits) {
            case "00":
                return new Complex(Math.cos(Math.PI / 4), Math.sin(Math.PI / 4)); // 45 degrees
            case "01":
                return new Complex(Math.cos(3 * Math.PI / 4), Math.sin(3 * Math.PI / 4)); // 135 degrees
            case "11":
                return new Complex(Math.cos(5 * Math.PI / 4), Math.sin(5 * Math.PI / 4)); // 225 degrees (-135 degrees)
            case "10":
                return new Complex(Math.cos(7 * Math.PI / 4), Math.sin(7 * Math.PI / 4)); // 315 degrees (-45 degrees)
            default:
                throw new IllegalArgumentException("Invalid bit pair: " + bits);
        }
    }

}
