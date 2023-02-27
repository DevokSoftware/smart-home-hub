package org.devok.smarthome;

import java.util.Optional;

import org.devok.smarthome.spotify.SpotifyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        // System.out.println("Voice Recognition running!");
        // VoiceRecognition.openChromeTab();
        // ModelTraining.recordVoiceAndCreateFiles();
        // ModelTraining.testModel();
        //   SpotifyAuthentication.getInstance();
        Optional<String> trackOptional = SpotifyClient.searchTrack("Darude - Sandstorm");
        SpotifyClient.addTrackToQueue(trackOptional.orElseThrow());
    }
}