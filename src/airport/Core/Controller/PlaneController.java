/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Controller;

import airport.Core.Controller.Utils.Response;
import airport.Core.Controller.Utils.Status;
import airport.Core.Model.Plane;
import airport.Core.Model.Storage.Storage;

/**
 *
 * @author nxq
 */
public class PlaneController {
    public static Response createPlane(String id, String brand, String model, String maxCapacityStr, String airline) {
        // Validate ID
        if (id == null || id.trim().isEmpty()) {
            return new Response("Plane ID cannot be empty.", Status.BAD_REQUEST);
        }
        if (!id.trim().matches("[A-Z]{2}[0-9]{5}")) {
            return new Response("Plane ID must follow the format XXYYYYY (2 uppercase letters, 5 digits).", Status.BAD_REQUEST);
        }
        String trimmedId = id.trim();

        // Validate non-empty fields
        if (brand == null || brand.trim().isEmpty()) {
            return new Response("Brand cannot be empty.", Status.BAD_REQUEST);
        }
        if (model == null || model.trim().isEmpty()) {
            return new Response("Model cannot be empty.", Status.BAD_REQUEST);
        }
        if (airline == null || airline.trim().isEmpty()) {
            return new Response("Airline cannot be empty.", Status.BAD_REQUEST);
        }

        // Validate and parse Max Capacity
        int maxCapacity;
        if (maxCapacityStr == null || maxCapacityStr.trim().isEmpty()){
            return new Response("Maximum capacity cannot be empty.", Status.BAD_REQUEST);
        }
        try {
            maxCapacity = Integer.parseInt(maxCapacityStr.trim());
            if (maxCapacity <= 0) {
                return new Response("Maximum capacity must be a positive number.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new Response("Maximum capacity must be a valid number.", Status.BAD_REQUEST);
        }

        Storage storage = Storage.getInstance();
        // Check for uniqueness
        for (Plane p : storage.getPlanes()) {
            if (p.getId().equals(trimmedId)) {
                return new Response("A plane with ID '" + trimmedId + "' already exists.", Status.BAD_REQUEST);
            }
        }
        
        try {
            Plane newPlane = new Plane(trimmedId, brand.trim(), model.trim(), maxCapacity, airline.trim());
            storage.addPlane(newPlane);
            return new Response("Plane registered successfully: " + trimmedId, Status.CREATED);
        } catch (Exception ex) {
             return new Response("Unexpected error creating plane: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
    

