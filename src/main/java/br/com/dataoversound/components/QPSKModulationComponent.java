package br.com.dataoversound.components;

import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.utils.QPSKUtils;
import br.com.dataoversound.utils.Utils;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;

public class QPSKModulationComponent {

    private QPSKParameters parameters;

    private QPSKPreambleComponent preambleComponent;

    public QPSKModulationComponent(QPSKParameters parameters) {
        this.parameters = parameters;
        this.preambleComponent = new QPSKPreambleComponent(this.parameters);
    }

    public double[] modulateBits(String bits) {
        List<Complex> symbols = QPSKUtils.convertBitsToComplex(bits);

        double[] preamble = this.preambleComponent.generatePreamble();

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

        double[] signal = Utils.concatArray(preamble, modulatedSignal.stream().mapToDouble(Double::doubleValue).toArray());

        return signal;
    }

}
