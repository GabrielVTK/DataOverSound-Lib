package br.com.dataoversound.utils;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AudioFile {

    public static double[] readWavFile(String filePath) {
        try {
            // Abre o arquivo WAV
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filePath));
            AudioFormat format = ais.getFormat();

            // Verifica se o formato do arquivo é compatível
            if (format.getSampleRate() != 44100 || format.getSampleSizeInBits() != 16 || format.getChannels() != 1) {
                throw new IllegalArgumentException("Formato de arquivo WAV inválido. A taxa de amostragem deve ser 44100 Hz, tamanho da amostra de 16 bits e um canal.");
            }

            int numFrames = (int) ais.getFrameLength();

            // Lê os dados do arquivo WAV em um buffer de bytes
//            byte[] audioData = ais.readAllBytes();
            byte[] audioData = new byte[numFrames * format.getFrameSize()];
            int bytesRead = ais.read(audioData);

            // Converte os bytes para amostras de áudio de 16 bits e depois para valores double
            double[] samples = new double[audioData.length / 2];
            for (int i = 0; i < samples.length; i++) {
                // Converte os bytes em um valor inteiro de 16 bits
                short value = (short) (((audioData[i * 2 + 1] & 0xFF) << 8) | (audioData[i * 2] & 0xFF));
                // Normaliza o valor para o intervalo de -1 a 1
                samples[i] = value / 32768.0;
            }

            return samples;
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File write(String filename, byte[] buffer, float sampleRate) {

        String path = filename;

        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File out = new File(path);

        AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, buffer.length);

        try {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
            audioInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("Arquivo wav gerado");

        return out;
    }

    private void playSound(File sound) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
