package org.devok.smarthome;

import java.io.IOException;

import org.devok.smarthome.voicerecognition.ModelTraining;

public class Main {
    public static void main(String[] args) {
        System.out.println("Voice Recognition running!");
        //   VoiceRecognition.openChromeTab();
        ModelTraining.recordVoiceAndCreateFiles();
        ModelTraining.testModel();
    }
}