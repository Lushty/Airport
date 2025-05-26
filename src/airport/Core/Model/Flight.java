/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model;

import airport.Core.Model.Operations.FlightPassengerManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author edangulo
 */
public class Flight implements Cloneable { 

    private final String id;
    private final ArrayList<Passenger> passengers;
    private Plane plane; 
    private Location departureLocation;
    private Location scaleLocation;
    private Location arrivalLocation;
    private LocalDateTime departureDate;
    private int hoursDurationArrival;
    private int minutesDurationArrival;
    private int hoursDurationScale;
    private int minutesDurationScale;

    public Flight(String id, Plane plane, Location departureLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival) {
        this.id = id;
        this.passengers = new ArrayList<>();
        this.plane = plane;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.hoursDurationArrival = hoursDurationArrival;
        this.minutesDurationArrival = minutesDurationArrival;
        this.scaleLocation = null;
        this.hoursDurationScale = 0;
        this.minutesDurationScale = 0;
    }

    public Flight(String id, Plane plane, Location departureLocation, Location scaleLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival, int hoursDurationScale, int minutesDurationScale) {
        this.id = id;
        this.passengers = new ArrayList<>();
        this.plane = plane;
        this.departureLocation = departureLocation;
        this.scaleLocation = scaleLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.hoursDurationArrival = hoursDurationArrival;
        this.minutesDurationArrival = minutesDurationArrival;
        this.hoursDurationScale = hoursDurationScale;
        this.minutesDurationScale = minutesDurationScale;
    }

    public Flight(Flight original) {
        this.id = original.id;
        this.passengers = new ArrayList<>(original.passengers);
        this.plane = original.plane;
        this.departureLocation = original.departureLocation;
        this.scaleLocation = original.scaleLocation;
        this.arrivalLocation = original.arrivalLocation;
        this.departureDate = original.departureDate;
        this.hoursDurationArrival = original.hoursDurationArrival;
        this.minutesDurationArrival = original.minutesDurationArrival;
        this.hoursDurationScale = original.hoursDurationScale;
        this.minutesDurationScale = original.minutesDurationScale;
    }

    @Override
    public Flight clone() {
        try {
            Flight cloned = (Flight) super.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("La clonación falló para Flight", e);
        }
    }


    public boolean managePassengers(Passenger passenger, FlightPassengerManager manager, boolean add) {
        if (add) {
            if (passenger != null) {
                if (this.plane != null && this.passengers.size() >= this.plane.getMaxCapacity()) {
                    System.err.println("Vuelo " + this.id + " está lleno. No se puede añadir pasajero " + passenger.getId());
                    return false; 
                }
                manager.addPassenger(this.passengers, passenger); 
                return true; 
            }
        } else {
        }
        return false; 
    }

    public void setPlaneOriginal(Plane originalPlane) {
        this.plane = originalPlane;
    }

    public List<Passenger> getPassengers() {
        return Collections.unmodifiableList(new ArrayList<>(this.passengers));
    }

    public String getId() {
        return id;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public Location getScaleLocation() {
        return scaleLocation;
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
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
        return plane;
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

    public int getNumPassengers() {
        return passengers.size();
    }
}
