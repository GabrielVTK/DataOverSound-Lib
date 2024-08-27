package br.com.dataoversound.configs;

abstract class Parameters {

    // Audio Parameters
    private float sampleRate;

    // Carrier Parameters
    private float carrierFrequency;
    private float carrierAmplitude;

    public Parameters(float sampleRate, float carrierFrequency, float carrierAmplitude) {
        this.setSampleRate(sampleRate);
        this.setCarrierFrequency(carrierFrequency);
        this.setCarrierAmplitude(carrierAmplitude);
    }

    public float getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(float sampleRate) {
        this.sampleRate = sampleRate;
    }

    public float getCarrierFrequency() {
        return carrierFrequency;
    }

    public void setCarrierFrequency(float carrierFrequency) {
        this.carrierFrequency = carrierFrequency;
    }

    public float getCarrierAmplitude() {
        return carrierAmplitude;
    }

    public void setCarrierAmplitude(float carrierAmplitude) {
        this.carrierAmplitude = carrierAmplitude;
    }
}
