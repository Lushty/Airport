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
        try {
            // Validate ID
            if (id == null || id.trim().isEmpty()) {
                return new Response("Plane ID cannot be empty.", Status.BAD_REQUEST);
            }
            // Validate Brand
            if (brand == null || brand.trim().isEmpty()) {
                return new Response("Brand cannot be empty.", Status.BAD_REQUEST);
            }
            // Validate Model
            if (model == null || model.trim().isEmpty()) {
                return new Response("Model cannot be empty.", Status.BAD_REQUEST);
            }
            // Validate Airline
            if (airline == null || airline.trim().isEmpty()) {
                return new Response("Airline cannot be empty.", Status.BAD_REQUEST);
            }

            // Validate and parse Max Capacity
            int maxCapacity;
            try {
                maxCapacity = Integer.parseInt(maxCapacityStr);
                if (maxCapacity <= 0) {
                    return new Response("Maximum capacity must be a positive number.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Maximum capacity must be a valid number.", Status.BAD_REQUEST);
            }

            Storage storage = Storage.getInstance();
            // Check if plane with the same ID already exists
            for (Plane existingPlane : storage.getPlanes()) {
                if (existingPlane.getId().equalsIgnoreCase(id.trim())) {
                    return new Response("A plane with ID '" + id + "' already exists.", Status.BAD_REQUEST);
                }
            }

            Plane newPlane = new Plane(id.trim(), brand.trim(), model.trim(), maxCapacity, airline.trim());
            storage.addPlane(newPlane); // Assuming addPlane handles the actual addition and any final checks

            return new Response("Plane registered successfully: " + newPlane.getId(), Status.CREATED);

        } catch (Exception ex) {
            // Log the exception for debugging: ex.printStackTrace();
            return new Response("Unexpected error creating plane: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }
}
    

