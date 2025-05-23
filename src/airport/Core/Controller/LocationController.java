/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Controller;

import airport.Core.Controller.Utils.Response;
import airport.Core.Controller.Utils.Status;
import airport.Core.Model.Location;
import airport.Core.Model.Storage.Storage;

/**
 *
 * @author nxq
 */
public class LocationController {
    public static Response createLocation(String airportId, String airportName, String airportCity, String airportCountry, String airportLatitudeStr, String airportLongitudeStr) {
        try {
            // Validate Airport ID
            if (airportId == null || airportId.trim().isEmpty()) {
                return new Response("Airport ID cannot be empty.", Status.BAD_REQUEST);
            }
            // Validate Airport Name
            if (airportName == null || airportName.trim().isEmpty()) {
                return new Response("Airport Name cannot be empty.", Status.BAD_REQUEST);
            }
            // Validate Airport City
            if (airportCity == null || airportCity.trim().isEmpty()) {
                return new Response("Airport City cannot be empty.", Status.BAD_REQUEST);
            }
            // Validate Airport Country
            if (airportCountry == null || airportCountry.trim().isEmpty()) {
                return new Response("Airport Country cannot be empty.", Status.BAD_REQUEST);
            }

            // Validate and parse Latitude
            double airportLatitude;
            try {
                airportLatitude = Double.parseDouble(airportLatitudeStr);
                if (airportLatitude < -90 || airportLatitude > 90) {
                    return new Response("Latitude must be between -90 and 90.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Latitude must be a valid number.", Status.BAD_REQUEST);
            }

            // Validate and parse Longitude
            double airportLongitude;
            try {
                airportLongitude = Double.parseDouble(airportLongitudeStr);
                if (airportLongitude < -180 || airportLongitude > 180) {
                    return new Response("Longitude must be between -180 and 180.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Longitude must be a valid number.", Status.BAD_REQUEST);
            }

            Storage storage = Storage.getInstance();
            // Check if location with the same ID already exists
            for (Location existingLocation : storage.getLocations()) {
                if (existingLocation.getAirportId().equalsIgnoreCase(airportId.trim())) {
                    return new Response("A location with Airport ID '" + airportId + "' already exists.", Status.BAD_REQUEST);
                }
            }
            
            Location newLocation = new Location(airportId.trim(), airportName.trim(), airportCity.trim(), airportCountry.trim(), airportLatitude, airportLongitude);
            storage.addLocation(newLocation); // Assuming addLocation handles the actual addition

            return new Response("Location registered successfully: " + newLocation.getAirportId(), Status.CREATED);

        } catch (Exception ex) {
            // Log the exception for debugging: ex.printStackTrace();
            return new Response("Unexpected error creating location: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

}
