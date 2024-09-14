package br.com.dataoversound.configs;

public class QPSKParameters extends Parameters {

    private int samplePerSymbol;

    public QPSKParameters(float sampleRate, float carrierFrequency, float carrierAmplitude, int samplePerSymbol) throws Exception {
        super(sampleRate, carrierFrequency, carrierAmplitude);
        this.setSamplePerSymbol(samplePerSymbol);
    }

    public int getSamplePerSymbol() {
        return this.samplePerSymbol;
    }

    public void setSamplePerSymbol(int samplePerSymbol) throws Exception {
        if (samplePerSymbol <= 0) {
            throw new Exception("Número de amostras por símbolo deve ser maior que zero.");
        }
        this.samplePerSymbol = samplePerSymbol;
    }
}
