package org.devok.smarthome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.devok.smarthome.voicerecognition.ModelTraining;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Voice Recognition running!");
        //   VoiceRecognition.openChromeTab();
        ModelTraining.recordVoiceAndCreateFiles();
        ModelTraining.testModel();
    }
}