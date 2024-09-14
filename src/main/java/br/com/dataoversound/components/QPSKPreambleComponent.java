package br.com.dataoversound.components;

import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.utils.QPSKUtils;
import org.apache.commons.math3.complex.Complex;
import org.jtransforms.fft.DoubleFFT_1D;

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

        // Calcular a correlação
        double[] correlation = this.correlateWithFFT(signal, knownPreamble);

        // Encontrar o índice da correlação máxima
        int maxIndex = -1;
        double maxCorrelation = 0;
        for (int i = 0; i < correlation.length; i++) {
            if (correlation[i] > maxCorrelation) {
                maxCorrelation = correlation[i];
                maxIndex = i;
            }
        }

        if (maxIndex != -1) {
            maxIndex += knownPreamble.length;
        }

        return maxIndex;
    }

    private double[] correlateWithFFT(double[] signal, double[] knownPreamble) {
        int n = signal.length + knownPreamble.length - 1; // Tamanho da convolução/correlação
        int fftSize = (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2))); // Encontre a próxima potência de 2 para eficiência

        // Prepare o sinal e o préambulo, zeropad até o comprimento do FFT
        double[] paddedSignal = new double[fftSize];
        double[] paddedPreamble = new double[fftSize];

        System.arraycopy(signal, 0, paddedSignal, 0, signal.length);
        System.arraycopy(knownPreamble, 0, paddedPreamble, 0, knownPreamble.length);

        // Calcular a FFT dos dois sinais
        DoubleFFT_1D fft = new DoubleFFT_1D(fftSize);
        fft.realForward(paddedSignal);     // FFT do sinal
        fft.realForward(paddedPreamble);   // FFT do préambulo

        // Multiplicar pela conjugada da FFT do préambulo (parte real e imaginária)
        for (int i = 0; i < fftSize / 2; i++) {
            double realSignal = paddedSignal[2 * i];
            double imagSignal = paddedSignal[2 * i + 1];

            double realPreamble = paddedPreamble[2 * i];
            double imagPreamble = paddedPreamble[2 * i + 1];

            // Multiplicar (real + i * imag) * (real - i * imag) -> a forma conjugada
            paddedSignal[2 * i] = realSignal * realPreamble + imagSignal * imagPreamble; // Real
            paddedSignal[2 * i + 1] = imagSignal * realPreamble - realSignal * imagPreamble; // Imaginário
        }

        // Transformada inversa de volta ao domínio do tempo
        fft.realInverse(paddedSignal, true);

        // O resultado da correlação será armazenado nos primeiros n elementos
        double[] correlation = new double[n];
        System.arraycopy(paddedSignal, 0, correlation, 0, n);

        return correlation;
    }

}
