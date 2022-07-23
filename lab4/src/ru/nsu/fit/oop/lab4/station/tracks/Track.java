package ru.nsu.fit.oop.lab4.station.tracks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Track {

    private final int id;

    public Track(int id) {
        this.id = id;
    }

    private Lock lock = new ReentrantLock();

    public boolean tryLock() {
        return lock.tryLock();
    }

    public void releaseLock() {
        lock.unlock();
    }

}
