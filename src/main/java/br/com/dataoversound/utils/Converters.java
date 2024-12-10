package br.com.dataoversound.utils;

public class Converters {

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

    public static String convertBinaryToString(String bits) {
        StringBuilder result = new StringBuilder();
        int length = bits.length();
        for (int i = 0; i < length; i += 8) {
            if (i + 8 <= length) {
                result.append((char) Integer.parseInt(bits.substring(i, i + 8), 2));
            }
        }

        return result.toString();
    }

    public static byte[] doubleToByte(double[] signal) {
        short[] signalShort;
        byte[] signalByte;

        signalShort = Converters.doubleToShort(signal);
        signalByte = Converters.shortToByte(signalShort);

        return signalByte;
    }

    public static short[] doubleToShort(double[] arrDouble) {
        short[] arrShort = new short[arrDouble.length];
        for (int i = 0; i < arrDouble.length; i++) {

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

    public static double[] concatArray(double[] array1, double[] array2) {
        // Cria um novo array com o tamanho somado dos dois arrays
        double[] newArray = new double[array1.length + array2.length];

        // Copia os elementos do primeiro array para o novo array
        System.arraycopy(array1, 0, newArray, 0, array1.length);

        // Copia os elementos do segundo array para o novo array
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        // Retorna o array unificado
        return newArray;
    }

    public static String convertIntegerToBinary(int input) {
        return String.format("%8s", Integer.toBinaryString(input)).replaceAll(" ", "0");
    }

}
