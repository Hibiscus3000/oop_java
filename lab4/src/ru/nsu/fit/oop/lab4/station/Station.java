package ru.nsu.fit.oop.lab4.station;

import ru.nsu.fit.oop.lab4.Main;
import ru.nsu.fit.oop.lab4.exception.BadNumberOfTracks;
import ru.nsu.fit.oop.lab4.good.Storage;
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
                   Map<String, Storage> departureStorages, Map<String, Storage> destinationStorages) throws BadNumberOfTracks {
        this.distance = distance;
        this.numberOfLoadingTracks = numberOfLoadingTracks;
        if (numberOfLoadingTracks < 1)
            throw new BadNumberOfTracks("loading",numberOfLoadingTracks);
        this.numberOfUnloadingTracks = numberOfUnloadingTracks;
        if (numberOfUnloadingTracks < 1)
            throw new BadNumberOfTracks("unloading",numberOfUnloadingTracks);
        this.numberOfTracksDepartureDestination = numberOfTracksDepartureDestination;
        if (numberOfTracksDepartureDestination < 1)
            throw new BadNumberOfTracks("departure destination",numberOfTracksDepartureDestination);
        this.numberOfTracksDestinationDeparture = numberOfTracksDestinationDeparture;
        if (numberOfTracksDestinationDeparture < 1)
            throw new BadNumberOfTracks("destination departure",numberOfTracksDestinationDeparture);

        loadingTracks = new ArrayList<>();
        unloadingTracks = new ArrayList<>();
        tracksDepartureDestination = new ArrayList<>();
        tracksDestinationDeparture = new ArrayList<>();

        for (int i = 0; i < numberOfLoadingTracks; ++i)
            loadingTracks.add(new LoadingTrack(departureStorages,i + 1));
        Main.logger.config("Created " + numberOfLoadingTracks + " loading tracks.");
        for (int i = 0; i < numberOfUnloadingTracks; ++i)
            unloadingTracks.add(new UnloadingTrack(destinationStorages,numberOfLoadingTracks + i + 1));
        Main.logger.config("Created " + numberOfUnloadingTracks + " unloading tracks.");
        for (int i = 0; i < numberOfTracksDepartureDestination; ++i)
            tracksDepartureDestination.add(new TrafficTrack(distance, numberOfLoadingTracks + numberOfUnloadingTracks
            + i + 1));
        Main.logger.config("Created " + numberOfTracksDepartureDestination +
                " departure destination tracks.");
        for (int i = 0; i < numberOfTracksDestinationDeparture; ++i)
            tracksDestinationDeparture.add(new TrafficTrack(distance, numberOfLoadingTracks + numberOfUnloadingTracks
            + numberOfTracksDepartureDestination + i + 1));
        Main.logger.config("Created " + numberOfTracksDestinationDeparture +
                " destination departure tracks.");

        semLoadingTracks = new Semaphore(numberOfLoadingTracks);
        semUnloadingTracks = new Semaphore(numberOfUnloadingTracks);
        semTracksDepartureDestination = new Semaphore(numberOfTracksDepartureDestination);
        semTracksDestinationDeparture = new Semaphore(numberOfTracksDestinationDeparture);
        Main.logger.config("Created semaphores.");
    }

    public Track acquireLoadingTrack() throws InterruptedException {
        semLoadingTracks.acquire();
        for (Track track : loadingTracks)
            if (track.tryLock()) {
                Main.logger.config("Loading track #" + track.getId() + " acquired.");
                return track;
            }
        Main.logger.severe("Wasn't able to acquire loading track.");
        return null;
    }

    public void releaseLoadingTrack(Track track) {
        track.releaseLock();
        semLoadingTracks.release();
        Main.logger.config("Loading track #" + track.getId() + " released.");
    }

    public Track acquireUnloadingTrack() throws InterruptedException {
        semUnloadingTracks.acquire();
        for (Track track : unloadingTracks)
            if (track.tryLock()) {
                Main.logger.config("Unloading track #" + track.getId() + " acquired.");
                return track;
            }
        Main.logger.severe("Wasn't able to acquire unloading track.");
        return null;
    }

    public void releaseUnloadingTrack(Track track) {
        track.releaseLock();
        semUnloadingTracks.release();
        Main.logger.config("Unloading track #" + track.getId() + " released.");
    }

    public Track acquireDepartureDestinationTrack() throws InterruptedException {
        semTracksDepartureDestination.acquire();
        for (Track track : tracksDepartureDestination)
            if (track.tryLock()) {
                Main.logger.config("Departure destination track #" + track.getId() + " acquired");
                return track;
            }
        Main.logger.severe("Wasn't able to acquire departure destination track.");
        return null;
    }

    public void releaseDepartureDestinationTrack(Track track) {
        track.releaseLock();
        semTracksDepartureDestination.release();
        Main.logger.config("Departure destination track #" + track.getId() + " released");
    }

    public Track acquireDestinationDepartureTrack() throws InterruptedException {
        semTracksDestinationDeparture.acquire();
        for (Track track : tracksDestinationDeparture)
            if (track.tryLock()) {
                Main.logger.config("Destination departure track #" + track.getId() + " acquired");
                return track;
            }
        return null;
    }

    public void releaseDestinationDepartureTrack(Track track) {
        track.releaseLock();
        semTracksDestinationDeparture.release();
        Main.logger.config("Destination departure track #" + track.getId() + " released");
    }

}
