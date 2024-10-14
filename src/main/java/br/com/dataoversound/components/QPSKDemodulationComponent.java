package br.com.dataoversound.components;

import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.utils.QPSKUtils;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QPSKDemodulationComponent {

    private QPSKParameters parameters;

    private QPSKPreambleComponent preambleComponent;

    public QPSKDemodulationComponent(QPSKParameters parameters) {
        this.parameters = parameters;
        this.preambleComponent = new QPSKPreambleComponent(this.parameters);
    }

    public String signalToBits(double[] signal) {

        int preambleEndIndex = this.preambleComponent.detectPreamble(signal);
        if (preambleEndIndex == -1) {
            return "Erro na detecção do preâmbulo";
        }
        System.out.println("Preamble detected at index: " + preambleEndIndex);

        double[] signalWithoutPreamble = Arrays.copyOfRange(signal, preambleEndIndex, signal.length);

        List<Complex> demodulatedSymbols = this.demodulateQPSK(signalWithoutPreamble);
        String bits = QPSKUtils.convertComplexToBits(demodulatedSymbols);

        System.out.println("bits: " + bits);

        return bits;
    }

    private List<Complex> demodulateQPSK(double[] receivedSignal) {
        List<Complex> demodulatedSymbols = new ArrayList<>();

        double carrierFrequency = this.parameters.getCarrierFrequency();
        double sampleRate = this.parameters.getSampleRate();
        int samplesPerSymbol = this.parameters.getSamplePerSymbol();

        int numSymbols = receivedSignal.length / samplesPerSymbol;

        // Pré-computa a frequência angular
        double angularFrequency = 2 * Math.PI * carrierFrequency;

        // Percorre o sinal recebido, demodulando cada símbolo QPSK
        for (int i = 0; i < numSymbols; i++) {
            double real = 0;
            double imag = 0;

            for (int j = 0; j < samplesPerSymbol; j++) {
                double t = j / sampleRate;
                int index = i * samplesPerSymbol + j;
                double sample = receivedSignal[index];

                real += sample * Math.cos(angularFrequency * t);
                imag -= sample * Math.sin(angularFrequency * t);
            }

            // Demodula o símbolo QPSK
            demodulatedSymbols.add(new Complex(real, imag));
        }

        return demodulatedSymbols;
    }

}
