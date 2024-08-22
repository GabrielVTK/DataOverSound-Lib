package br.com.dataoversound.utils;

import junit.framework.TestCase;
import org.junit.Test;

public class UtilsTest extends TestCase {

    @Test
    public void testConvertStringToBinary() {
        String bits = Utils.convertStringToBinary("Teste");
        assertEquals("0101010001100101011100110111010001100101", bits);
    }

    @Test
    public void testConvertBinaryToString() {
        String text = Utils.convertBinaryToString("0101010001100101011100110111010001100101");
        assertEquals("Teste", text);
    }

}
