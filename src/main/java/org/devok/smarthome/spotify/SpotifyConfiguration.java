package org.devok.smarthome.spotify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotifyConfiguration {
    private static String clientId;
    private static String clientSecret;

    @Value("${spotify.client.id}")
    public void setClientId(String clientId) {
        SpotifyConfiguration.clientId = clientId;
    }

    @Value("${spotify.client.secret}")
    public void setClientSecret(String clientSecret) {
        SpotifyConfiguration.clientSecret = clientSecret;
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public static String getClientId() {
        return clientId;
    }
}
