/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Controller;

import airport.Core.Controller.Utils.Response;
import airport.Core.Controller.Utils.Status;
import airport.Core.Model.Storage.Storage;
import airport.Core.Model.Passenger;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author nxq
 */
public class PassengerController {

    private static Response validatePassengerData(String idStr, String firstname, String lastname, String yearStr, String monthStr, String dayStr, String phoneCodeStr, String phoneStr, String country, boolean isUpdate) {
        // Validate ID
        long idD;
        if (idStr == null || idStr.trim().isEmpty()) {
            return new Response("Passenger ID cannot be empty.", Status.BAD_REQUEST);
        }
        try {
            idD = Long.parseLong(idStr.trim());
            if (idD < 0) {
                return new Response("Passenger ID must be non-negative.", Status.BAD_REQUEST);
            }
            if (idStr.trim().length() > 15) { // Check original string length for digits
                return new Response("Passenger ID must have at most 15 digits.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new Response("Passenger ID must be a valid number.", Status.BAD_REQUEST);
        }

        // Validate non-empty fields
        if (firstname == null || firstname.trim().isEmpty()) {
            return new Response("First name cannot be empty.", Status.BAD_REQUEST);
        }
        if (lastname == null || lastname.trim().isEmpty()) {
            return new Response("Last name cannot be empty.", Status.BAD_REQUEST);
        }
        if (country == null || country.trim().isEmpty()) {
            return new Response("Country cannot be empty.", Status.BAD_REQUEST);
        }

        // Validate Birthdate
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

        // Validate Phone Code
        int phoneCodeD;
        if (phoneCodeStr == null || phoneCodeStr.trim().isEmpty()){
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

        // Validate Phone
        long phoneD;
         if (phoneStr == null || phoneStr.trim().isEmpty()){
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
        
        // Uniqueness check for ID (only for new passenger registration)
        if (!isUpdate) {
            Storage storage = Storage.getInstance();
            for (Passenger p : storage.getPassengers()) {
                if (p.getId() == idD) {
                    return new Response("A passenger with ID " + idD + " already exists.", Status.BAD_REQUEST);
                }
            }
        }

        return null; // All validations passed
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
            if (!storage.addPassenger(newPassenger)) { // Storage.addPassenger also checks for uniqueness
                // This case might be redundant if controller validation is thorough, but good for safety
                return new Response("Failed to add passenger to storage. ID might already exist.", Status.BAD_REQUEST);
            }
            return new Response("Passenger registered successfully: " + idD, Status.CREATED);
        } catch (Exception ex) { // Catch any unexpected error during final object creation or storage
            return new Response("Unexpected error registering passenger: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updatePassenger(String idStr, String firstname, String lastname, String yearStr, String monthStr, String dayStr, String phoneCodeStr, String phoneStr, String country) {
        Response validationResponse = validatePassengerData(idStr, firstname, lastname, yearStr, monthStr, dayStr, phoneCodeStr, phoneStr, country, true);
        if (validationResponse != null) {
            // If validation fails for ID format itself (e.g., non-numeric), it will be caught here.
            // We don't re-check ID uniqueness for update, but the ID must exist.
            return validationResponse; 
        }
        
        try {
            long idD = Long.parseLong(idStr.trim());
            Storage storage = Storage.getInstance();
            Passenger passengerToUpdate = null;
            for (Passenger p : storage.getPassengers()) {
                if (p.getId() == idD) {
                    passengerToUpdate = p;
                    break;
                }
            }

            if (passengerToUpdate == null) {
                return new Response("Passenger with ID " + idD + " not found for update.", Status.NOT_FOUND);
            }

            // Parse data again for updating (already validated)
            int yearD = Integer.parseInt(yearStr.trim());
            int monthD = Integer.parseInt(monthStr.trim());
            int dayD = Integer.parseInt(dayStr.trim());
            LocalDate birthDate = LocalDate.of(yearD, monthD, dayD);
            int phoneCodeD = Integer.parseInt(phoneCodeStr.trim());
            long phoneD = Long.parseLong(phoneStr.trim());

            // Update passenger fields
            passengerToUpdate.setFirstname(firstname.trim());
            passengerToUpdate.setLastname(lastname.trim());
            passengerToUpdate.setBirthDate(birthDate);
            passengerToUpdate.setCountryPhoneCode(phoneCodeD);
            passengerToUpdate.setPhone(phoneD);
            passengerToUpdate.setCountry(country.trim());
            
            // No explicit storage.updatePassenger() method is shown in Storage.java.
            // Assuming modification of the object in the list is sufficient if it's the same reference.
            // If Storage clones objects on get, then an update method in Storage would be needed.

            return new Response("Passenger " + idD + " updated successfully.", Status.OK);

        } catch (Exception ex) { // Catch any unexpected error
            return new Response("Unexpected error updating passenger: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}