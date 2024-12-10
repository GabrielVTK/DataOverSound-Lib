package services;

import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.services.QPSKDemodulationService;
import br.com.dataoversound.services.QPSKModulationService;
import br.com.dataoversound.utils.AudioFile;
import br.com.dataoversound.utils.Converters;
import junit.framework.TestCase;
import org.junit.Test;

public class QPSKDemodulationTest extends TestCase {

    QPSKParameters qpskParameters;
    QPSKModulationService qpskModulationService;
    QPSKDemodulationService qpskDemodulationService;

    String message = "Teste";
    float sampleRate = 44100.0f;
    float carrierFrequency = 440f;

    @Override
    protected void setUp() throws Exception {
        this.qpskParameters = new QPSKParameters(
                sampleRate,
                carrierFrequency,
                0.9f,
                ((int) (sampleRate/carrierFrequency)) * 20
        );

        this.qpskModulationService = new QPSKModulationService(qpskParameters);
        this.qpskDemodulationService = new QPSKDemodulationService(qpskParameters);
    }

    @Test
    public void testDemodulateQPSK() {

        double[] signal = this.qpskModulationService.modulateMessage(message);

        double[] clearSignal = generateCleanSignal(0.5f);
        //clearSignal = new double[0];

        double[] signalWithCleanSignal = Converters.concatArray(clearSignal, signal);
        signalWithCleanSignal = Converters.concatArray(signalWithCleanSignal, clearSignal);

        generateFile(signalWithCleanSignal);

        String messageOutput = null;
        try {
            messageOutput = this.qpskDemodulationService.demodulateMessage(signalWithCleanSignal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(message, messageOutput);
    }

    @Test
    public void testModuleDemodule() {
        double[] signal = this.qpskModulationService.modulateMessage(message);
        String messageReceived = null;
        try {
            messageReceived = this.qpskDemodulationService.demodulateMessage(signal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(message, messageReceived);
    }

    private double[] generateCleanSignal(float seconds) {
        int numSamples = (int) (this.qpskParameters.getSampleRate() * seconds);
        double[] signal = new double[numSamples];

        for (int i = 0; i < numSamples; i++) {
            signal[i] = 0;
        }

        return signal;
    }

    private void generateFile(double[] signal) {
        short[] carrierQPSKShort  = new short[0];
        byte[] carrierQPSKByte = new byte[0];

        carrierQPSKShort = Converters.doubleToShort(signal);
        carrierQPSKByte = Converters.shortToByte(carrierQPSKShort);

        AudioFile.write("./test.wav", carrierQPSKByte, qpskParameters.getSampleRate()); // Save signal in wav file
    }

}
