package ru.nsu.fit.oop.lab4.station;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Station {

    private int distance;
    private int numberOfTracksDeparture;
    private int numberOfTracksDestination;
    private int numberOfTracksDepartureDestination;
    private int numberOfTracksDestinationDeparture;
    private List<Track> tracksDeparture;
    private List<Track> tracksDestination;
    private List<Track> tracksDepartureDestination;
    private List<Track> tracksDestinationDeparture;
    private Semaphore semTracksDeparture;
    private Semaphore semTracksDestination;
    private Semaphore semTracksDepartureDestination;
    private Semaphore semTracksDestinationDeparture;

    public Station(int distance, int numberOfTracksDeparture, int numberOfTracksDestination,
                   int numberOfTracksDepartureDestination, int numberOfTracksDestinationDeparture) {
        this.distance = distance;
        this.numberOfTracksDeparture = numberOfTracksDeparture;
        this.numberOfTracksDestination = numberOfTracksDestination;
        this.numberOfTracksDepartureDestination = numberOfTracksDepartureDestination;
        this.numberOfTracksDestinationDeparture = numberOfTracksDestinationDeparture;

        tracksDeparture = new ArrayList<>();
        tracksDestination = new ArrayList<>();
        tracksDepartureDestination = new ArrayList<>();
        tracksDestinationDeparture = new ArrayList<>();

        for (int i = 0; i < numberOfTracksDeparture; ++i)
            tracksDeparture.add(new Track());
        for (int i = 0; i < numberOfTracksDestination; ++i)
            tracksDestination.add(new Track());
        for (int i = 0; i < numberOfTracksDepartureDestination; ++i)
            tracksDepartureDestination.add(new Track());
        for (int i = 0; i < numberOfTracksDestinationDeparture; ++i)
            tracksDestinationDeparture.add(new Track());

        semTracksDeparture = new Semaphore(numberOfTracksDeparture);
        semTracksDestination = new Semaphore(numberOfTracksDestination);
        semTracksDepartureDestination = new Semaphore(numberOfTracksDepartureDestination);
        semTracksDestinationDeparture = new Semaphore(numberOfTracksDestinationDeparture);
    }

    public Track acquireDepartureTrack() throws InterruptedException {
        semTracksDeparture.acquire();
        for (Track track : tracksDeparture)
            if (track.tryLock())
                return track;
        return null;
    }

    public void releaseDepartureTrack(Track track) {
        track.releaseLock();
        semTracksDeparture.release();
    }

    public Track acquireDestinationTrack() throws InterruptedException {
        semTracksDestination.acquire();
        for (Track track : tracksDestination)
            if (track.tryLock())
                return track;
        return null;
    }

    public void releaseDestinationTrack(Track track) {
        track.releaseLock();
        semTracksDestination.release();
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
