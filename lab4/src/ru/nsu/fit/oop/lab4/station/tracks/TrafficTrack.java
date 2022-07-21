package ru.nsu.fit.oop.lab4.station.tracks;

public class TrafficTrack extends Track{

    private int distance;

    public TrafficTrack(int distance) {
        this.distance = distance;
    }

    public void drive(int speed) throws InterruptedException {
        Thread.sleep(1000 * distance / speed);
    }
}
