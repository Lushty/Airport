/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Controller;

import airport.Core.Controller.Utils.Response;
import airport.Core.Controller.Utils.Status;
import airport.Core.Model.Flight;
import airport.Core.Storage.Storage;
import airport.Core.Model.Passenger;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 *
 * @author nxq
 */
public class PassengerController {

    private static Response validatePassengerData(String idStr, String firstname, String lastname, String yearStr, String monthStr, String dayStr, String phoneCodeStr, String phoneStr, String country, boolean isUpdate) {
        long idD;
        if (idStr == null || idStr.trim().isEmpty()) {
            return new Response("Passenger ID cannot be empty.", Status.BAD_REQUEST);
        }
        try {
            idD = Long.parseLong(idStr.trim());
            if (idD < 0) {
                return new Response("Passenger ID must be non-negative.", Status.BAD_REQUEST);
            }
            if (idStr.trim().length() > 15) {
                return new Response("Passenger ID must have at most 15 digits.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new Response("Passenger ID must be a valid number.", Status.BAD_REQUEST);
        }

        if (firstname == null || firstname.trim().isEmpty()) {
            return new Response("First name cannot be empty.", Status.BAD_REQUEST);
        }
        if (lastname == null || lastname.trim().isEmpty()) {
            return new Response("Last name cannot be empty.", Status.BAD_REQUEST);
        }
        if (country == null || country.trim().isEmpty()) {
            return new Response("Country cannot be empty.", Status.BAD_REQUEST);
        }

        LocalDate birthDate;
        try {
            int yearD = Integer.parseInt(yearStr);
            int monthD = Integer.parseInt(monthStr);
            int dayD = Integer.parseInt(dayStr);
            birthDate = LocalDate.of(yearD, monthD, dayD);
            if (birthDate.isAfter(LocalDate.now())) {
                return new Response("Birthdate cannot be in the future.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new Response("Birthdate components (year, month, day) must be valid numbers.", Status.BAD_REQUEST);
        } catch (DateTimeException ex) {
            return new Response("Invalid birthdate: " + ex.getMessage(), Status.BAD_REQUEST);
        }

        int phoneCodeD;
        if (phoneCodeStr == null || phoneCodeStr.trim().isEmpty()) {
            return new Response("Phone code cannot be empty.", Status.BAD_REQUEST);
        }
        try {
            phoneCodeD = Integer.parseInt(phoneCodeStr.trim());
            if (phoneCodeD < 0) {
                return new Response("Phone code must be non-negative.", Status.BAD_REQUEST);
            }
            if (phoneCodeStr.trim().length() > 3) {
                return new Response("Phone code must have at most 3 digits.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new Response("Phone code must be a valid number.", Status.BAD_REQUEST);
        }

        long phoneD;
        if (phoneStr == null || phoneStr.trim().isEmpty()) {
            return new Response("Phone number cannot be empty.", Status.BAD_REQUEST);
        }
        try {
            phoneD = Long.parseLong(phoneStr.trim());
            if (phoneD < 0) {
                return new Response("Phone number must be non-negative.", Status.BAD_REQUEST);
            }
            if (phoneStr.trim().length() > 11) {
                return new Response("Phone number must have at most 11 digits.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new Response("Phone number must be a valid number.", Status.BAD_REQUEST);
        }

        if (!isUpdate) {
            Storage storage = Storage.getInstance();
            for (Passenger p : storage.getPassengers()) {
                if (p.getId() == idD) {
                    return new Response("A passenger with ID " + idD + " already exists.", Status.BAD_REQUEST);
                }
            }
        }

        return null;
    }

    public static Response registerPassenger(String idStr, String firstname, String lastname, String yearStr, String monthStr, String dayStr, String phoneCodeStr, String phoneStr, String country) {
        Response validationResponse = validatePassengerData(idStr, firstname, lastname, yearStr, monthStr, dayStr, phoneCodeStr, phoneStr, country, false);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            long idD = Long.parseLong(idStr.trim());
            int yearD = Integer.parseInt(yearStr.trim());
            int monthD = Integer.parseInt(monthStr.trim());
            int dayD = Integer.parseInt(dayStr.trim());
            LocalDate birthDate = LocalDate.of(yearD, monthD, dayD);
            int phoneCodeD = Integer.parseInt(phoneCodeStr.trim());
            long phoneD = Long.parseLong(phoneStr.trim());

            Passenger newPassenger = new Passenger(idD, firstname.trim(), lastname.trim(), birthDate, phoneCodeD, phoneD, country.trim());
            Storage storage = Storage.getInstance();
            if (!storage.addPassenger(newPassenger)) {
                return new Response("Failed to add passenger to storage. ID might already exist.", Status.BAD_REQUEST);
            }
            return new Response("Passenger registered successfully: " + idD, Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error registering passenger: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updatePassenger(String idStr, String firstname, String lastname, String yearStr, String monthStr, String dayStr, String phoneCodeStr, String phoneStr, String country) {
        Response validationResponse = validatePassengerData(idStr, firstname, lastname, yearStr, monthStr, dayStr, phoneCodeStr, phoneStr, country, true);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            long idD = Long.parseLong(idStr.trim());
            Storage storage = Storage.getInstance();

            boolean passengerExists = false;
            for (Passenger pClone : storage.getPassengers()) {
                if (pClone.getId() == idD) {
                    passengerExists = true;
                    break;
                }
            }

            if (!passengerExists) {
                return new Response("Passenger with ID " + idD + " not found for update.", Status.NOT_FOUND);
            }

            int yearD = Integer.parseInt(yearStr.trim());
            int monthD = Integer.parseInt(monthStr.trim());
            int dayD = Integer.parseInt(dayStr.trim());
            LocalDate birthDate = LocalDate.of(yearD, monthD, dayD);
            int phoneCodeD = Integer.parseInt(phoneCodeStr.trim());
            long phoneD = Long.parseLong(phoneStr.trim());

            Passenger passengerWithUpdates = new Passenger(idD, firstname.trim(), lastname.trim(), birthDate, phoneCodeD, phoneD, country.trim());

            if (storage.updatePassenger(passengerWithUpdates)) {
                return new Response("Passenger " + idD + " updated successfully.", Status.OK);
            } else {
                return new Response("Failed to update passenger " + idD + ". Passenger might not exist or an internal error occurred.", Status.INTERNAL_SERVER_ERROR);
            }

        } catch (NumberFormatException | DateTimeException ex) {
            return new Response("Error parsing data for passenger update: " + ex.getMessage(), Status.BAD_REQUEST);
        } catch (Exception ex) {
            return new Response("Unexpected error updating passenger: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response addPassengerToFlight(String passengerIdStr, String flightIdStr) {
        long passengerId;
        if (passengerIdStr == null || passengerIdStr.trim().isEmpty()) {
            return new Response("Passenger ID cannot be empty.", Status.BAD_REQUEST);
        }
        try {
            passengerId = Long.parseLong(passengerIdStr.trim());
        } catch (NumberFormatException ex) {
            return new Response("Passenger ID must be a valid number.", Status.BAD_REQUEST);
        }

        if (flightIdStr == null || flightIdStr.trim().isEmpty() || flightIdStr.equals("Flight")) { // "Flight" es el placeholder del JComboBox
            return new Response("Flight ID must be selected.", Status.BAD_REQUEST);
        }

        Storage storage = Storage.getInstance(); //

        Passenger passengerDataForValidation = null;
        for (Passenger pClone : storage.getPassengers()) {
            if (pClone.getId() == passengerId) {
                passengerDataForValidation = pClone;
                break;
            }
        }
        if (passengerDataForValidation == null) {
            return new Response("Passenger with ID " + passengerId + " not found.", Status.NOT_FOUND);
        }

        Flight flightDataForValidation = null;
        for (Flight fClone : storage.getFlights()) {
            if (fClone.getId().equals(flightIdStr)) {
                flightDataForValidation = fClone;
                break;
            }
        }
        if (flightDataForValidation == null) {
            return new Response("Flight with ID " + flightIdStr + " not found.", Status.NOT_FOUND);
        }

        boolean alreadyOnFlight = false;
        for (Flight f : passengerDataForValidation.getFlights()) {
            if (f.getId().equals(flightIdStr)) {
                alreadyOnFlight = true;
                break;
            }
        }
        if (alreadyOnFlight) {
            return new Response("Passenger " + passengerId + " is already on flight " + flightIdStr + ".", Status.BAD_REQUEST);
        }

        if (flightDataForValidation.getPlane() != null && flightDataForValidation.getNumPassengers() >= flightDataForValidation.getPlane().getMaxCapacity()) { //
            return new Response("Flight " + flightIdStr + " is full. Cannot add more passengers.", Status.BAD_REQUEST);
        }

        boolean associationSuccess = storage.associatePassengerWithFlight(passengerId, flightIdStr);

        if (associationSuccess) {
            return new Response("Passenger " + passengerId + " added to flight " + flightIdStr + " successfully.", Status.OK);
        } else {
            return new Response("Failed to add passenger to flight. Please ensure IDs are correct, flight has capacity, and passenger is not already on flight.", Status.BAD_REQUEST); // O INTERNAL_SERVER_ERROR si la causa no es clara
        }
    }

    public static ArrayList<String> getAllPassengerIds() {
        Storage storage = Storage.getInstance();
        ArrayList<Passenger> passengers = storage.getPassengers();
        if (passengers == null) {
            return new ArrayList<>();
        }
        return passengers.stream()
                .map(p -> String.valueOf(p.getId()))
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Object[]> getAllPassengersForTable() {
        Storage storage = Storage.getInstance();
        ArrayList<Passenger> passengers = storage.getPassengers();
        if (passengers == null) {
            return new ArrayList<>();
        }
        passengers.sort(Comparator.comparingLong(Passenger::getId));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return passengers.stream()
                .map(passenger -> new Object[]{
            passenger.getId(),
            passenger.getFullname(),
            passenger.getBirthDate().format(dateFormatter),
            passenger.calculateAge(),
            passenger.generateFullPhone(),
            passenger.getCountry(),
            passenger.getNumFlights()
        })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Object[]> getFlightsForPassenger(String passengerIdStr) {
        if (passengerIdStr == null || passengerIdStr.equals("Select User")) {
            return new ArrayList<>();
        }
        try {
            long passengerId = Long.parseLong(passengerIdStr);
            Storage storage = Storage.getInstance();
            Passenger selectedPassenger = null;
            for (Passenger p : storage.getPassengers()) {
                if (p.getId() == passengerId) {
                    selectedPassenger = p;
                    break;
                }
            }

            if (selectedPassenger != null) {
                ArrayList<Flight> passengerFlights = new ArrayList<>(selectedPassenger.getFlights());
                passengerFlights.sort(Comparator.comparing(Flight::getDepartureDate));

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                return passengerFlights.stream()
                        .map(flight -> new Object[]{
                    flight.getId(),
                    flight.getDepartureDate().format(dateTimeFormatter),
                    flight.calculateArrivalDate().format(dateTimeFormatter)
                })
                        .collect(Collectors.toCollection(ArrayList::new));
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid passenger ID for getFlightsForPassenger: " + passengerIdStr);
        }
        return new ArrayList<>();
    }
}
