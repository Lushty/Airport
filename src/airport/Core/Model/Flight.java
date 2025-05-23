/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class Flight implements Cloneable { // Implementar Cloneable
    
    private final String id;
    private ArrayList<Passenger> passengers;
    private Plane plane; // Referencia compartida
    private Location departureLocation; // Referencia compartida
    private Location scaleLocation; // Referencia compartida, puede ser null
    private Location arrivalLocation; // Referencia compartida
    private LocalDateTime departureDate; // Inmutable
    private int hoursDurationArrival;
    private int minutesDurationArrival;
    private int hoursDurationScale;
    private int minutesDurationScale;
    

    public Flight(String id, Plane plane, Location departureLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival) {
        this.id = id;
        this.passengers = new ArrayList<>();
        this.plane = plane; // Se asigna la referencia
        this.departureLocation = departureLocation; // Se asigna la referencia
        this.arrivalLocation = arrivalLocation; // Se asigna la referencia
        this.departureDate = departureDate;
        this.hoursDurationArrival = hoursDurationArrival;
        this.minutesDurationArrival = minutesDurationArrival;
        this.scaleLocation = null;
        this.hoursDurationScale = 0;
        this.minutesDurationScale = 0;
        
        if (this.plane != null) {
            // No se añade al clon, solo al original si se modifica la lista de vuelos del avión original.
            // this.plane.addFlight(this); // Esto crearía una dependencia circular si se clonan profundamente.
        }
    }

    public Flight(String id, Plane plane, Location departureLocation, Location scaleLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival, int hoursDurationScale, int minutesDurationScale) {
        this.id = id;
        this.passengers = new ArrayList<>();
        this.plane = plane; // Se asigna la referencia
        this.departureLocation = departureLocation; // Se asigna la referencia
        this.scaleLocation = scaleLocation; // Se asigna la referencia
        this.arrivalLocation = arrivalLocation; // Se asigna la referencia
        this.departureDate = departureDate;
        this.hoursDurationArrival = hoursDurationArrival;
        this.minutesDurationArrival = minutesDurationArrival;
        this.hoursDurationScale = hoursDurationScale;
        this.minutesDurationScale = minutesDurationScale;
        
        // if (this.plane != null) {
        //     this.plane.addFlight(this);
        // }
    }
    
    // Constructor de copia
    public Flight(Flight original) {
        this.id = original.id;
        this.passengers = new ArrayList<>(original.passengers); // Copia de la lista, mismas referencias a Passenger
        
        // Las referencias a Plane y Location se copian. No se clonan profundamente.
        // Esto es generalmente lo deseado: la copia del vuelo se refiere al mismo avión/ubicación física.
        this.plane = original.plane; 
        this.departureLocation = original.departureLocation;
        this.scaleLocation = original.scaleLocation;
        this.arrivalLocation = original.arrivalLocation;
        
        this.departureDate = original.departureDate; // LocalDateTime es inmutable
        this.hoursDurationArrival = original.hoursDurationArrival;
        this.minutesDurationArrival = original.minutesDurationArrival;
        this.hoursDurationScale = original.hoursDurationScale;
        this.minutesDurationScale = original.minutesDurationScale;
    }


    @Override
    public Flight clone() {
        try {
            Flight cloned = (Flight) super.clone();
            // Primitivos e inmutables (String, LocalDateTime) se copian bien.
            // Copiar referencias para Plane y Location (no clonar profundamente por defecto)
            cloned.plane = this.plane; // Sigue apuntando al mismo objeto Plane
            cloned.departureLocation = this.departureLocation; // Sigue apuntando al mismo objeto Location
            cloned.scaleLocation = this.scaleLocation;
            cloned.arrivalLocation = this.arrivalLocation;
            
            // Crear una nueva lista de pasajeros, pero con las mismas referencias a los objetos Passenger
            cloned.passengers = new ArrayList<>(this.passengers);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed for Flight", e);
        }
    }

    public void addPassenger(Passenger passenger) {
        if (!this.passengers.contains(passenger)) {
            this.passengers.add(passenger);
        }
    }
    
    public ArrayList<Passenger> getPassengers() {
        return new ArrayList<>(this.passengers); // Devolver copia
    }

    public String getId() {
        return id;
    }

    public Location getDepartureLocation() {
        return departureLocation; // Devuelve la referencia, podría clonarse si se necesita aislamiento total
    }

    public Location getScaleLocation() {
        return scaleLocation; // Devuelve la referencia
    }

    public Location getArrivalLocation() {
        return arrivalLocation; // Devuelve la referencia
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public int getHoursDurationArrival() {
        return hoursDurationArrival;
    }

    public int getMinutesDurationArrival() {
        return minutesDurationArrival;
    }

    public int getHoursDurationScale() {
        return hoursDurationScale;
    }

    public int getMinutesDurationScale() {
        return minutesDurationScale;
    }

    public Plane getPlane() {
        return plane; // Devuelve la referencia
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }
    
    public LocalDateTime calculateArrivalDate() {
        LocalDateTime arrival = departureDate.plusHours(hoursDurationArrival).plusMinutes(minutesDurationArrival);
        if (scaleLocation != null) { 
            arrival = arrival.plusHours(hoursDurationScale).plusMinutes(minutesDurationScale);
        }
        return arrival;
    }
    
    public void delay(int hours, int minutes) {
        this.departureDate = this.departureDate.plusHours(hours).plusMinutes(minutes);
    }
    
    public int getNumPassengers() {
        return passengers.size();
    }
}