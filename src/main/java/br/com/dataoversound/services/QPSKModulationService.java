package br.com.dataoversound.services;

import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.utils.Utils;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class QPSKModulationService {

    private QPSKParameters parameters;

    public double[] modulateMessage(String message) {
        String bits = Utils.convertStringToBinary(message);
        return this.modulateBits(bits);
    }

    public double[] modulateBits(String bits) {
        List<Complex> symbols = convertBitsToComplex(bits);

        // Lista para armazenar as amostras do sinal modulado
        List<Double> modulatedSignal = new ArrayList<>();

        // Modulação do sinal QPSK
        for (Complex symbol : symbols) {
            for (int i = 0; i < this.parameters.getSamplePerSymbol(); i++) {
                // Fase do símbolo QPSK (argumento do número complexo)
                double phase = symbol.getArgument();

                // Amplitude do símbolo QPSK (módulo do número complexo)
                double amplitude = symbol.abs();

                // Amostra do sinal modulado
                double sample = this.parameters.getCarrierAmplitude() * amplitude * Math.cos(2 * Math.PI * this.parameters.getCarrierFrequency() * i / this.parameters.getSampleRate() + phase);
                modulatedSignal.add(sample);
            }
        }

        return modulatedSignal.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private List<Complex> convertBitsToComplex(String bits) {
        List<Complex> symbols = new ArrayList<>();

        // Mapeamento de cada par de bits para um símbolo QPSK
        for (int i = 0; i < bits.length() - 1; i += 2) {
            String pair = bits.substring(i, i + 2);
            Complex symbol = mapBitsToQPSKSymbol(pair);
            symbols.add(symbol);
        }

        return symbols;
    }

    private Complex mapBitsToQPSKSymbol(String bits) {
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
