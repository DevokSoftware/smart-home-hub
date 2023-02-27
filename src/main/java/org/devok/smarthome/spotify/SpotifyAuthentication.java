package org.devok.smarthome.spotify;

import java.io.IOException;
import java.util.Optional;

import org.apache.hc.core5.http.ParseException;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;


// TODO - implement Authorization Code Flow in the future

public class SpotifyAuthentication {
    private static SpotifyAuthentication SpotifyAuthenticationInstance;
    private SpotifyApi spotifyApi;

    private SpotifyAuthentication() {
        initializeSpotifyApi();
        ClientCredentialsRequest clientCredentialsRequest = buildSpotifyCredentials();
        addAccessTokenToSpotifyApi(getAccessTokenWithCredentials(clientCredentialsRequest).orElseThrow());
    }

    public static SpotifyAuthentication getInstance() {
        if (SpotifyAuthenticationInstance == null) {
            SpotifyAuthenticationInstance = new SpotifyAuthentication();
        }
        return SpotifyAuthenticationInstance;
    }

    public SpotifyApi getSpotifyApi() {
        return spotifyApi;
    }

    private void initializeSpotifyApi() {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(SpotifyConfiguration.getClientId())
                .setClientSecret(SpotifyConfiguration.getClientSecret())
                .build();
    }

    private ClientCredentialsRequest buildSpotifyCredentials() {
        return spotifyApi.clientCredentials()
                .build();
    }

    private Optional<String> getAccessTokenWithCredentials(ClientCredentialsRequest clientCredentialsRequest) {
        try {
            return Optional.of(clientCredentialsRequest.execute().getAccessToken());
        } catch (IOException | ParseException | SpotifyWebApiException exception) {
            return Optional.empty();
        }
    }

    private void addAccessTokenToSpotifyApi(String accessToken) {
        spotifyApi.setAccessToken(accessToken);
    }
}
