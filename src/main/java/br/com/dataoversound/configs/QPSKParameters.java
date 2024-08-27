package br.com.dataoversound.configs;

public class QPSKParameters extends Parameters {

    private int samplePerSymbol;

    public QPSKParameters(float sampleRate, float carrierFrequency, float carrierAmplitude, int samplePerSymbol) {
        super(sampleRate, carrierFrequency, carrierAmplitude);
        this.samplePerSymbol = samplePerSymbol;
    }

    public int getSamplePerSymbol () {
        return this.samplePerSymbol;
    }

}
