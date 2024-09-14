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

    public static String convertComplexToBits(List<Complex> demodulatedSymbols) {
        StringBuilder demodulatedBits = new StringBuilder();

        for (Complex symbol : demodulatedSymbols) {
            String bits = mapPhaseToBits(symbol);
            demodulatedBits.append(bits);
        }

        return demodulatedBits.toString();
    }

    private static Complex mapBitsToQPSKSymbol(String bits) {
        switch (bits) {
            case "00":
                return new Complex(Math.cos(Math.PI / 4), Math.sin(Math.PI / 4)); // 45º
            case "01":
                return new Complex(Math.cos(3 * Math.PI / 4), Math.sin(3 * Math.PI / 4)); // 135º
            case "11":
                return new Complex(Math.cos(5 * Math.PI / 4), Math.sin(5 * Math.PI / 4)); // 225º (-135º)
            case "10":
                return new Complex(Math.cos(7 * Math.PI / 4), Math.sin(7 * Math.PI / 4)); // 315º (-45º)
            default:
                throw new IllegalArgumentException("Par de bits inválido: " + bits);
        }
    }

    private static String mapPhaseToBits(Complex symbol) {
        double phase = symbol.getArgument();

        // Normaliza a fase para estar no intervalo de 0 a 2*PI
        phase = (phase + 2 * Math.PI) % (2 * Math.PI);

        // Mapeamento da fase para os bits correspondentes
        if (phase >= 0 && phase < Math.PI / 2) {
            return "00"; // 45 graus
        } else if (phase >= Math.PI / 2 && phase < Math.PI) {
            return "01"; // 135 graus
        } else if (phase >= Math.PI && phase < 3 * Math.PI / 2) {
            return "11"; // 225 graus
        } else {
            return "10"; // 315 graus
        }
    }

}
