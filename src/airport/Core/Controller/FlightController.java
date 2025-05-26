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
import airport.Core.Storage.Storage;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 *
 * @author nxq
 */
public class FlightController {

    public static Response createFlight(String id, String planeId, String departureLocationId,
            String arrivalLocationId, String scaleLocationId,
            String departureYearStr, String departureMonthStr, String departureDayStr,
            String departureHourStr, String departureMinuteStr,
            String hoursDurationArrivalStr, String minutesDurationArrivalStr,
            String hoursDurationScaleStr, String minutesDurationScaleStr) {
        if (id == null || id.trim().isEmpty()) {
            return new Response("Flight ID cannot be empty.", Status.BAD_REQUEST);
        }
        if (!id.trim().matches("[A-Z]{3}[0-9]{3}")) {
            return new Response("Flight ID must follow the format XXXYYY (3 uppercase letters, 3 digits).", Status.BAD_REQUEST);
        }
        String trimmedFlightId = id.trim();

        Storage storage = Storage.getInstance();

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

        Location scaleLocationObj = null;
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

            } catch (NumberFormatException ex) {
                return new Response("Scale layover duration components must be valid numbers.", Status.BAD_REQUEST);
            }
        } else {
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
        }

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

        for (Flight f : storage.getFlights()) {
            if (f.getId().equals(trimmedFlightId)) {
                return new Response("A flight with ID '" + trimmedFlightId + "' already exists.", Status.BAD_REQUEST);
            }
        }

        try {
            Flight newFlight;
            if (hasScale && scaleLocationObj != null) {
                newFlight = new Flight(trimmedFlightId, plane, departureLocation, scaleLocationObj, arrivalLocation,
                        departureDateTime, parsedHoursDurationArrival, parsedMinutesDurationArrival,
                        parsedHoursDurationScale, parsedMinutesDurationScale);
            } else {
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
        if (flightId == null || flightId.trim().isEmpty() || flightId.equals("ID")) { //
            return new Response("Flight ID must be selected to apply delay.", Status.BAD_REQUEST);
        }

        int hours, minutes;
        try {
            if (hoursStr == null || hoursStr.trim().isEmpty() || hoursStr.equals("Hour")) { //
                return new Response("Delay hours are required and must be a number.", Status.BAD_REQUEST);
            }
            if (minutesStr == null || minutesStr.trim().isEmpty() || minutesStr.equals("Minute")) { //
                return new Response("Delay minutes are required and must be a number.", Status.BAD_REQUEST);
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

        Storage storage = Storage.getInstance(); //
        if (storage.delayFlightInStorage(flightId, hours, minutes)) {
            return new Response("Flight " + flightId + " delayed successfully by " + hours + "h " + minutes + "m.", Status.OK);
        } else {
            return new Response("Flight with ID '" + flightId + "' not found or an error occurred during delay.", Status.NOT_FOUND);
        }
    }

    public static ArrayList<String> getAllFlightIds() {
        Storage storage = Storage.getInstance();
        ArrayList<Flight> flights = storage.getFlights();
        if (flights == null) {
            return new ArrayList<>();
        }
        return flights.stream()
                .map(Flight::getId)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new)); // Específicamente a ArrayList
    }

    public static ArrayList<Object[]> getAllFlightsForTable() {
        Storage storage = Storage.getInstance();
        ArrayList<Flight> flights = storage.getFlights();
        if (flights == null) {
            return new ArrayList<>();
        }
        flights.sort(Comparator.comparing(Flight::getDepartureDate));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return flights.stream()
                .map(flight -> new Object[]{
            flight.getId(),
            (flight.getDepartureLocation() != null) ? flight.getDepartureLocation().getAirportId() : "-",
            (flight.getArrivalLocation() != null) ? flight.getArrivalLocation().getAirportId() : "-",
            (flight.getScaleLocation() != null) ? flight.getScaleLocation().getAirportId() : "-",
            flight.getDepartureDate().format(dateTimeFormatter),
            flight.calculateArrivalDate().format(dateTimeFormatter),
            (flight.getPlane() != null) ? flight.getPlane().getId() : "-",
            flight.getNumPassengers()
        })
                .collect(Collectors.toCollection(ArrayList::new)); // Específicamente a ArrayList
    }
}
