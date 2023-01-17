package org.devok.smarthome.voicerecognition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class ModelTraining {
    final static Configuration configuration = new Configuration();

    public static void recordVoiceAndCreateFiles() {

        configuration
                .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration
                .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration
                .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");


        final AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
        TargetDataLine microphone;
        final File destinationFile = new File("dst.wav");

        try {
            final DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);

            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            int numBytesRead;
            final int CHUNK_SIZE = 1024;
            final byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();
            int bytesRead = 0;
            System.out.println("Recording has started!");
            while (bytesRead < 100000) {
                numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                bytesRead += numBytesRead;
                out.write(data, 0, numBytesRead);
            }
            microphone.close();

            final byte[] audioData = out.toByteArray();
            final ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
            final AudioInputStream audioInputStream = new AudioInputStream(bais, format,
                    audioData.length / format.getFrameSize());


            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, destinationFile);
            audioInputStream.close();
            out.close();
        } catch (final LineUnavailableException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void testModel() {
        try {
            System.out.println("Testing the Model..");
            final StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(
                    configuration);
            final InputStream stream = new FileInputStream(new File("dst.wav"));//dst.wav

            recognizer.startRecognition(stream);
            SpeechResult result;
            while ((result = recognizer.getResult()) != null) {
                System.out.format("Hypothesis: %s\n", result.getHypothesis());
            }
            recognizer.stopRecognition();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
