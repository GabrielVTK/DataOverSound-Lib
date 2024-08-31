package br.com.dataoversound.components;

import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.utils.QPSKUtils;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;


public class QPSKPreambleComponent {

    private QPSKParameters qpskParameters;

    public QPSKPreambleComponent(QPSKParameters qpskParameters) {
        this.qpskParameters = qpskParameters;
    }

    public double[] generatePreamble() {
        List<Complex> symbols = QPSKUtils.convertBitsToComplex("11111111");

        // Lista para armazenar as amostras do sinal modulado
        List<Double> modulatedSignal = new ArrayList<>();

        // Modulação do sinal QPSK
        for (Complex symbol : symbols) {
            for (int i = 0; i < this.qpskParameters.getSamplePerSymbol(); i++) {
                // Fase do símbolo QPSK (argumento do número complexo)
                double phase = symbol.getArgument();

                // Amplitude do símbolo QPSK (módulo do número complexo)
                double amplitude = symbol.abs();

                // Amostra do sinal modulado
                double sample = this.qpskParameters.getCarrierAmplitude() * amplitude * Math.cos(2 * Math.PI * this.qpskParameters.getCarrierFrequency() * i / this.qpskParameters.getSampleRate() + phase);
                modulatedSignal.add(sample);
            }
        }

        return modulatedSignal.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public int detectPreamble(double[] signal) {
        double[] knownPreamble = this.generatePreamble();
        double[] correlation = new double[signal.length - knownPreamble.length + 1];

        // Calcular a correlação
        for (int i = 0; i <= signal.length - knownPreamble.length; i++) {
            correlation[i] = 0;
            for (int j = 0; j < knownPreamble.length; j++) {
                correlation[i] += signal[i + j] * knownPreamble[j];
            }
        }

        // Encontrar o índice da correlação máxima
        int maxIndex = -1;
        double maxCorrelation = 0;
        for (int i = 0; i < correlation.length; i++) {
            if (correlation[i] > maxCorrelation) {
                maxCorrelation = correlation[i];
                maxIndex = i;
            }
        }

        if(maxIndex != -1) {
            maxIndex += knownPreamble.length;
        }

        return maxIndex;
    }

}
