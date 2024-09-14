package services;

import br.com.dataoversound.components.QPSKPreambleComponent;
import br.com.dataoversound.components.error.detection.ParityBitComponent;
import br.com.dataoversound.configs.QPSKParameters;
import br.com.dataoversound.services.QPSKDemodulationService;
import br.com.dataoversound.services.QPSKModulationService;
import br.com.dataoversound.utils.AudioFile;
import br.com.dataoversound.utils.Utils;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

public class QPSKDemodulationTest extends TestCase {

    QPSKParameters qpskParameters;
    QPSKModulationService qpskModulationService;
    QPSKDemodulationService qpskDemodulationService;
    QPSKPreambleComponent preambleComponent;

    @Mock
    ParityBitComponent parityBitComponent = new ParityBitComponent();

    String message = "Gabriel";
    float carrierFrequency = 440f;

    @Override
    protected void setUp() throws Exception {
        this.qpskParameters = new QPSKParameters(
                44100.0f,
                carrierFrequency,
                1.0f,
                (int) (carrierFrequency * 10)
        );

        this.qpskModulationService = new QPSKModulationService(qpskParameters);
        this.qpskDemodulationService = new QPSKDemodulationService(qpskParameters);
        this.preambleComponent = new QPSKPreambleComponent(qpskParameters);
    }

    @Test
    public void testDemodulateQPSK() {

        double[] signal = this.qpskModulationService.modulateMessage("Gabriel");

        double[] signalWithCleanSignal = Utils.concatArray(generateCleanSignal(0.5f), signal);
        signalWithCleanSignal = Utils.concatArray(signalWithCleanSignal, generateCleanSignal(0.5f));

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

        carrierQPSKShort = Utils.doubleToShort(signal);
        carrierQPSKByte = Utils.shortToByte(carrierQPSKShort);

        AudioFile.write("./test.wav", carrierQPSKByte, qpskParameters.getSampleRate()); // Save signal in wav file
    }

}
