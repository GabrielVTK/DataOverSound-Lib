package br.com.dataoversound.services;

import br.com.dataoversound.components.QPSKPreambleComponent;
import br.com.dataoversound.components.error.detection.BitParityComponent;
import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.utils.QPSKUtils;
import br.com.dataoversound.utils.Utils;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class QPSKModulationService {

    private QPSKParameters parameters;
    private QPSKPreambleComponent preambleComponent;
    private BitParityComponent bitParityComponent;

    private double[] signal;

    public QPSKModulationService(QPSKParameters parameters) {
        this.parameters = parameters;
        this.preambleComponent = new QPSKPreambleComponent(this.parameters);
        this.bitParityComponent = new BitParityComponent();
        this.signal = new double[0];
    }

    public double[] modulateMessage(String message) {
        this.signal = new double[0];
        String bits = Utils.convertStringToBinary(message);
        String symbolsNumber = Utils.convertIntegerToBinary(bits.length() / 2);
        String bitParity = this.bitParityComponent.calculate(bits);

        double[] preambleSignal = preambleComponent.generatePreamble();
        double[] symbolNumberSignal = this.modulateBits(symbolsNumber);
        double[] messageSignal = this.modulateBits(bits);
        double[] bitParitySignal = this.modulateBits(bitParity);

        this.signal = Utils.concatArray(this.signal, preambleSignal);
        this.signal = Utils.concatArray(this.signal, symbolNumberSignal);
        this.signal = Utils.concatArray(this.signal, messageSignal);
        this.signal = Utils.concatArray(this.signal, bitParitySignal);

        return this.signal;
    }

    public double[] modulateBits(String bits) {
        List<Complex> symbols = QPSKUtils.convertBitsToComplex(bits);

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

}
