package votypka;

import java.util.*;

public class Institute {

    private String name;
    private Set<Station> stations;

    // Constructor
    public Institute(String name) {
        this.name = name;
        this.stations = new TreeSet<>(); // TreeSet for sorted stations by name
    }

    // Method to add a station to the institute
    public void addStation(Station station) {
        if (stations.contains(station)) {
            throw new IllegalArgumentException("Station with name " + station.getName() + " already exists in the institute.");
        }
        stations.add(station);
    }

    // Method to get number of managed stations
    public int getNumberOfStations() {
        return stations.size();
    }

    // Override toString() to provide sorted list of stations
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Institute: ").append(name).append("\n");
        for (Station station : stations) {
            sb.append(station).append("\n");
        }
        return sb.toString();
    }

    // Method to find closest station to given location
    public Station findClosestStation(double longitude, double latitude) {
        if (stations.isEmpty()) {
            throw new IllegalStateException("No stations available in the institute.");
        }

        Station closestStation = null;
        double minDistance = Double.MAX_VALUE;

        for (Station station : stations) {
            if (station.getLongitude() != null && station.getLatitude() != null) {
                double distance = station.distanceTo(longitude, latitude);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestStation = station;
                }
            }
        }

        if (closestStation == null) {
            throw new IllegalStateException("No station found with valid location data.");
        }

        return closestStation;
    }

    // Method to sort stations by distance from given location
    public List<Station> sortStationsByDistance(double longitude, double latitude) {
        List<Station> sortedStations = new ArrayList<>(stations);
        sortedStations.sort(Comparator.comparingDouble(s -> s.distanceTo(longitude, latitude)));
        return sortedStations;
    }

    // Method to allow user to add coordinates to stations without them
    public void addCoordinatesToStation(String stationName, double longitude, double latitude) {
        for (Station station : stations) {
            if (station.getName().equalsIgnoreCase(stationName)) {
                if (station.getLongitude() == null && station.getLatitude() == null) {
                    station.setLongitude(longitude);
                    station.setLatitude(latitude);
                } else {
                    throw new IllegalStateException("Coordinates already specified for station: " + station.getName());
                }
                return;
            }
        }
        throw new NoSuchElementException("Station with name " + stationName + " not found in the institute.");
    }
}
