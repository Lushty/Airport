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
            String arrivalLocationId, String scaleLocationId, // UI sends null if placeholder
            String departureYearStr, String departureMonthStr, String departureDayStr,
            String departureHourStr, String departureMinuteStr,
            String hoursDurationArrivalStr, String minutesDurationArrivalStr, // Total travel time
            String hoursDurationScaleStr, String minutesDurationScaleStr) {   // Layover time at scale
        // Validate Flight ID
        if (id == null || id.trim().isEmpty()) {
            return new Response("Flight ID cannot be empty.", Status.BAD_REQUEST);
        }
        if (!id.trim().matches("[A-Z]{3}[0-9]{3}")) {
            return new Response("Flight ID must follow the format XXXYYY (3 uppercase letters, 3 digits).", Status.BAD_REQUEST);
        }
        String trimmedFlightId = id.trim();

        Storage storage = Storage.getInstance();

        // Validate and find Plane
        if (planeId == null || planeId.trim().isEmpty()) {
            return new Response("Plane ID must be selected.", Status.BAD_REQUEST);
        }
        Plane plane = storage.getPlanes().stream()
                .filter(p -> p.getId().equals(planeId))
                .findFirst()
                .orElse(null);
        if (plane == null) {
            return new Response("Plane with ID '" + planeId + "' not found (must be created previously).", Status.NOT_FOUND);
        }

        // Validate and find Departure Location
        if (departureLocationId == null || departureLocationId.trim().isEmpty()) {
            return new Response("Departure location must be selected.", Status.BAD_REQUEST);
        }
        Location departureLocation = storage.getLocations().stream()
                .filter(loc -> loc.getAirportId().equals(departureLocationId))
                .findFirst()
                .orElse(null);
        if (departureLocation == null) {
            return new Response("Departure location with ID '" + departureLocationId + "' not found (must be created previously).", Status.NOT_FOUND);
        }

        // Validate and find Arrival Location
        if (arrivalLocationId == null || arrivalLocationId.trim().isEmpty()) {
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
            return new Response("Arrival location with ID '" + arrivalLocationId + "' not found (must be created previously).", Status.NOT_FOUND);
        }

        Location scaleLocationObj = null; // Renamed to avoid confusion with scaleLocationId parameter
        int parsedHoursDurationScale = 0;
        int parsedMinutesDurationScale = 0;
        boolean hasScale = scaleLocationId != null && !scaleLocationId.trim().isEmpty();

        if (hasScale) {
            if (scaleLocationId.equals(departureLocationId) || scaleLocationId.equals(arrivalLocationId)) {
                return new Response("Scale location cannot be the same as departure or arrival location.", Status.BAD_REQUEST);
            }
            scaleLocationObj = storage.getLocations().stream()
                    .filter(loc -> loc.getAirportId().equals(scaleLocationId))
                    .findFirst()
                    .orElse(null);
            if (scaleLocationObj == null) {
                return new Response("Scale location with ID '" + scaleLocationId + "' not found (must be created previously).", Status.NOT_FOUND);
            }
            // Parse and Validate Scale Duration (Layover time)
            try {
                if (hoursDurationScaleStr == null || minutesDurationScaleStr == null) { // If scale location provided, its duration must be too
                    return new Response("Scale layover duration (hours and minutes) cannot be null if scale location is provided.", Status.BAD_REQUEST);
                }
                parsedHoursDurationScale = Integer.parseInt(hoursDurationScaleStr);
                parsedMinutesDurationScale = Integer.parseInt(minutesDurationScaleStr);
                if (parsedHoursDurationScale < 0 || parsedMinutesDurationScale < 0) {
                    return new Response("Scale layover duration components must be non-negative.", Status.BAD_REQUEST);
                }
                if (parsedMinutesDurationScale >= 60) {
                    return new Response("Scale layover duration minutes must be less than 60.", Status.BAD_REQUEST);
                }
                // A scale layover can be 00:00 (technical stop) but usually it's > 0 if it's a passenger stop.
                // Requirement "En caso de no estar, el tiempo en horas y minutos de la escala debe ser 0"
                // This implies if it IS present, it can be > 0.
            } catch (NumberFormatException ex) {
                return new Response("Scale layover duration components must be valid numbers.", Status.BAD_REQUEST);
            }
        } else {
            // If no scale location is provided, ensure any passed scale duration strings are effectively zero or not set.
            // The constructor for Flight (no scale) will set scale durations to 0 anyway.
            // This validation ensures consistency if UI erroneously sends non-zero scale durations when no scale location is chosen.
            if ((hoursDurationScaleStr != null && !hoursDurationScaleStr.trim().isEmpty() && !hoursDurationScaleStr.equals("0"))
                    || (minutesDurationScaleStr != null && !minutesDurationScaleStr.trim().isEmpty() && !minutesDurationScaleStr.equals("0"))) {
                try {
                    if (hoursDurationScaleStr != null && !hoursDurationScaleStr.trim().isEmpty() && Integer.parseInt(hoursDurationScaleStr) != 0) {
                        return new Response("Scale layover hours must be 0 or empty if no scale location is specified.", Status.BAD_REQUEST);
                    }
                    if (minutesDurationScaleStr != null && !minutesDurationScaleStr.trim().isEmpty() && Integer.parseInt(minutesDurationScaleStr) != 0) {
                        return new Response("Scale layover minutes must be 0 or empty if no scale location is specified.", Status.BAD_REQUEST);
                    }
                } catch (NumberFormatException e) {
                    return new Response("Invalid scale layover duration when no scale location is specified.", Status.BAD_REQUEST);
                }
            }
            // parsedHoursDurationScale and parsedMinutesDurationScale remain 0
        }

        // Parse and Validate Departure DateTime
        LocalDateTime departureDateTime;
        try {
            if (departureYearStr == null || departureMonthStr == null || departureDayStr == null || departureHourStr == null || departureMinuteStr == null) {
                return new Response("All departure date and time components are required.", Status.BAD_REQUEST);
            }
            int departureYear = Integer.parseInt(departureYearStr);
            int departureMonth = Integer.parseInt(departureMonthStr);
            int departureDay = Integer.parseInt(departureDayStr);
            int departureHour = Integer.parseInt(departureHourStr);
            int departureMinute = Integer.parseInt(departureMinuteStr);
            departureDateTime = LocalDateTime.of(departureYear, departureMonth, departureDay, departureHour, departureMinute);
        } catch (NumberFormatException ex) {
            return new Response("Departure date/time components must be valid numbers.", Status.BAD_REQUEST);
        } catch (DateTimeException ex) {
            return new Response("Invalid departure date or time: " + ex.getMessage(), Status.BAD_REQUEST);
        }

        // Parse and Validate Arrival Duration (Total Travel Time in air)
        int parsedHoursDurationArrival, parsedMinutesDurationArrival;
        try {
            if (hoursDurationArrivalStr == null || minutesDurationArrivalStr == null) {
                return new Response("Total flight travel time (hours and minutes) is required.", Status.BAD_REQUEST);
            }
            parsedHoursDurationArrival = Integer.parseInt(hoursDurationArrivalStr);
            parsedMinutesDurationArrival = Integer.parseInt(minutesDurationArrivalStr);
            if (parsedHoursDurationArrival < 0 || parsedMinutesDurationArrival < 0) {
                return new Response("Total flight travel time must be non-negative.", Status.BAD_REQUEST);
            }
            if (parsedMinutesDurationArrival >= 60) {
                return new Response("Total flight travel time minutes must be less than 60.", Status.BAD_REQUEST);
            }
            if (parsedHoursDurationArrival == 0 && parsedMinutesDurationArrival == 0) {
                return new Response("Total flight travel time must be greater than 00:00.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new Response("Total flight travel time components must be valid numbers.", Status.BAD_REQUEST);
        }

        // Check for Flight ID uniqueness
        for (Flight f : storage.getFlights()) {
            if (f.getId().equals(trimmedFlightId)) {
                return new Response("A flight with ID '" + trimmedFlightId + "' already exists.", Status.BAD_REQUEST);
            }
        }

        try {
            Flight newFlight;
            if (hasScale && scaleLocationObj != null) {
                // Pass total travel time and layover time separately
                newFlight = new Flight(trimmedFlightId, plane, departureLocation, scaleLocationObj, arrivalLocation,
                        departureDateTime, parsedHoursDurationArrival, parsedMinutesDurationArrival,
                        parsedHoursDurationScale, parsedMinutesDurationScale);
            } else {
                // Constructor for no scale sets its scale durations to 0
                newFlight = new Flight(trimmedFlightId, plane, departureLocation, arrivalLocation,
                        departureDateTime, parsedHoursDurationArrival, parsedMinutesDurationArrival);
            }
            storage.addFlight(newFlight);
            return new Response("Flight registered successfully: " + trimmedFlightId, Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error creating flight: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response delayFlight(String flightId, String hoursStr, String minutesStr) {
        if (flightId == null) {
            return new Response("Flight ID must be selected to apply delay.", Status.BAD_REQUEST);
        }

        int hours, minutes;
        try {
            if (hoursStr == null || minutesStr == null) {
                return new Response("Delay hours and minutes are required.", Status.BAD_REQUEST);
            }
            hours = Integer.parseInt(hoursStr);
            minutes = Integer.parseInt(minutesStr);
            if (hours < 0 || minutes < 0) {
                return new Response("Delay hours and minutes must be non-negative.", Status.BAD_REQUEST);
            }
            if (hours == 0 && minutes == 0) {
                return new Response("Delay time must be greater than 00:00.", Status.BAD_REQUEST);
            }
            if (minutes >= 60) {
                return new Response("Delay minutes must be less than 60.", Status.BAD_REQUEST);
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
            return new Response("Flight with ID '" + flightId + "' not found (must be a valid flight).", Status.NOT_FOUND);
        }

        try {
            flightToDelay.delay(hours, minutes);
            return new Response("Flight " + flightId + " delayed successfully by " + hours + "h " + minutes + "m.", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error delaying flight: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
