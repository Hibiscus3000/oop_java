package ru.nsu.fit.oop.lab4.train;

public enum TrainState {

    ASSEMBLING ("assembling"), LOADING ("loading"), UNLOADING ("unloading"),
    DRIVING_DEPARTURE_DESTINATION ("driving from departure station to destination station"),
    DRIVING_DESTINATION_DEPARTURE ("driving from destination station to departure station");

    private String state;

    TrainState(String state) {
        this.state = state;
    }

    public String getStringState() {
        return state;
    }
}
