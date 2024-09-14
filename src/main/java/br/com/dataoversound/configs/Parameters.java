package br.com.dataoversound.configs;

public class Parameters {

    // Audio Parameters
    private float sampleRate;

    // Carrier Parameters
    private float carrierFrequency;
    private float carrierAmplitude;

    public Parameters(float sampleRate, float carrierFrequency, float carrierAmplitude) throws Exception {
        this.setSampleRate(sampleRate);
        this.setCarrierFrequency(carrierFrequency);
        this.setCarrierAmplitude(carrierAmplitude);
    }

    public float getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(float sampleRate) throws Exception {
        if (sampleRate <= 0) {
            throw new Exception("Taxa de amostragem deve ser maior que zero.");
        }
        this.sampleRate = sampleRate;
    }

    public float getCarrierFrequency() {
        return carrierFrequency;
    }

    public void setCarrierFrequency(float carrierFrequency) throws Exception {
        if (carrierFrequency <= 0) {
            throw new Exception("Frequência portadora deve ser maior que zero.");
        }
        this.carrierFrequency = carrierFrequency;
    }

    public float getCarrierAmplitude() {
        return carrierAmplitude;
    }

    public void setCarrierAmplitude(float carrierAmplitude) throws Exception {
        if (carrierAmplitude <= 0 || carrierAmplitude > 1) {
            throw new Exception("Amplitude deve ser maior que zero e menor ou igual a um.");
        }
        this.carrierAmplitude = carrierAmplitude;
    }
}
