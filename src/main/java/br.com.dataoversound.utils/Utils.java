package br.com.dataoversound.utils;

public class Utils {

    public static String convertStringToBinary(String input) {
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))
                            .replaceAll(" ", "0")
            );
        }

        return result.toString();
    }

    public static String convertBinaryToString(String input) {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < input.length(); i += 8){
            result.append((char)Integer.parseInt(input.substring(i, i + 8), 2));
        }

        return result.toString();
    }

    public static byte[] doubleToByte(double[] signal) {
        short[] signalShort;
        byte[] signalByte;

        signalShort = Utils.doubleToShort(signal);
        signalByte = Utils.shortToByte(signalShort);

        return signalByte;
    }

    public static short[] doubleToShort(double[] arrDouble) {
        short[] arrShort = new short[arrDouble.length];
        for(int i = 0; i < arrDouble.length; i++) {

            double value = arrDouble[i];

            // Limitando o valor ao intervalo [-1.0, 1.0]
            if (value > 1.0) {
                value = 1.0;
            } else if (value < -1.0) {
                value = -1.0;
            }

            short shortValue = (short) (Short.MAX_VALUE * value);

            arrShort[i] = shortValue;
        }
        return arrShort;
    }

    public static byte[] shortToByte(short[] arrShort) {
        byte[] arrByte = new byte[arrShort.length * 2];

        for (int i = 0; i < arrShort.length; i++) {
            arrByte[i * 2] = (byte) (arrShort[i] & 0xff);
            arrByte[i * 2 + 1] = (byte) ((arrShort[i] >> 8) & 0xff);
        }

        return arrByte;
    }

}
