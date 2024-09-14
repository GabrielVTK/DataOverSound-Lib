package components.error.detection;

import br.com.dataoversound.components.error.detection.ParityBitComponent;
import junit.framework.TestCase;
import org.junit.Test;

public class ParityBitComponentTest extends TestCase {

    ParityBitComponent parityBitComponent = new ParityBitComponent();

    @Test
    public void testBitParityOdd() {
        String bits = "00000001";
        String bitsExpected = bits + "01";

        String bitParity = this.parityBitComponent.calculate(bits);

        assertEquals(bitsExpected, bits + bitParity);
    }

    @Test
    public void testBitParityEven() {
        String bits = "00000011";
        String bitsExpected = bits + "00";

        String bitParity = this.parityBitComponent.calculate(bits);

        assertEquals(bitsExpected, bits + bitParity);
    }

    @Test
    public void testValidateBitParityTrue() {
        String bits = "00000011";
        String bitParity = "00";

        assertEquals(true, this.parityBitComponent.validate(bits, bitParity));
    }

    @Test
    public void testValidateBitParityFalse() {
        String bits = "00000011";
        String bitParity = "01";

        assertEquals(false, this.parityBitComponent.validate(bits, bitParity));
    }

}
