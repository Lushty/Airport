/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Storage;

import airport.Core.Data.DataLoader;
import airport.Core.Model.Flight;
import airport.Core.Model.Location;
import airport.Core.Model.Operations.FlightDelayHandler;
import airport.Core.Model.Operations.FlightDelayHandlerNormal;
import airport.Core.Model.Operations.FlightPassengerManager;
import airport.Core.Model.Operations.FlightManager;
import airport.Core.Model.Operations.FlightManagerNormal;
import airport.Core.Model.Operations.FlightPassengerManagerNormal;
import airport.Core.Model.Passenger;
import airport.Core.Model.Plane;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    private final FlightDelayHandler flightDelayHandler;
    private final FlightManager flightManager; // Usar la interfaz
    private final FlightPassengerManager flightPassengerManager; // Usar la interfaz

    private Storage() {
        this.planes = new ArrayList<>();
        this.passengers = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.flights = new ArrayList<>();

        this.flightDelayHandler = new FlightDelayHandlerNormal();
        this.flightManager = new FlightManagerNormal(); // Instanciar la implementación
        this.flightPassengerManager = new FlightPassengerManagerNormal(); // Instanciar la implementación
    }

    public static synchronized Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public void initializeData() {
        if (this.locations.isEmpty() && this.planes.isEmpty() && this.passengers.isEmpty() && this.flights.isEmpty()) {
            DataLoader loader = new DataLoader();
            String locationsPath = "json/locations.json";
            String planesPath = "json/planes.json";
            String passengersPath = "json/passengers.json";
            String flightsPath = "json/flights.json";

            ArrayList<Location> loadedLocations = loader.loadLocations(locationsPath);
            this.setLocations(loadedLocations);

            ArrayList<Plane> loadedPlanes = loader.loadPlanes(planesPath);
            this.setPlanes(loadedPlanes);

            ArrayList<Passenger> loadedPassengers = loader.loadPassengers(passengersPath);
            this.setPassengers(loadedPassengers);

            ArrayList<Flight> loadedFlights = loader.loadFlights(flightsPath, this.planes, this.locations);

            for (Flight flight : loadedFlights) {
                if (flight.getPlane() != null) {
                    Plane planeForFlight = findOriginalPlaneById(flight.getPlane().getId());
                    if (planeForFlight != null) {
                        // Asegurar que el vuelo tenga la referencia al objeto Plane original de Storage
                        flight.setPlaneOriginal(planeForFlight);
                        // Usar el manager para añadir el vuelo al avión
                        planeForFlight.manageOperatedFlights(flight, this.flightManager, true);
                    } else {
                        System.err.println("Storage InitializeData: Plane with ID " + flight.getPlane().getId() + " for flight " + flight.getId() + " not found in storage.");
                    }
                }
                // Si flights.json también contuviera IDs de pasajeros para cada vuelo, esa lógica de asociación iría aquí,
                // usando originalFlight.managePassengers(passenger, this.flightPassengerManager, true);
                // y passenger.manageFlights(originalFlight, this.flightManager, true);
            }
            this.setFlights(loadedFlights);
        }
    }

    private Plane findOriginalPlaneById(String id) {
        for (Plane p : this.planes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    private Passenger findOriginalPassengerById(long id) {
        for (Passenger p : this.passengers) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private Flight findOriginalFlightById(String id) {
        for (Flight f : this.flights) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public void setPlanes(ArrayList<Plane> planes) {
        this.planes = planes;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public boolean addPassenger(Passenger passenger) {
        if (passenger == null) {
            return false;
        }
        for (Passenger p : this.passengers) {
            if (p.getId() == passenger.getId()) {
                System.err.println("Storage: Passenger with ID " + passenger.getId() + " already exists.");
                return false;
            }
        }
        this.passengers.add(passenger);
        return true;
    }

    public ArrayList<Plane> getPlanes() {
        if (this.planes == null) {
            return new ArrayList<>();
        }
        return this.planes.stream().map(p -> p.clone()).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Passenger> getPassengers() {
        if (this.passengers == null) {
            return new ArrayList<>();
        }
        return this.passengers.stream().map(p -> p.clone()).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Location> getLocations() {
        if (this.locations == null) {
            return new ArrayList<>();
        }
        return this.locations.stream().map(l -> l.clone()).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Flight> getFlights() {
        if (this.flights == null) {
            return new ArrayList<>();
        }
        return this.flights.stream().map(f -> f.clone()).collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean addPlane(Plane plane) {
        if (plane == null) {
            return false;
        }
        for (Plane p : this.planes) {
            if (p.getId().equals(plane.getId())) {
                System.err.println("Storage: Plane with ID " + plane.getId() + " already exists. Not added.");
                return false;
            }
        }
        this.planes.add(plane);
        return true;
    }

    public boolean addLocation(Location location) {
        if (location == null) {
            return false;
        }
        for (Location l : this.locations) {
            if (l.getAirportId().equals(location.getAirportId())) {
                System.err.println("Storage: Location with Airport ID " + location.getAirportId() + " already exists. Not added.");
                return false;
            }
        }
        this.locations.add(location);
        return true;
    }

    public boolean addFlight(Flight flight) {
        if (flight == null) {
            return false;
        }
        for (Flight f : this.flights) {
            if (f.getId().equals(flight.getId())) {
                System.err.println("Storage: Flight with ID " + flight.getId() + " already exists. Not added.");
                return false;
            }
        }
        this.flights.add(flight);

        if (flight.getPlane() != null) {
            Plane originalPlane = findOriginalPlaneById(flight.getPlane().getId());
            if (originalPlane != null) {
                // Asegurar que el vuelo tenga la referencia al objeto Plane original de Storage
                flight.setPlaneOriginal(originalPlane);
                // Usar el manager para añadir el vuelo al avión
                originalPlane.manageOperatedFlights(flight, this.flightManager, true);
            } else {
                System.err.println("Storage addFlight: Plane with ID " + flight.getPlane().getId() + " for flight " + flight.getId() + " not found in storage.");
            }
        }
        return true;
    }

    public boolean updatePassenger(Passenger passengerWithUpdates) {
        if (passengerWithUpdates == null) {
            return false;
        }
        Passenger existingPassenger = findOriginalPassengerById(passengerWithUpdates.getId());
        if (existingPassenger != null) {
            existingPassenger.setFirstname(passengerWithUpdates.getFirstname());
            existingPassenger.setLastname(passengerWithUpdates.getLastname());
            existingPassenger.setBirthDate(passengerWithUpdates.getBirthDate());
            existingPassenger.setCountryPhoneCode(passengerWithUpdates.getCountryPhoneCode());
            existingPassenger.setPhone(passengerWithUpdates.getPhone());
            existingPassenger.setCountry(passengerWithUpdates.getCountry());
            return true;
        }
        System.err.println("Storage: Passenger with ID " + passengerWithUpdates.getId() + " not found for update.");
        return false;
    }

    public boolean associatePassengerWithFlight(long passengerId, String flightId) {
        Passenger originalPassenger = findOriginalPassengerById(passengerId);
        Flight originalFlight = findOriginalFlightById(flightId);

        if (originalPassenger == null) {
            System.err.println("Storage: Passenger with ID " + passengerId + " not found for association.");
            return false;
        }
        if (originalFlight == null) {
            System.err.println("Storage: Flight with ID " + flightId + " not found for association.");
            return false;
        }

        // Verificar si ya está asociado (para evitar lógica duplicada en los managers si no la tienen)
        boolean alreadyOnFlightForPassenger = originalPassenger.getFlights().stream().anyMatch(fl -> fl.getId().equals(originalFlight.getId()));
        boolean passengerAlreadyInFlightList = originalFlight.getPassengers().stream().anyMatch(p -> p.getId() == originalPassenger.getId());

        if (alreadyOnFlightForPassenger && passengerAlreadyInFlightList) {
            System.err.println("Storage: Passenger " + passengerId + " is ALREADY associated with flight " + flightId + ".");
            return false;
        }

        // La verificación de capacidad ahora está dentro de originalFlight.managePassengers
        // Intentar añadir pasajero al vuelo (esto incluye la verificación de capacidad)
        boolean addedToFlightList = originalFlight.managePassengers(originalPassenger, this.flightPassengerManager, true);

        if (!addedToFlightList) {
            // Si managePassengers devuelve false, es probable que sea por capacidad u otro error interno.
            // No es necesario continuar, y no se debe añadir el vuelo al pasajero.
            System.err.println("Storage: Failed to add passenger " + passengerId + " to flight " + flightId + " list (e.g. capacity).");
            return false;
        }

        // Si se añadió exitosamente al vuelo, añadir el vuelo al pasajero
        originalPassenger.manageFlights(originalFlight, this.flightManager, true);

        System.out.println("Storage: Successfully associated passenger " + passengerId + " with flight " + flightId + ".");
        System.out.println("Storage: Flight " + flightId + " now has " + originalFlight.getNumPassengers() + " passengers.");
        System.out.println("Storage: Passenger " + passengerId + " now has " + originalPassenger.getNumFlights() + " flights.");

        return true;
    }

    public boolean delayFlightInStorage(String flightId, int hours, int minutes) {
        Flight originalFlight = findOriginalFlightById(flightId);
        if (originalFlight != null) {
            LocalDateTime newDepartureDate = this.flightDelayHandler.delay(originalFlight.getDepartureDate(), hours, minutes);
            originalFlight.setDepartureDate(newDepartureDate);
            return true;
        }
        System.err.println("Storage: Flight with ID " + flightId + " not found for delay.");
        return false;
    }
}
