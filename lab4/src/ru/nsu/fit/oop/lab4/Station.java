package ru.nsu.fit.oop.lab4;

import java.util.concurrent.Semaphore;

public class Station {

    private int distance;
    private int numberOfTracksDeparture;
    private int numberOfTracksDestination;
    private int numberOfTracksDepartureDestination;
    private int numberOfTracksDestinationDeparture;
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
        semTracksDeparture = new Semaphore(numberOfTracksDeparture);
        semTracksDestination = new Semaphore(numberOfTracksDestination);
        semTracksDepartureDestination = new Semaphore(numberOfTracksDepartureDestination);
        semTracksDestinationDeparture = new Semaphore(numberOfTracksDestinationDeparture);
    }

    public void acquireDepartureTrack() throws InterruptedException {
        semTracksDeparture.acquire();
    }

    public void releaseDepartureTrack() {
        semTracksDeparture.release();
    }

    public void acquireDestinationTrack() throws InterruptedException {
        semTracksDestination.acquire();
    }

    public void releaseDestinationTrack() {
        semTracksDestination.release();
    }

    public void acquireDepartureDestinationTrack() throws InterruptedException {
        semTracksDepartureDestination.acquire();
    }

    public void releaseDepartureDestinationTrack() {
        semTracksDepartureDestination.release();
    }

    public void acquireDestinationDepartureTrack() throws InterruptedException {
        semTracksDestinationDeparture.acquire();
    }

    public void releaseDestinationDepartureTrack() {
        semTracksDestinationDeparture.release();
    }

}
