package org.devok.smarthome.voicerecognition;

import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class VoiceRecognition {
    public static void openChromeTab() {
        Configuration config = new Configuration();

        config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        // config.setDictionaryPath("src\\main\\resources\\commands.dic");
        // config.setLanguageModelPath("src\\main\\resources\\commands.lm");

        config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        try {
            LiveSpeechRecognizer speech = new LiveSpeechRecognizer(config);
            speech.startRecognition(true);

            SpeechResult speechResult;
            while ((speechResult = speech.getResult()) != null) {
                String voiceCommand = speechResult.getHypothesis();
                if (voiceCommand.equalsIgnoreCase("Open Chrome")) {
                    Runtime.getRuntime().exec("cmd.exe /c start chrome www.google.com");
                } else if (voiceCommand.equalsIgnoreCase("Close Chrome")) {
                    Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM chrome.exe");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}