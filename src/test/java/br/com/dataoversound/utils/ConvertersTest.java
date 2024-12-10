package br.com.dataoversound.utils;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ConvertersTest {

    @Test
    public void testConvertStringToBinary() {
        String bits = Converters.convertStringToBinary("Teste");
        assertEquals("0101010001100101011100110111010001100101", bits);
    }

    @Test
    public void testConvertBinaryToString() {
        String text = Converters.convertBinaryToString("0101010001100101011100110111010001100101");
        assertEquals("Teste", text);
    }

    @Test
    public void testDoubleToByte() {
        // Dados de entrada
        double[] input = {0.0, 0.5, -0.5, 1.0, -1.0};

        // Convertendo o input para short e depois para byte
        short[] expectedShort = Converters.doubleToShort(input);
        byte[] expectedByte = Converters.shortToByte(expectedShort);

        // Quando o método é chamado
        byte[] result = Converters.doubleToByte(input);

        // Então o resultado deve ser igual ao esperado
        assertArrayEquals(expectedByte, result);
    }

    @Test
    public void testDoubleToByteWithEmptyArray() {
        // Dados de entrada
        double[] input = {};

        // O resultado esperado é um array vazio
        byte[] expected = new byte[0];

        // Quando o método é chamado
        byte[] result = Converters.doubleToByte(input);

        // Então o resultado deve ser igual ao esperado
        assertArrayEquals(expected, result);
    }

    @Test
    public void testDoubleToByteWithLargeValues() {
        // Dados de entrada com valores próximos aos limites
        double[] input = {0.999999, -0.999999};

        // Convertendo o input para short e depois para byte
        short[] expectedShort = Converters.doubleToShort(input);
        byte[] expectedByte = Converters.shortToByte(expectedShort);

        // Quando o método é chamado
        byte[] result = Converters.doubleToByte(input);

        // Então o resultado deve ser igual ao esperado
        assertArrayEquals(expectedByte, result);
    }

    @Test
    public void testDoubleToByteWithMinMaxValues() {
        // Dados de entrada com valores no limite
        double[] input = {1.0, -1.0};

        // Convertendo o input para short e depois para byte
        short[] expectedShort = Converters.doubleToShort(input);
        byte[] expectedByte = Converters.shortToByte(expectedShort);

        // Quando o método é chamado
        byte[] result = Converters.doubleToByte(input);

        // Então o resultado deve ser igual ao esperado
        assertArrayEquals(expectedByte, result);
    }

    @Test
    public void testDoubleToShort() {
        // Test case 1: Test with positive double values
        double[] input1 = {0.0, 0.5, 1.0};
        short[] expected1 = {0, 16383, 32767}; // 32767 * 0.0 = 0, 32767 * 0.5 ≈ 16383, 32767 * 1.0 = 32767
        short[] result1 = Converters.doubleToShort(input1);
        assertArrayEquals(expected1, result1);

        // Test case 2: Test with negative double values
        double[] input2 = {-0.5, -1.0};
        short[] expected2 = {-16383, -32767}; // 32767 * -0.5 ≈ -16383, 32767 * -1.0 = -32767
        short[] result2 = Converters.doubleToShort(input2);
        assertArrayEquals(expected2, result2);

        // Test case 3: Test with mixed positive and negative double values
        double[] input3 = {0.5, -0.25, 1.0, -1.0};
        short[] expected3 = {16383, -8191, 32767, -32767}; // 32767 * -0.25 ≈ -8191
        short[] result3 = Converters.doubleToShort(input3);
        assertArrayEquals(expected3, result3);

        // Test case 4: Test with edge cases (values close to -1.0 and 1.0)
        double[] input4 = {0.9999, -0.9999};
        short[] expected4 = {32763, -32763}; // Values close to max and min short
        short[] result4 = Converters.doubleToShort(input4);
        assertArrayEquals(expected4, result4);

        // Test case 5: Test with extreme double values
        double[] input5 = {Double.MAX_VALUE, Double.MIN_VALUE, -Double.MAX_VALUE, -Double.MIN_VALUE};
        short[] expected5 = {32767, 0, -32767, 0}; // Double.MAX_VALUE and Double.MIN_VALUE are out of the -1.0 to 1.0 range
        short[] result5 = Converters.doubleToShort(input5);
        assertArrayEquals(expected5, result5);
    }

    @Test
    public void testShortToByte() {
        // Test case 1: Test with positive short values
        short[] input1 = {256, 512, 1024};
        byte[] expected1 = {0, 1, 0, 2, 0, 4};
        byte[] result1 = Converters.shortToByte(input1);
        assertArrayEquals(expected1, result1);

        // Test case 2: Test with negative short values
        short[] input2 = {-256, -512, -1024};
        byte[] expected2 = {0, -1, 0, -2, 0, -4};
        byte[] result2 = Converters.shortToByte(input2);
        assertArrayEquals(expected2, result2);

        // Test case 3: Test with mix of positive and negative short values
        short[] input3 = {256, -512, 1024};
        byte[] expected3 = {0, 1, 0, -2, 0, 4};
        byte[] result3 = Converters.shortToByte(input3);
        assertArrayEquals(expected3, result3);

        // Test case 4: Test with zero values
        short[] input4 = {0, 0, 0};
        byte[] expected4 = {0, 0, 0, 0, 0, 0};
        byte[] result4 = Converters.shortToByte(input4);
        assertArrayEquals(expected4, result4);

        // Test case 5: Test with max and min short values
        short[] input5 = {Short.MAX_VALUE, Short.MIN_VALUE};
        byte[] expected5 = {(byte) 0xFF, 0x7F, 0x00, (byte) 0x80};
        byte[] result5 = Converters.shortToByte(input5);
        assertArrayEquals(expected5, result5);
    }

}
