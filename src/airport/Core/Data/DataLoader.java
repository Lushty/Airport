/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Data;

import airport.Core.Model.Flight;
import airport.Core.Model.Location;
import airport.Core.Model.Passenger;
import airport.Core.Model.Plane;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList; // Asegúrate que ArrayList esté importado
// import java.util.List; // Ya no es necesario si solo usas ArrayList

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 *
 * @author jhony
 */
public class DataLoader {

    private String readFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public ArrayList<Location> loadLocations(String filePath) { // Cambiado a ArrayList
        ArrayList<Location> locations = new ArrayList<>(); // Usar ArrayList
        try {
            String content = readFileContent(filePath);
            JSONArray locationsArray = new JSONArray(content);
            for (int i = 0; i < locationsArray.length(); i++) {
                JSONObject locJson = locationsArray.getJSONObject(i);
                Location location = new Location(
                        locJson.getString("airportId"),
                        locJson.getString("airportName"),
                        locJson.getString("airportCity"),
                        locJson.getString("airportCountry"),
                        locJson.getDouble("airportLatitude"),
                        locJson.getDouble("airportLongitude")
                );
                locations.add(location);
            }
            System.out.println("DataLoader: Locations loaded from " + filePath + " - Count: " + locations.size());
        } catch (IOException e) {
            System.err.println("DataLoader: Error reading locations file (" + filePath + "): " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("DataLoader: Error parsing locations JSON (" + filePath + "): " + e.getMessage());
        }
        return locations;
    }

    public ArrayList<Plane> loadPlanes(String filePath) { // Cambiado a ArrayList
        ArrayList<Plane> planes = new ArrayList<>(); // Usar ArrayList
        try {
            String content = readFileContent(filePath);
            JSONArray planesArray = new JSONArray(content);
            for (int i = 0; i < planesArray.length(); i++) {
                JSONObject planeJson = planesArray.getJSONObject(i);
                Plane plane = new Plane(
                        planeJson.getString("id"),
                        planeJson.getString("brand"),
                        planeJson.getString("model"),
                        planeJson.getInt("maxCapacity"),
                        planeJson.getString("airline")
                );
                planes.add(plane);
            }
            System.out.println("DataLoader: Planes loaded from " + filePath + " - Count: " + planes.size());
        } catch (IOException e) {
            System.err.println("DataLoader: Error reading planes file (" + filePath + "): " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("DataLoader: Error parsing planes JSON (" + filePath + "): " + e.getMessage());
        }
        return planes;
    }

    public ArrayList<Passenger> loadPassengers(String filePath) { // Cambiado a ArrayList
        ArrayList<Passenger> passengers = new ArrayList<>(); // Usar ArrayList
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            String content = readFileContent(filePath);
            JSONArray passengersArray = new JSONArray(content);
            for (int i = 0; i < passengersArray.length(); i++) {
                JSONObject pJson = passengersArray.getJSONObject(i);
                LocalDate birthDate = LocalDate.parse(pJson.getString("birthDate"), dateFormatter);
                Passenger passenger = new Passenger(
                        pJson.getLong("id"),
                        pJson.getString("firstname"),
                        pJson.getString("lastname"),
                        birthDate,
                        pJson.getInt("countryPhoneCode"),
                        pJson.getLong("phone"),
                        pJson.getString("country")
                );
                passengers.add(passenger);
            }
            System.out.println("DataLoader: Passengers loaded from " + filePath + " - Count: " + passengers.size());
        } catch (IOException e) {
            System.err.println("DataLoader: Error reading passengers file (" + filePath + "): " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("DataLoader: Error parsing passengers JSON (" + filePath + "): " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.err.println("DataLoader: Error parsing date in passengers JSON (" + filePath + "): " + e.getMessage());
        }
        return passengers;
    }

    // Los métodos findPlaneById y findLocationById ahora toman ArrayList como parámetro
    private Plane findPlaneById(String id, ArrayList<Plane> planes) {
        for (Plane p : planes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    private Location findLocationById(String id, ArrayList<Location> locations) {
        for (Location l : locations) {
            if (l.getAirportId().equals(id)) {
                return l;
            }
        }
        return null;
    }

    public ArrayList<Flight> loadFlights(String filePath, ArrayList<Plane> allPlanes, ArrayList<Location> allLocations) { // Cambiado a ArrayList y parámetros también
        ArrayList<Flight> flights = new ArrayList<>(); // Usar ArrayList
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            String content = readFileContent(filePath);
            JSONArray flightsArray = new JSONArray(content);
            for (int i = 0; i < flightsArray.length(); i++) {
                JSONObject fJson = flightsArray.getJSONObject(i);

                Plane plane = findPlaneById(fJson.getString("plane"), allPlanes);
                Location departureLoc = findLocationById(fJson.getString("departureLocation"), allLocations);
                Location arrivalLoc = findLocationById(fJson.getString("arrivalLocation"), allLocations);

                Location scaleLoc = null;
                if (fJson.has("scaleLocation") && !fJson.isNull("scaleLocation")) {
                    scaleLoc = findLocationById(fJson.getString("scaleLocation"), allLocations);
                }

                LocalDateTime departureDate = LocalDateTime.parse(fJson.getString("departureDate"), dateTimeFormatter);

                if (plane == null) {
                    System.err.println("DataLoader: Plane with ID " + fJson.getString("plane") + " not found for flight " + fJson.getString("id"));
                    continue;
                }
                if (departureLoc == null) {
                    System.err.println("DataLoader: Departure location with ID " + fJson.getString("departureLocation") + " not found for flight " + fJson.getString("id"));
                    continue;
                }
                if (arrivalLoc == null) {
                    System.err.println("DataLoader: Arrival location with ID " + fJson.getString("arrivalLocation") + " not found for flight " + fJson.getString("id"));
                    continue;
                }

                Flight flight;
                if (scaleLoc != null) {
                    flight = new Flight(
                            fJson.getString("id"),
                            plane,
                            departureLoc,
                            scaleLoc,
                            arrivalLoc,
                            departureDate,
                            fJson.getInt("hoursDurationArrival"),
                            fJson.getInt("minutesDurationArrival"),
                            fJson.getInt("hoursDurationScale"),
                            fJson.getInt("minutesDurationScale")
                    );
                } else {
                    flight = new Flight(
                            fJson.getString("id"),
                            plane,
                            departureLoc,
                            arrivalLoc,
                            departureDate,
                            fJson.getInt("hoursDurationArrival"),
                            fJson.getInt("minutesDurationArrival")
                    );
                }
                flights.add(flight);
            }
            System.out.println("DataLoader: Flights loaded from " + filePath + " - Count: " + flights.size());
        } catch (IOException e) {
            System.err.println("DataLoader: Error reading flights file (" + filePath + "): " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("DataLoader: Error parsing flights JSON (" + filePath + "): " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.err.println("DataLoader: Error parsing date in flights JSON (" + filePath + "): " + e.getMessage());
        }
        return flights;
    }
}

