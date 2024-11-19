package votypka;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        List<Station> stations;
        Institute institute = new Institute("Meteorological Institute");
        File root = new File(System.getProperty("user.dir"), "data");
        File datas = new File(root, "stations.csv");

        // Load stations from CSV file
        try {
            stations = FileHandler.readStationsFromFile(datas);
            System.out.println("Stations loaded successfully from stations.csv");
        } catch (IOException e) {
            System.err.println("Error loading stations from file: " + e.getMessage());
            return;
        }

        // Add stations to the institute
        for (Station station : stations) {
            try {
                institute.addStation(station);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Main menu loop
        while (true) {
            System.out.println("1. List institutes sorted by name");
            System.out.println("2. List stations in an institute sorted by name");
            System.out.println("3. Find closest station to a location");
            System.out.println("4. Write data to binary file");
            System.out.println("5. Add coordinates to a station");
            System.out.println("6. Calculate distance between two stations");
            System.out.println("7. Calculate distance to TUL (50.773, 15.074)");
            System.out.println("8. List stations sorted by distance from a location");
            System.out.println("9. Exit");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 ->
                    System.out.println(institute.toString());

                case 2 -> {
                    System.out.println("Enter institute name:");
                    String instituteName = sc.nextLine().trim();
                    Institute selectedInstitute = null;
                    for (Station s : stations) {
                        if (s.getInstitute().equalsIgnoreCase(instituteName)) {
                            if (selectedInstitute == null) {
                                selectedInstitute = new Institute(instituteName);
                            }
                            selectedInstitute.addStation(s);
                        }
                    }
                    if (selectedInstitute != null) {
                        System.out.println(selectedInstitute.toString());
                    } else {
                        System.out.println("Institute not found.");
                    }
                }

                case 3 -> {
                    try {
                        System.out.println("Enter longitude:");
                        double lon = sc.nextDouble();
                        System.out.println("Enter latitude:");
                        double lat = sc.nextDouble();

                        Station closestStation = institute.findClosestStation(lon, lat);
                        if (closestStation != null) {
                            double distance = closestStation.distanceTo(lon, lat);
                            System.out.printf("Closest station to (%.2f, %.2f) is %s at %.2f km%n",
                                    lon, lat, closestStation.getName(), distance);
                        } else {
                            System.out.println("No station found with valid location data.");
                        }

                    } catch (Exception e) {
                        System.out.println("Error finding closest station: " + e.getMessage());
                    }
                }

                case 4 -> {
                    try {
                        System.out.println("Enter output binary file name:");
                        String fileName = sc.nextLine().trim();
                        FileHandler.writeToFile(fileName, institute);
                        System.out.println("Data written to binary file successfully.");
                    } catch (IOException e) {
                        System.out.println("Error writing to binary file: " + e.getMessage());
                    }
                }

                case 5 -> {
                    try {
                        System.out.println("Enter station name:");
                        String stationName = sc.nextLine().trim();
                        System.out.println("Enter longitude:");
                        double longitude = sc.nextDouble();
                        System.out.println("Enter latitude:");
                        double latitude = sc.nextDouble();
                        sc.nextLine(); // Consume newline

                        institute.addCoordinatesToStation(stationName, longitude, latitude);
                        System.out.println("Coordinates added successfully to station " + stationName);
                    } catch (Exception e) {
                        System.out.println("Error adding coordinates: " + e.getMessage());
                    }
                }

                case 6 -> {
                    try {
                        System.out.println("Enter the name of the first station:");
                        String stationName1 = sc.nextLine().trim();
                        System.out.println("Enter the name of the second station:");
                        String stationName2 = sc.nextLine().trim();

                        Station station1 = null, station2 = null;
                        for (Station station : stations) {
                            if (station.getName().equalsIgnoreCase(stationName1)) {
                                station1 = station;
                            }
                            if (station.getName().equalsIgnoreCase(stationName2)) {
                                station2 = station;
                            }
                        }

                        if (station1 == null || station2 == null) {
                            System.out.println("One or both stations not found.");
                            break;
                        }

                        double distance = station1.distanceTo(station2);
                        System.out.printf("Distance between %s and %s is %.2f km%n", station1.getName(), station2.getName(), distance);
                    } catch (Exception e) {
                        System.out.println("Error calculating distance: " + e.getMessage());
                    }
                }

                case 7 -> {
                    try {
                        System.out.println("Enter the station name:");
                        String stationNameTUL = sc.nextLine().trim();

                        Station stationTUL = null;
                        for (Station station : stations) {
                            if (station.getName().equalsIgnoreCase(stationNameTUL)) {
                                stationTUL = station;
                                break;
                            }
                        }

                        if (stationTUL == null) {
                            System.out.println("Station not found.");
                            break;
                        }

                        double distanceTUL = stationTUL.distanceToTUL();
                        System.out.printf("Distance from %s to TUL is %.2f km%n", stationTUL.getName(), distanceTUL);
                    } catch (Exception e) {
                        System.out.println("Error calculating distance to TUL: " + e.getMessage());
                    }
                }

                case 8 -> {
                    try {
                        System.out.println("Enter longitude:");
                        double sortLon = sc.nextDouble();
                        System.out.println("Enter latitude:");
                        double sortLat = sc.nextDouble();
                        sc.nextLine(); // Consume newline

                        List<Station> sortedStations = institute.sortStationsByDistance(sortLon, sortLat);
                        System.out.printf("Stations sorted by distance from (%.2f, %.2f):\n", sortLon, sortLat);
                        for (Station station : sortedStations) {
                            System.out.printf("%s - %.2f km\n", station.getName(), station.distanceTo(sortLon, sortLat));
                        }
                    } catch (Exception e) {
                        System.out.println("Error sorting stations by distance: " + e.getMessage());
                    }
                }

                case 9 -> {
                    System.out.println("Exiting program.");
                    return;
                }

                default ->
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
        }
    }
}
