/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Controller;

import airport.Core.Controller.Utils.Response;
import airport.Core.Controller.Utils.Status;
import airport.Core.Model.Location;
import airport.Core.Model.Storage.Storage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 *
 * @author nxq
 */
public class LocationController {

    private static boolean hasMaxDecimalPlaces(String value, int maxPlaces) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Or false based on requirement for empty strings
        }
        int decimalPointIndex = value.indexOf('.');
        if (decimalPointIndex == -1) {
            return true; // No decimal part
        }
        // Check if there are any non-digit characters after decimal point (except for sign at start if allowed)
        String decimals = value.substring(decimalPointIndex + 1);
        if (!decimals.matches("[0-9]+")) {
            return false; // Invalid characters in decimal part
        }
        return decimals.length() <= maxPlaces;
    }

    public static Response createLocation(String airportId, String airportName, String airportCity, String airportCountry, String airportLatitudeStr, String airportLongitudeStr) {
        // Validate Airport ID
        if (airportId == null || airportId.trim().isEmpty()) {
            return new Response("Airport ID cannot be empty.", Status.BAD_REQUEST);
        }
        if (!airportId.trim().matches("[A-Z]{3}")) {
            return new Response("Airport ID must be composed of strictly 3 uppercase letters.", Status.BAD_REQUEST);
        }
        String trimmedAirportId = airportId.trim();

        // Validate non-empty fields
        if (airportName == null || airportName.trim().isEmpty()) {
            return new Response("Airport name cannot be empty.", Status.BAD_REQUEST);
        }
        if (airportCity == null || airportCity.trim().isEmpty()) {
            return new Response("Airport city cannot be empty.", Status.BAD_REQUEST);
        }
        if (airportCountry == null || airportCountry.trim().isEmpty()) {
            return new Response("Airport country cannot be empty.", Status.BAD_REQUEST);
        }

        // Validate Latitude
        double airportLatitude;
        if (airportLatitudeStr == null || airportLatitudeStr.trim().isEmpty()) {
            return new Response("Latitude cannot be empty.", Status.BAD_REQUEST);
        }
        if (!hasMaxDecimalPlaces(airportLatitudeStr.trim(), 4)) {
            return new Response("Latitude must have at most 4 decimal places.", Status.BAD_REQUEST);
        }
        try {
            airportLatitude = Double.parseDouble(airportLatitudeStr.trim());
            if (airportLatitude < -90 || airportLatitude > 90) {
                return new Response("Latitude must be between -90 and 90.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new Response("Latitude must be a valid number.", Status.BAD_REQUEST);
        }

        // Validate Longitude
        double airportLongitude;
        if (airportLongitudeStr == null || airportLongitudeStr.trim().isEmpty()) {
            return new Response("Longitude cannot be empty.", Status.BAD_REQUEST);
        }
        if (!hasMaxDecimalPlaces(airportLongitudeStr.trim(), 4)) {
            return new Response("Longitude must have at most 4 decimal places.", Status.BAD_REQUEST);
        }
        try {
            airportLongitude = Double.parseDouble(airportLongitudeStr.trim());
            if (airportLongitude < -180 || airportLongitude > 180) {
                return new Response("Longitude must be between -180 and 180.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new Response("Longitude must be a valid number.", Status.BAD_REQUEST);
        }

        Storage storage = Storage.getInstance();
        // Check for uniqueness
        for (Location loc : storage.getLocations()) {
            if (loc.getAirportId().equals(trimmedAirportId)) {
                return new Response("An airport with ID '" + trimmedAirportId + "' already exists.", Status.BAD_REQUEST);
            }
        }

        try {
            Location newLocation = new Location(trimmedAirportId, airportName.trim(), airportCity.trim(), airportCountry.trim(), airportLatitude, airportLongitude);
            storage.addLocation(newLocation);
            return new Response("Location registered successfully: " + trimmedAirportId, Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error creating location: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static ArrayList<String> getAllLocationAirportIds() {
        Storage storage = Storage.getInstance();
        ArrayList<Location> locations = storage.getLocations();
        if (locations == null) {
            return new ArrayList<>();
        }
        return locations.stream()
                .map(Location::getAirportId)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new)); // Específicamente a ArrayList
    }

    public static ArrayList<Object[]> getAllLocationsForTable() {
        Storage storage = Storage.getInstance();
        ArrayList<Location> locations = storage.getLocations();
        if (locations == null) {
            return new ArrayList<>();
        }
        locations.sort(Comparator.comparing(Location::getAirportId));

        return locations.stream()
                .map(location -> new Object[]{
            location.getAirportId(),
            location.getAirportName(),
            location.getAirportCity(),
            location.getAirportCountry()
        })
                .collect(Collectors.toCollection(ArrayList::new)); // Específicamente a ArrayList
    }
}
