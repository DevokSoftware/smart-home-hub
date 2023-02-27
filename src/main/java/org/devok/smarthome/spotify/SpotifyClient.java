package org.devok.smarthome.spotify;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;

import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.player.AddItemToUsersPlaybackQueueRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

public class SpotifyClient {

    public static Optional<String> searchTrack(String searchQuery) {
        SearchTracksRequest searchTracksRequest = SpotifyAuthentication.getInstance().getSpotifyApi()
                .searchTracks(searchQuery)
                .limit(1)
                .build();
        try {
            final Paging<Track> trackPaging = searchTracksRequest.execute();
            System.out.println("Total: " + trackPaging.getTotal());
            return Optional.of(trackPaging.getItems()[0].getUri());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public static void addTrackToQueue(String trackUri) {
        AddItemToUsersPlaybackQueueRequest addItemToUsersPlaybackQueueRequest = SpotifyAuthentication.getInstance().getSpotifyApi()
                .addItemToUsersPlaybackQueue(trackUri)
                .build();
        try {
            final CompletableFuture<String> stringFuture = addItemToUsersPlaybackQueueRequest.executeAsync();
            final String string = stringFuture.join();
            System.out.println("Null: " + string);
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }
}
