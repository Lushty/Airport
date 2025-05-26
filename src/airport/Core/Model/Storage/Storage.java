/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model.Storage;

import airport.Core.Model.Data.DataLoader;
import airport.Core.Model.Flight;
import airport.Core.Model.Location;
import airport.Core.Model.Utils.FlightDelayHandler;
import airport.Core.Model.Operations.FlightDelayHandlerNormal;
import airport.Core.Model.Utils.FlightPassengerManager;
import airport.Core.Model.Utils.FlightManager;
import airport.Core.Model.Operations.FlightManagerNormal;
import airport.Core.Model.Operations.FlightPassengerManagerNormal;
import airport.Core.Model.Passenger;
import airport.Core.Model.Plane;
import java.time.LocalDateTime;
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

    private final FlightDelayHandler flightDelayHandler;
    private final FlightManager flightmanager;
    private final FlightPassengerManager flightPassengerManager;

    private Storage() {
        this.planes = new ArrayList<>();
        this.passengers = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.flights = new ArrayList<>();

        this.flightDelayHandler = new FlightDelayHandlerNormal();
        this.flightmanager = new FlightManagerNormal();
        this.flightPassengerManager = new FlightPassengerManagerNormal();
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

    public boolean addPassenger(Passenger x) { // Ya retorna boolean
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

    public boolean addPlane(Plane plane) { // Cambiar para retornar boolean
        for (Plane p : this.planes) {
            if (p.getId().equals(plane.getId())) {
                System.err.println("Storage: Plane with ID " + plane.getId() + " already exists. Not added.");
                return false; // Indicar que no se añadió
            }
        }
        this.planes.add(plane);
        return true; // Indicar que se añadió
    }

    public boolean addLocation(Location location) { // Cambiar para retornar boolean
        for (Location l : this.locations) {
            if (l.getAirportId().equals(location.getAirportId())) {
                System.err.println("Storage: Location with Airport ID " + location.getAirportId() + " already exists. Not added.");
                return false;
            }
        }
        this.locations.add(location);
        return true;
    }

    public boolean addFlight(Flight flight) { // Cambiar para retornar boolean
        for (Flight f : this.flights) {
        if (f.getId().equals(flight.getId())) {
            System.err.println("Storage: Flight with ID " + flight.getId() + " already exists. Not added.");
            return false;
        }
    }
    this.flights.add(flight); // Añade a la lista general de vuelos

    if (flight.getPlane() != null) {
        Plane planeDelVuelo = flight.getPlane();
        // Para encontrar el objeto Plane original en Storage y modificarlo:
        for (Plane p : this.planes) {
            if (p.getId().equals(planeDelVuelo.getId())) {
                p.addFlight(flight); // Llama al método corregido en el objeto Plane original
                break;
            }
        }
    }
    return true;
}
    // --- MÉTODOS DE ACTUALIZACIÓN (NUEVOS) ---
    /**
     * Actualiza un pasajero existente en el almacenamiento.
     *
     * @param updatedPassenger El pasajero con la información actualizada.
     * @return true si el pasajero fue encontrado y actualizado, false en caso
     * contrario.
     */
    /**
     * Actualiza un pasajero existente en el almacenamiento. Modifica los campos
     * del pasajero original con los datos del pasajero actualizado, preservando
     * la lista de vuelos original a menos que se indique lo contrario.
     *
     * @param passengerWithUpdates El pasajero con la información actualizada.
     * @return true si el pasajero fue encontrado y actualizado, false en caso
     * contrario.
     */
    public boolean updatePassenger(Passenger passengerWithUpdates) {
        for (int i = 0; i < this.passengers.size(); i++) {
            Passenger existingPassenger = this.passengers.get(i);
            if (existingPassenger.getId() == passengerWithUpdates.getId()) {
                // Actualizar los campos del pasajero existente
                existingPassenger.setFirstname(passengerWithUpdates.getFirstname());
                existingPassenger.setLastname(passengerWithUpdates.getLastname());
                existingPassenger.setBirthDate(passengerWithUpdates.getBirthDate());
                existingPassenger.setCountryPhoneCode(passengerWithUpdates.getCountryPhoneCode());
                existingPassenger.setPhone(passengerWithUpdates.getPhone());
                existingPassenger.setCountry(passengerWithUpdates.getCountry());
                // La lista de vuelos (existingPassenger.getFlights()) no se toca aquí,
                // por lo que se preserva. Si necesitaras modificar los vuelos,
                // sería una operación separada o más compleja.

                // No es necesario this.passengers.set(i, existingPassenger);
                // porque 'existingPassenger' es una referencia al objeto en la lista,
                // por lo que las modificaciones a través de los setters ya afectan
                // al objeto almacenado.
                return true;
            }
        }
        System.err.println("Storage: Passenger with ID " + passengerWithUpdates.getId() + " not found for update.");
        return false;
    }

    /**
     * Actualiza un avión existente en el almacenamiento.
     *
     * @param updatedPlane El avión con la información actualizada.
     * @return true si el avión fue encontrado y actualizado, false en caso
     * contrario.
     */
    public boolean updatePlane(Plane updatedPlane) {
        for (int i = 0; i < this.planes.size(); i++) {
            if (this.planes.get(i).getId().equals(updatedPlane.getId())) {
                this.planes.set(i, updatedPlane.clone());
                return true;
            }
        }
        System.err.println("Storage: Plane with ID " + updatedPlane.getId() + " not found for update.");
        return false;
    }

    /**
     * Actualiza una ubicación existente en el almacenamiento.
     *
     * @param updatedLocation La ubicación con la información actualizada.
     * @return true si la ubicación fue encontrada y actualizada, false en caso
     * contrario.
     */
    public boolean updateLocation(Location updatedLocation) {
        for (int i = 0; i < this.locations.size(); i++) {
            if (this.locations.get(i).getAirportId().equals(updatedLocation.getAirportId())) {
                this.locations.set(i, updatedLocation.clone());
                return true;
            }
        }
        System.err.println("Storage: Location with Airport ID " + updatedLocation.getAirportId() + " not found for update.");
        return false;
    }

    /**
     * Actualiza un vuelo existente en el almacenamiento. Esto es más complejo
     * si el vuelo afecta las listas de otros objetos (pasajeros, avión). Por
     * ahora, solo actualizaremos los datos directos del vuelo. El retraso ya
     * modifica el objeto original. Si se cambian el avión o los pasajeros, se
     * necesitaría una lógica más robusta.
     *
     * @param updatedFlight El vuelo con la información actualizada.
     * @return true si el vuelo fue encontrado y actualizado, false en caso
     * contrario.
     */
    public boolean updateFlight(Flight updatedFlight) {
        for (int i = 0; i < this.flights.size(); i++) {
            if (this.flights.get(i).getId().equals(updatedFlight.getId())) {
                // Considerar el impacto en las listas de pasajeros y avión si estos cambian.
                // Por ahora, simple reemplazo.
                // Si updatedFlight tiene referencias a los mismos pasajeros/avión, está bien.
                // Si tiene nuevas referencias, hay que gestionar la consistencia.
                Flight oldFlight = this.flights.get(i);

                // Si el avión ha cambiado, hay que quitar el vuelo del avión antiguo y añadirlo al nuevo.
                if (oldFlight.getPlane() != null && !oldFlight.getPlane().equals(updatedFlight.getPlane())) {
                    // Esta lógica de desasociar/asociar de avión/pasajeros es compleja
                    // y debería manejarse con cuidado, preferiblemente en el controlador
                    // o con métodos más específicos en Storage.
                    // oldFlight.getPlane().getFlights().remove(oldFlight); // Asumiendo que getFlights() es mutable o hay un removeFlight()
                }
                if (updatedFlight.getPlane() != null && !updatedFlight.getPlane().equals(oldFlight.getPlane())) {
                    // updatedFlight.getPlane().addFlight(updatedFlight);
                }

                // Similar para pasajeros si la lista de pasajeros del vuelo se modifica externamente
                // y se pasa el `updatedFlight` con una nueva lista de pasajeros.
                this.flights.set(i, updatedFlight.clone());
                return true;
            }
        }
        System.err.println("Storage: Flight with ID " + updatedFlight.getId() + " not found for update.");
        return false;
    }

    public boolean associatePassengerWithFlight(long passengerId, String flightId) {
            Passenger originalPassenger = null;
    for (Passenger p : this.passengers) { // Iterar sobre la lista original
        if (p.getId() == passengerId) {
            originalPassenger = p;
            break;
        }
    }

    Flight originalFlight = null;
    for (Flight f : this.flights) { // Iterar sobre la lista original
        if (f.getId().equals(flightId)) {
            originalFlight = f;
            break;
        }
    }

    if (originalPassenger == null || originalFlight == null) {
        System.err.println("Storage: Passenger or Flight not found for association.");
        return false;
    }

    // Verificar capacidad (sobre el original)
    if (originalFlight.getPlane() != null
            && originalFlight.getNumPassengers() >= originalFlight.getPlane().getMaxCapacity()) { //
        System.err.println("Storage: Flight " + flightId + " is full.");
        return false;
    }

    // Verificar si el pasajero ya está en el vuelo (usando el método addPassenger que no añade duplicados)
    // y si el vuelo ya está en la lista del pasajero (usando el método addFlight que no añade duplicados)
    // Esto se maneja implícitamente si los métodos addPassenger/addFlight en los modelos evitan duplicados.

    // Realizar la asociación bidireccional en los objetos originales
    
    // Paso 1: Añadir pasajero al vuelo (asumiendo que Flight.addPassenger está corregido)
    originalFlight.addPassenger(originalPassenger); 

    // Paso 2: Añadir vuelo al pasajero (asumiendo que Passenger.addFlight está corregido)
    originalPassenger.addFlight(originalFlight); // <--- AQUÍ VA LA LÍNEA

    // La línea que tenías antes con flightPassengerManager y flightManager ya no es necesaria aquí
    // si los modelos gestionan sus propias listas internas a través de sus métodos add.
    // this.flightmanager.addFlight(originalFlight.getPlane().getFlights(), originalFlight); // Esta línea tenía problemas si getFlights() devuelve copia
    // this.flightPassengerManager.addPassenger(originalFlight.getPassengers(),originalPassenger); // Esta línea era la que causaba el error principal

    System.out.println("Storage: Association attempt for P:" + passengerId + " F:" + flightId 
        + ". Flight passengers now: " + originalFlight.getNumPassengers() 
        + ". Passenger flights now: " + originalPassenger.getNumFlights()); //
    return true;
}

    /**
     * Retrasa un vuelo específico modificando su hora de salida. Opera
     * directamente sobre la lista interna de vuelos originales.
     *
     * @param flightId El ID del vuelo a retrasar.
     * @param hours Las horas a añadir al retraso.
     * @param minutes Los minutos a añadir al retraso.
     * @return true si el vuelo fue encontrado y retrasado, false en caso
     * contrario.
     */
    public boolean delayFlightInStorage(String flightId, int hours, int minutes) {
        for (Flight f : this.flights) { // Itera sobre la lista original 'this.flights'
            if (f.getId().equals(flightId)) {
//                LocalDateTime x = FlightDelayHandler.delay(f.getDepartureDate(), hours, minutes); // Modifica el objeto Flight original directamente en la lista
//                f.setDepartureDate(x);
                LocalDateTime x = this.flightDelayHandler.delay(f.getDepartureDate(), hours, minutes);
                f.setDepartureDate(x);
            }
            return true;
        }

        System.err.println("Storage: Flight with ID " + flightId + " not found for delay.");
        return false;
    }

    // --- MÉTODOS DE ELIMINACIÓN (Si fueran necesarios) ---
    // public boolean removePassenger(long id) { ... }
    // public boolean removePlane(String id) { ... }
    // etc.
}
