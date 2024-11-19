package votypka;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static List<Station> readStationsFromFile(File file) throws IOException {
        List<Station> stations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Read the header line and discard it

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1); // Use -1 to include empty fields

                String name = parts[0].trim();
                String institute = parts[1].trim();

                Double longitude = null;
                Double latitude = null;

                if (!parts[2].trim().isEmpty()) {
                    longitude = Double.valueOf(parts[2].trim());
                }

                if (!parts[3].trim().isEmpty()) {
                    latitude = Double.valueOf(parts[3].trim());
                }

                String responsiblePerson = parts.length > 4 ? parts[4].trim() : "";

                Station station;
                if (longitude == null || latitude == null) {
                    station = new Station(name, institute, responsiblePerson);
                } else {
                    station = new Station(name, institute, longitude, latitude, responsiblePerson);
                }

                stations.add(station);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing CSV file: " + e.getMessage());
        }

        return stations;
    }

    public static void writeToFile(String fileName, Institute institute) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("File " + fileName + " already exists.");
            System.out.println("Do you want to overwrite it? (yes/no)");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String response = reader.readLine();
            if (!response.equalsIgnoreCase("yes")) {
                System.out.println("Operation cancelled.");
                return;
            }
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(institute);
        }
    }
}
