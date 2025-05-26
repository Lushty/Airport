/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model;

import airport.Core.Model.Flight;
import airport.Core.Model.Operations.FlightManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author edangulo
 */
public class Plane implements Cloneable {

    private final String id;
    private String brand;
    private String model;
    private final int maxCapacity;
    private String airline;
    private final ArrayList<Flight> flights;

    public Plane(String id, String brand, String model, int maxCapacity, String airline) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.maxCapacity = maxCapacity;
        this.airline = airline;
        this.flights = new ArrayList<>();
    }

    public Plane(Plane original) {
        this.id = original.id;
        this.brand = original.brand;
        this.model = original.model;
        this.maxCapacity = original.maxCapacity;
        this.airline = original.airline;
        this.flights = new ArrayList<>(original.flights);
    }

    @Override
    public Plane clone() {
        try {
            Plane cloned = (Plane) super.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("La clonación falló para Plane", e);
        }
    }

    public void manageOperatedFlights(Flight flight, FlightManager manager, boolean add) {
        if (add) {
            if (flight != null) {
                manager.addFlight(this.flights, flight);
            }
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

    public List<Flight> getFlights() {
        return Collections.unmodifiableList(new ArrayList<>(this.flights));
    }

    public int getNumFlights() {
        return flights.size();
    }
}
