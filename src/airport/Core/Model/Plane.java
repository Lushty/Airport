/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model;

import airport.Core.Model.Flight;
import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class Plane implements Cloneable { // Implementar Cloneable
    
    private final String id;
    private String brand;
    private String model;
    private final int maxCapacity;
    private String airline;
    private ArrayList<Flight> flights;

    public Plane(String id, String brand, String model, int maxCapacity, String airline) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.maxCapacity = maxCapacity;
        this.airline = airline;
        this.flights = new ArrayList<>(); //
    }

    // Constructor de copia
    public Plane(Plane original) {
        this.id = original.id; // String es inmutable
        this.brand = original.brand;
        this.model = original.model;
        this.maxCapacity = original.maxCapacity;
        this.airline = original.airline;
        this.flights = new ArrayList<>(original.flights); // Copia de la lista, mismas referencias a Flight
    }

    @Override
    public Plane clone() {
        try {
            Plane cloned = (Plane) super.clone();
            cloned.flights = new ArrayList<>(this.flights);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed for Plane", e);
        }
    }

    public void addFlight(Flight flight) {
        if (!this.flights.contains(flight)) {
            this.flights.add(flight);
        }
    }
    
    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getAirline() {
        return airline;
    }

    public ArrayList<Flight> getFlights() {
        // Devolver una copia de la lista
        return new ArrayList<>(flights);
    }
    
    public int getNumFlights() {
        return flights.size();
    }
}