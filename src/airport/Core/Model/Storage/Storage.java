/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model.Storage;

import airport.Core.Model.DataLoader.DataLoader;
import airport.Core.Model.Flight;
import airport.Core.Model.Location;
import airport.Core.Model.Passenger;
import airport.Core.Model.Plane;
import java.util.ArrayList;

/**
 *
 * @author nxq
 */
public class Storage {

    private static Storage instance;
    private ArrayList<Plane> planes;
    private ArrayList<Passenger> passengers;
    private ArrayList<Location> locations;
    private ArrayList<Flight> flights;

    private Storage() {
        this.planes = new ArrayList<>();
        this.passengers = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.flights = new ArrayList<>();
    }

    public static synchronized Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public void initializeData() {
        DataLoader loader = new DataLoader();

        String locationsPath = "json/locations.json";
        String planesPath = "json/planes.json";
        String passengersPath = "json/passengers.json";
        String flightsPath = "json/flights.json";

        // La asignación ahora es ArrayList = ArrayList, lo cual es correcto.
        this.locations = loader.loadLocations(locationsPath);
        this.planes = loader.loadPlanes(planesPath);
        this.passengers = loader.loadPassengers(passengersPath);
        this.flights = loader.loadFlights(flightsPath, this.planes, this.locations);
    }

    public boolean addPassenger(Passenger x) { // Ya estaba en tu Storage.java
        for (Passenger passenger : this.passengers) {
            if (x.getId() == passenger.getId()) {
                System.err.println("Storage: Passenger with ID " + x.getId() + " already exists.");
                return false;
            }
        }
        this.passengers.add(x);
        return true;
    }

    // Getters deben devolver ArrayList<Type>
    public ArrayList<Plane> getPlanes() {
        return planes;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    // Métodos para añadir individuales (ejemplos)
    public void addPlane(Plane plane) {
        // Comprobar si ya existe para evitar duplicados
        boolean exists = false;
        for (Plane p : this.planes) {
            if (p.getId().equals(plane.getId())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            this.planes.add(plane);
        }
    }

    public void addLocation(Location location) {
        boolean exists = false;
        for (Location l : this.locations) {
            if (l.getAirportId().equals(location.getAirportId())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            this.locations.add(location);
        }
    }

    public void addFlight(Flight flight) {
        boolean exists = false;
        for (Flight f : this.flights) {
            if (f.getId().equals(flight.getId())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            this.flights.add(flight);
        }
    }
}
