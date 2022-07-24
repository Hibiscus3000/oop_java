package ru.nsu.fit.oop.lab4.station;

import ru.nsu.fit.oop.lab4.goods.Storage;
import ru.nsu.fit.oop.lab4.station.tracks.LoadingTrack;
import ru.nsu.fit.oop.lab4.station.tracks.Track;
import ru.nsu.fit.oop.lab4.station.tracks.TrafficTrack;
import ru.nsu.fit.oop.lab4.station.tracks.UnloadingTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Station {

    private int distance;
    private int numberOfLoadingTracks;
    private int numberOfUnloadingTracks;
    private int numberOfTracksDepartureDestination;
    private int numberOfTracksDestinationDeparture;
    private List<Track> loadingTracks;
    private List<Track> unloadingTracks;
    private List<Track> tracksDepartureDestination;
    private List<Track> tracksDestinationDeparture;
    private Semaphore semLoadingTracks;
    private Semaphore semUnloadingTracks;
    private Semaphore semTracksDepartureDestination;
    private Semaphore semTracksDestinationDeparture;

    public Station(int distance, int numberOfLoadingTracks, int numberOfUnloadingTracks,
                   int numberOfTracksDepartureDestination, int numberOfTracksDestinationDeparture,
                   Map<String, Storage> departureStorages, Map<String, Storage> destinationStorages) {
        this.distance = distance;
        this.numberOfLoadingTracks = numberOfLoadingTracks;
        this.numberOfUnloadingTracks = numberOfUnloadingTracks;
        this.numberOfTracksDepartureDestination = numberOfTracksDepartureDestination;
        this.numberOfTracksDestinationDeparture = numberOfTracksDestinationDeparture;

        loadingTracks = new ArrayList<>();
        unloadingTracks = new ArrayList<>();
        tracksDepartureDestination = new ArrayList<>();
        tracksDestinationDeparture = new ArrayList<>();

        for (int i = 0; i < numberOfLoadingTracks; ++i)
            loadingTracks.add(new LoadingTrack(departureStorages,i + 1));
        for (int i = 0; i < numberOfUnloadingTracks; ++i)
            unloadingTracks.add(new UnloadingTrack(destinationStorages,numberOfLoadingTracks + i + 1));
        for (int i = 0; i < numberOfTracksDepartureDestination; ++i)
            tracksDepartureDestination.add(new TrafficTrack(distance, numberOfLoadingTracks + numberOfUnloadingTracks
            + i + 1));
        for (int i = 0; i < numberOfTracksDestinationDeparture; ++i)
            tracksDestinationDeparture.add(new TrafficTrack(distance, numberOfLoadingTracks + numberOfUnloadingTracks
            + numberOfTracksDepartureDestination + i + 1));

        semLoadingTracks = new Semaphore(numberOfLoadingTracks);
        semUnloadingTracks = new Semaphore(numberOfUnloadingTracks);
        semTracksDepartureDestination = new Semaphore(numberOfTracksDepartureDestination);
        semTracksDestinationDeparture = new Semaphore(numberOfTracksDestinationDeparture);
    }

    public Track acquireLoadingTrack() throws InterruptedException {
        semLoadingTracks.acquire();
        for (Track track : loadingTracks)
            if (track.tryLock())
                return track;
        return null;
    }

    public void releaseLoadingTrack(Track track) {
        track.releaseLock();
        semLoadingTracks.release();
    }

    public Track acquireUnloadingTrack() throws InterruptedException {
        semUnloadingTracks.acquire();
        for (Track track : unloadingTracks)
            if (track.tryLock())
                return track;
        return null;
    }

    public void releaseUnloadingTrack(Track track) {
        track.releaseLock();
        semUnloadingTracks.release();
    }

    public Track acquireDepartureDestinationTrack() throws InterruptedException {
        semTracksDepartureDestination.acquire();
        for (Track track : tracksDepartureDestination)
            if (track.tryLock())
                return track;
        return null;
    }

    public void releaseDepartureDestinationTrack(Track track) {
        track.releaseLock();
        semTracksDepartureDestination.release();
    }

    public Track acquireDestinationDepartureTrack() throws InterruptedException {
        semTracksDestinationDeparture.acquire();
        for (Track track : tracksDestinationDeparture)
            if (track.tryLock())
                return track;
        return null;
    }

    public void releaseDestinationDepartureTrack(Track track) {
        track.releaseLock();
        semTracksDestinationDeparture.release();
    }

}
