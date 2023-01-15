package org.devok.smarthome;

import org.devok.smarthome.voicerecognition.VoiceRecognition;

public class Main {
    public static void main(String[] args) {
        System.out.println("Voice Recognition running!");
        VoiceRecognition.openChromeTab();
    }
}