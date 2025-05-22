/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Controller;

import airport.Core.Controller.Utils.Response;
import airport.Core.Controller.Utils.Status;
import airport.Core.Model.Storage.Storage;
import airport.Core.Model.Passenger;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author nxq
 */
public class PassengerController {

    public static Response registerPassenger(String id, String firstname, String lastname,  String year, String month, String day, String phoneCode, String phone, String country) {
        try {
            long idD;
            // Attempt to parse ID and catch NumberFormatException immediately
            try {
                idD = Long.parseLong(id);
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }

            // Validate parsed ID
            if (idD < 0) {
                return new Response("Id must be positive", Status.BAD_REQUEST);
            } else if (idD > 999999999999999L) {
                return new Response("ID must be max 15 digits", Status.BAD_REQUEST);
            }

            int yearD = Integer.parseInt(year); // This and other parseInt calls might also need specific error handling
            int monthD = Integer.parseInt(month);
            int dayD = Integer.parseInt(day);
            int phoneCodeD = Integer.parseInt(phoneCode);
            long phoneD = Long.parseLong(phone);
            LocalDate birthDate = LocalDate.of(yearD, monthD, dayD);

            if (firstname.equals("")) {
                return new Response("Firstname must be not empty", Status.BAD_REQUEST);
            }
            if (lastname.equals("")) {
                return new Response("Lastname must be not empty", Status.BAD_REQUEST);
            }
            if (country.equals("")) {
                return new Response("Country must be not empty", Status.BAD_REQUEST);
            }

            try { // This try-catch is for phoneCodeD validation
                if (phoneCodeD < 0) {
                    return new Response("The PhoneCode must be positive", Status.BAD_REQUEST);
                } else {
                    if (phoneCodeD > 999){
                        return new Response("The PhoneCode must be max 3 digits", Status.BAD_REQUEST);
                    }
                }
            } catch (NumberFormatException ex) { // This seems redundant if phoneCode is parsed before this block
                return new Response("The PhoneCode must be numeric", Status.BAD_REQUEST);
            }
            // It's better to catch NumberFormatException for phoneCodeD where it's parsed.
            // Same for phoneD.

            try {
                if (birthDate.isAfter(LocalDate.now())) {
                    return new Response("The BirthDate must valid", Status.BAD_REQUEST);
                }
            } catch (DateTimeParseException ex) { // This catch is for birthDate validation after LocalDate.of
                return new Response("The birthDate must be Date", Status.BAD_REQUEST);
            }
            // Consider catching DateTimeException from LocalDate.of if invalid year/month/day are provided

            Storage storage = Storage.getInstance();
            if (!storage.addPassenger(new Passenger( idD,firstname, lastname, birthDate, phoneCodeD, phoneD, country))) {
                return new Response("A passenger with that id already registered", Status.BAD_REQUEST);
            }
            return new Response("Passenger registered successfully", Status.CREATED);

        } catch (NumberFormatException ex) {
            // This will now catch NumberFormatExceptions from year, month, day, phoneCode, phone parsing
            // if they are not handled more specifically above.
            // For a more user-friendly message, each parse operation should have its own try-catch.
            // For example, if year is "abc", it will be caught here.
            return new Response("Ensure all numeric fields (year, month, day, phone code, phone) are valid numbers.", Status.BAD_REQUEST);
        } catch (DateTimeParseException ex) {
             return new Response("Invalid date format for birth date.", Status.BAD_REQUEST);
        }
         catch (Exception ex) {
            // Log the exception for debugging: ex.printStackTrace();
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}