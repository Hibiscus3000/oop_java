package ru.nsu.fit.oop.lab4.view.table.table_model;

import ru.nsu.fit.oop.lab4.station.Station;

public class StationTableModel extends ObservableLoggingTableModel<Station> {

    private final Station station;

    public StationTableModel(Station station) {
        super(null, "track type", "all", "available");
        this.station = station;
    }

    @Override
    public int getRowCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (null == station)
            return null;
        if (0 == columnIndex) {
            if (0 == rowIndex)
                return "loading tracks";
            if (1 == rowIndex)
                return "unloading tracks";
            if (2 == rowIndex)
                return "departure destination tracks";
            return "destination departure tracks";
        }
        if (1 == columnIndex) {
            if (0 == rowIndex)
                return station.getNumberOfLoadingTracks();
            if (1 == rowIndex)
                return station.getNumberOfUnloadingTracks();
            if (2 == rowIndex)
                return station.getNumberOfTracksDepartureDestination();
            return station.getNumberOfTracksDestinationDeparture();
        }
        if (0 == rowIndex)
            return station.getNumberOfAvailableLoadingTracks();
        if (1 == rowIndex)
            return station.getNumberOfAvailableUnloadingTracks();
        if (2 == rowIndex)
            return station.getNumberOfAvailableDepartureDestinationTracks();
        return station.getNumberOfAvailableDestinationDepartureTracks();
    }

    public void showLogs() {
        station.setWindowHandlerVisible(true);
    }
}
