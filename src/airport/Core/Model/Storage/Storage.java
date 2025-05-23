/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model.Storage;

import airport.Core.Model.DataLoader.DataLoader;
import  airport.Core.Model.Flight;
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

        this.locations = loader.loadLocations(locationsPath); //
        this.planes = loader.loadPlanes(planesPath); //
        this.passengers = loader.loadPassengers(passengersPath); //
        this.flights = loader.loadFlights(flightsPath, this.planes, this.locations); //
    }

    public boolean addPassenger(Passenger x) { 
        for (Passenger passenger : this.passengers) {
            if (x.getId() == passenger.getId()) {
                System.err.println("Storage: Passenger with ID " + x.getId() + " already exists.");
                return false;
            }
        }
        this.passengers.add(x);
        return true;
    }

    // Getters que devuelven listas de CLONES usando bucles for-each
    public ArrayList<Plane> getPlanes() {
        if (this.planes == null) {
            return new ArrayList<>();
        }
        ArrayList<Plane> clonedPlanes = new ArrayList<>();
        for (Plane plane : this.planes) {
            if (plane != null) {
                try {
                    clonedPlanes.add(plane.clone());
                } catch (Exception e) { // Object.clone() es protected, pero nuestro Plane.clone() es public
                                        // y maneja CloneNotSupportedException internamente.
                    System.err.println("Error al clonar Plane con ID " + plane.getId() + ": " + e.getMessage());
                    // Decide cómo manejar el error: no añadir, añadir null, o añadir el original (menos seguro)
                }
            }
        }
        return clonedPlanes;
    }

    public ArrayList<Passenger> getPassengers() {
        if (this.passengers == null) {
            return new ArrayList<>();
        }
        ArrayList<Passenger> clonedPassengers = new ArrayList<>();
        for (Passenger passenger : this.passengers) {
            if (passenger != null) {
                try {
                    clonedPassengers.add(passenger.clone());
                } catch (Exception e) {
                    System.err.println("Error al clonar Passenger con ID " + passenger.getId() + ": " + e.getMessage());
                }
            }
        }
        return clonedPassengers;
    }

    public ArrayList<Location> getLocations() {
        if (this.locations == null) {
            return new ArrayList<>();
        }
        ArrayList<Location> clonedLocations = new ArrayList<>();
        for (Location location : this.locations) {
            if (location != null) {
                try {
                    clonedLocations.add(location.clone());
                } catch (Exception e) {
                    System.err.println("Error al clonar Location con ID " + location.getAirportId() + ": " + e.getMessage());
                }
            }
        }
        return clonedLocations;
    }

    public ArrayList<Flight> getFlights() {
        if (this.flights == null) {
            return new ArrayList<>();
        }
        ArrayList<Flight> clonedFlights = new ArrayList<>();
        for (Flight flight : this.flights) {
            if (flight != null) {
                try {
                    clonedFlights.add(flight.clone());
                } catch (Exception e) {
                    System.err.println("Error al clonar Flight con ID " + flight.getId() + ": " + e.getMessage());
                }
            }
        }
        return clonedFlights;
    }

    // Métodos para añadir individuales (ejemplos)
    public void addPlane(Plane plane) {
        boolean exists = false;
        for (Plane p : this.planes) {
            if (p.getId().equals(plane.getId())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            this.planes.add(plane);
        } else {
            System.err.println("Storage: Plane with ID " + plane.getId() + " already exists. Not added.");
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
        } else {
            System.err.println("Storage: Location with Airport ID " + location.getAirportId() + " already exists. Not added.");
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
        } else {
            System.err.println("Storage: Flight with ID " + flight.getId() + " already exists. Not added.");
        }
    }
}
