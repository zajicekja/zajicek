package votypka;

import java.util.Objects;

public class Station implements Comparable<Station> {

    private String name;
    private String institute;
    private Double longitude;
    private Double latitude;
    private String responsiblePerson;

    // Constructor without longitude and latitude
    public Station(String name, String institute, String responsiblePerson) {
        this.name = name;
        this.institute = institute;
        this.longitude = null;
        this.latitude = null;
        this.responsiblePerson = responsiblePerson;
    }

    // Constructor with longitude and latitude
    public Station(String name, String institute, double longitude, double latitude, String responsiblePerson) {
        this.name = name;
        this.institute = institute;
        this.longitude = longitude;
        this.latitude = latitude;
        this.responsiblePerson = responsiblePerson;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getInstitute() {
        return institute;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        if (this.longitude != null) {
            throw new IllegalStateException("Longitude is already specified for station: " + this.name);
        }
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        if (this.latitude != null) {
            throw new IllegalStateException("Latitude is already specified for station: " + this.name);
        }
        this.latitude = latitude;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    // Calculate distance to another station
    public double distanceTo(Station other) {
        if (this.longitude == null || this.latitude == null) {
            throw new IllegalStateException("Longitude and/or latitude is not specified for station: " + this.name);
        }
        if (other.longitude == null || other.latitude == null) {
            throw new IllegalStateException("Longitude and/or latitude is not specified for station: " + other.name);
        }

        return haversine(this.latitude, this.longitude, other.latitude, other.longitude);
    }

    // Calculate distance to a specific location
    public double distanceTo(double longitude, double latitude) {
        if (this.longitude == null || this.latitude == null) {
            throw new IllegalStateException("Longitude and/or latitude is not specified for station: " + this.name);
        }

        return haversine(this.latitude, this.longitude, latitude, longitude);
    }

    // Haversine formula to calculate the great-circle distance between two points
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // Calculate distance to TUL (50.773, 15.074)
    public double distanceToTUL() {
        return distanceTo(15.074, 50.773);
    }

    // Override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Station station = (Station) o;
        return Objects.equals(name, station.name)
                && Objects.equals(institute, station.institute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, institute);
    }

    // Implement compareTo() for sorting by name
    @Override
    public int compareTo(Station other) {
        return this.name.compareTo(other.name);
    }

    // Override toString() for printing station details
    @Override
    public String toString() {
        return "Station{name='" + name + '\''
                + ", institute='" + institute + '\''
                + ", longitude=" + (longitude == null ? "N/A" : longitude)
                + ", latitude=" + (latitude == null ? "N/A" : latitude)
                + ", responsiblePerson='" + responsiblePerson + '\''
                + '}';
    }
}
