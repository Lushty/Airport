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
public class Flight {
    
    private final String id;
    private ArrayList<Passenger> passengers;
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
        
        this.plane.addFlight(this);
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
        
        this.plane.addFlight(this);
    }
    
    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
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
        return departureDate.plusHours(hoursDurationScale).plusHours(hoursDurationArrival).plusMinutes(minutesDurationScale).plusMinutes(minutesDurationArrival);
    }
    
    public void delay(int hours, int minutes) {
        this.departureDate = this.departureDate.plusHours(hours).plusMinutes(minutes);
    }
    
    public int getNumPassengers() {
        return passengers.size();
    }
    
}
