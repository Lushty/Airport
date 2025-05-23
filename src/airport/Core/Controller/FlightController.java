/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Controller;

import airport.Core.Controller.Utils.Response;
import airport.Core.Controller.Utils.Status;
import airport.Core.Model.Flight;
import airport.Core.Model.Location;
import airport.Core.Model.Plane;
import airport.Core.Model.Storage.Storage;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 *
 * @author nxq
 */
public class FlightController {

    public static Response createFlight(String id, String planeId, String departureLocationId,
            String arrivalLocationId, String scaleLocationId, // scaleLocationId can be null or "Location" (default from JComboBox)
            String departureYearStr, String departureMonthStr, String departureDayStr,
            String departureHourStr, String departureMinuteStr,
            String hoursDurationArrivalStr, String minutesDurationArrivalStr,
            String hoursDurationScaleStr, String minutesDurationScaleStr) {
        try {
            // Validate ID
            if (id == null || id.trim().isEmpty()) {
                return new Response("Flight ID cannot be empty.", Status.BAD_REQUEST);
            }

            Storage storage = Storage.getInstance();

            // Validate and find Plane
            if (planeId == null || planeId.trim().isEmpty() || planeId.equals("Plane")) { // "Plane" is default for JComboBox
                return new Response("Plane ID must be selected.", Status.BAD_REQUEST);
            }
            Plane plane = storage.getPlanes().stream()
                    .filter(p -> p.getId().equals(planeId))
                    .findFirst()
                    .orElse(null);
            if (plane == null) {
                return new Response("Plane with ID '" + planeId + "' not found.", Status.NOT_FOUND);
            }

            // Validate and find Departure Location
            if (departureLocationId == null || departureLocationId.trim().isEmpty() || departureLocationId.equals("Location")) {
                return new Response("Departure location must be selected.", Status.BAD_REQUEST);
            }
            Location departureLocation = storage.getLocations().stream()
                    .filter(loc -> loc.getAirportId().equals(departureLocationId))
                    .findFirst()
                    .orElse(null);
            if (departureLocation == null) {
                return new Response("Departure location with ID '" + departureLocationId + "' not found.", Status.NOT_FOUND);
            }

            // Validate and find Arrival Location
            if (arrivalLocationId == null || arrivalLocationId.trim().isEmpty() || arrivalLocationId.equals("Location")) {
                return new Response("Arrival location must be selected.", Status.BAD_REQUEST);
            }
            if (arrivalLocationId.equals(departureLocationId)) {
                return new Response("Arrival location cannot be the same as departure location.", Status.BAD_REQUEST);
            }
            Location arrivalLocation = storage.getLocations().stream()
                    .filter(loc -> loc.getAirportId().equals(arrivalLocationId))
                    .findFirst()
                    .orElse(null);
            if (arrivalLocation == null) {
                return new Response("Arrival location with ID '" + arrivalLocationId + "' not found.", Status.NOT_FOUND);
            }

            // Validate and find Scale Location (optional)
            Location scaleLocation = null;
            boolean hasScale = scaleLocationId != null && !scaleLocationId.trim().isEmpty() && !scaleLocationId.equals("Location");
            if (hasScale) {
                if (scaleLocationId.equals(departureLocationId) || scaleLocationId.equals(arrivalLocationId)) {
                    return new Response("Scale location cannot be the same as departure or arrival location.", Status.BAD_REQUEST);
                }
                scaleLocation = storage.getLocations().stream()
                        .filter(loc -> loc.getAirportId().equals(scaleLocationId))
                        .findFirst()
                        .orElse(null);
                if (scaleLocation == null) {
                    return new Response("Scale location with ID '" + scaleLocationId + "' not found.", Status.NOT_FOUND);
                }
            }

            // Parse and Validate Departure DateTime
            LocalDateTime departureDateTime;
            int departureYear, departureMonth, departureDay, departureHour, departureMinute;
            try {
                departureYear = Integer.parseInt(departureYearStr);
                departureMonth = Integer.parseInt(departureMonthStr); // Assuming JComboBox gives "1", "2", ...
                departureDay = Integer.parseInt(departureDayStr);     // Assuming JComboBox gives "1", "2", ...
                departureHour = Integer.parseInt(departureHourStr);   // Assuming JComboBox gives "0", "1", ...
                departureMinute = Integer.parseInt(departureMinuteStr); // Assuming JComboBox gives "0", "1", ...
                departureDateTime = LocalDateTime.of(departureYear, departureMonth, departureDay, departureHour, departureMinute);
                if (departureDateTime.isBefore(LocalDateTime.now())) {
                    // Depending on requirements, past flights might be allowed for record-keeping.
                    // For new flights, this check is usually desired.
                    // return new Response("Departure date and time cannot be in the past.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Departure date/time components must be valid numbers.", Status.BAD_REQUEST);
            } catch (DateTimeException ex) { // Corrected: Catching only DateTimeException
                return new Response("Invalid departure date or time: " + ex.getMessage(), Status.BAD_REQUEST);
            }

            // Parse and Validate Arrival Duration
            int hoursDurationArrival, minutesDurationArrival;
            try {
                hoursDurationArrival = Integer.parseInt(hoursDurationArrivalStr); // From JComboBox "Hour"
                minutesDurationArrival = Integer.parseInt(minutesDurationArrivalStr); // From JComboBox "Minute"
                if (hoursDurationArrival < 0 || minutesDurationArrival < 0 || (hoursDurationArrival == 0 && minutesDurationArrival == 0)) {
                    return new Response("Arrival duration must be positive.", Status.BAD_REQUEST);
                }
                if (minutesDurationArrival >= 60) {
                    return new Response("Arrival duration minutes must be less than 60.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Arrival duration components must be valid numbers.", Status.BAD_REQUEST);
            }

            // Parse and Validate Scale Duration (only if scaleLocation exists)
            int hoursDurationScale = 0;
            int minutesDurationScale = 0;
            if (hasScale) {
                try {
                    hoursDurationScale = Integer.parseInt(hoursDurationScaleStr); // From JComboBox "Hour"
                    minutesDurationScale = Integer.parseInt(minutesDurationScaleStr); // From JComboBox "Minute"
                    if (hoursDurationScale < 0 || minutesDurationScale < 0) { // Scale duration can be 0 if it's a quick stop.
                        return new Response("Scale duration components must be non-negative.", Status.BAD_REQUEST);
                    }
                    if (minutesDurationScale >= 60) {
                        return new Response("Scale duration minutes must be less than 60.", Status.BAD_REQUEST);
                    }
                } catch (NumberFormatException ex) {
                    return new Response("Scale duration components must be valid numbers.", Status.BAD_REQUEST);
                }
            }

            // Check if flight with the same ID already exists
            for (Flight existingFlight : storage.getFlights()) {
                if (existingFlight.getId().equalsIgnoreCase(id.trim())) {
                    return new Response("A flight with ID '" + id + "' already exists.", Status.BAD_REQUEST);
                }
            }

            Flight newFlight;
            if (hasScale && scaleLocation != null) {
                newFlight = new Flight(id.trim(), plane, departureLocation, scaleLocation, arrivalLocation,
                        departureDateTime, hoursDurationArrival, minutesDurationArrival,
                        hoursDurationScale, minutesDurationScale);
            } else {
                newFlight = new Flight(id.trim(), plane, departureLocation, arrivalLocation,
                        departureDateTime, hoursDurationArrival, minutesDurationArrival);
            }

            storage.addFlight(newFlight);

            return new Response("Flight registered successfully: " + newFlight.getId(), Status.CREATED);

        } catch (Exception ex) {
            // Log the exception for debugging: ex.printStackTrace();
            return new Response("Unexpected error creating flight: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response delayFlight(String flightId, String hoursStr, String minutesStr) {
        try {
            if (flightId == null || flightId.equals("ID")) { // "ID" is default for JComboBox
                return new Response("Flight ID must be selected to apply delay.", Status.BAD_REQUEST);
            }

            int hours, minutes;
            try {
                hours = Integer.parseInt(hoursStr); // From JComboBox "Hour"
                minutes = Integer.parseInt(minutesStr); // From JComboBox "Minute"
                if (hours < 0 || minutes < 0) {
                    return new Response("Delay hours and minutes must be non-negative.", Status.BAD_REQUEST);
                }
                if (hours == 0 && minutes == 0) {
                    return new Response("Delay must be a positive amount of time.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Delay hours and minutes must be valid numbers.", Status.BAD_REQUEST);
            }

            Storage storage = Storage.getInstance();
            Flight flightToDelay = storage.getFlights().stream()
                    .filter(f -> f.getId().equals(flightId))
                    .findFirst()
                    .orElse(null);

            if (flightToDelay == null) {
                return new Response("Flight with ID '" + flightId + "' not found.", Status.NOT_FOUND);
            }

            flightToDelay.delay(hours, minutes); // Assuming Flight model has a delay method
            // No need to call storage.updateFlight or similar unless you persist changes elsewhere

            return new Response("Flight " + flightId + " delayed successfully by " + hours + "h " + minutes + "m.", Status.OK);

        } catch (Exception ex) {
            return new Response("Unexpected error delaying flight: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
