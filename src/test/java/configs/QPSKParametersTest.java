package configs;

import br.com.dataoversound.configs.QPSKParameters;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class QPSKParametersTest {

    @Test
    public void testConstructor() {
        boolean thrown = false;

        try {
            QPSKParameters qpskParameters = new QPSKParameters(44100,440, 1, 1000);
        } catch (Exception e) {
            thrown = true;
        }

        assertEquals(false, thrown);
    }

    @Test
    public void exceptionSampleRateLessThanZero() {
        Exception exception = assertThrows(Exception.class, () ->
                new QPSKParameters(0,440, 1, 1000));

        assertEquals("Taxa de amostragem deve ser maior que zero.", exception.getMessage());
    }

    @Test
    public void exceptionCarrierFrequencyLessThanZero() {
        Exception exception = assertThrows(Exception.class, () ->
                new QPSKParameters(44100,0, 1, 1000));

        assertEquals("Frequência portadora deve ser maior que zero.", exception.getMessage());
    }

    @Test
    public void exceptionCarrierAmplitudeLessThanZero() {
        Exception exception = assertThrows(Exception.class, () ->
                new QPSKParameters(44100,440, 0, 1000));

        assertEquals("Amplitude deve ser maior que zero e menor ou igual a um.", exception.getMessage());
    }

    @Test
    public void exceptionCarrierAmplitudeGreaterThanOne() {
        Exception exception = assertThrows(Exception.class, () ->
                new QPSKParameters(44100,440, 2, 1000));

        assertEquals("Amplitude deve ser maior que zero e menor ou igual a um.", exception.getMessage());
    }

    @Test
    public void exceptionSamplePerSymbolLessThanZero() {
        Exception exception = assertThrows(Exception.class, () ->
                new QPSKParameters(44100,440, 1, 0));

        assertEquals("Número de amostras por símbolo deve ser maior que zero.", exception.getMessage());
    }

}
