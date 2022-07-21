package ru.nsu.fit.oop.lab4.station.tracks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Track {

    private Lock lock = new ReentrantLock();

    public boolean tryLock() {
        return lock.tryLock();
    }

    public void releaseLock() {
        lock.unlock();
    }
    // gives more authority to trains then they should have
}
