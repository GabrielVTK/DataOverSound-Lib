package br.com.dataoversound.services;

import br.com.dataoversound.components.QPSKPreambleComponent;
import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.utils.Utils;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QPSKDemodulationService {

    private QPSKParameters parameters;

    private QPSKPreambleComponent preambleComponent;

    public QPSKDemodulationService(QPSKParameters parameters) {
        this.parameters = parameters;
        this.preambleComponent = new QPSKPreambleComponent(this.parameters);
    }

    public String demodulateMessage(double[] signal) {

        int preambleEndIndex = this.preambleComponent.detectPreamble(signal);
        if (preambleEndIndex != -1) {
            System.out.println("Preamble detected at index: " + preambleEndIndex);
        } else {
            System.out.println("Preamble not detected");
            return "Erro na detecção do preâmbulo";
        }

        double[] newSignal = Arrays.copyOfRange(signal, preambleEndIndex, signal.length);

        List<Complex> demodulatedSymbols = this.demodulateQPSK(newSignal);

        List<Complex> symbolsNumber = demodulatedSymbols.subList(0, 4);
        String symbolNumberBits = this.bitsFromDemodulatedSymbols(symbolsNumber);
        Integer symbolNumberInt = Integer.parseInt(symbolNumberBits, 2);

        if(demodulatedSymbols.size() < symbolNumberInt + 4) {
            return "Erro na demodulação da mensagem, número de simbolos insuficiente para mensagem";
        }

        List<Complex> symbolsMessage = demodulatedSymbols.subList(4, symbolNumberInt + 4);

        String bitsOutput = QPSKDemodulationService.bitsFromDemodulatedSymbols(symbolsMessage);
        return Utils.convertBinaryToString(bitsOutput);
    }

    public List<Complex> demodulateQPSK(double[] receivedSignal) {
        List<Complex> demodulatedSymbols = new ArrayList<>();
 
        double carrierFrequency = this.parameters.getCarrierFrequency();
        double sampleRate = this.parameters.getSampleRate();
        int samplesPerSymbol = this.parameters.getSamplePerSymbol();

        int numSymbols = receivedSignal.length / samplesPerSymbol;

        // Pré-computa o fator de frequência
        double angularFrequency = 2 * Math.PI * carrierFrequency;

        long tempoInicial = System.currentTimeMillis();

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

        System.out.println("o metodo demodulateQPSK executou em " + (System.currentTimeMillis() - tempoInicial)); // 463 450

        return demodulatedSymbols;
    }

    public static String bitsFromDemodulatedSymbols(List<Complex> demodulatedSymbols) {
        StringBuilder demodulatedBits = new StringBuilder();

        for (Complex symbol : demodulatedSymbols) {
            String bits = mapPhaseToBits(symbol);
            demodulatedBits.append(bits);
        }

        return demodulatedBits.toString();
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