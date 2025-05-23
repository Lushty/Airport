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
        this.scaleLocation = null; // Explicitly null
        this.hoursDurationScale = 0;   // No scale, so duration is 0
        this.minutesDurationScale = 0; // No scale, so duration is 0
        
        if (this.plane != null) {
            this.plane.addFlight(this);
        }
    }

    // Constructor for flights with a scale
    public Flight(String id, Plane plane, Location departureLocation, Location scaleLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival, int hoursDurationScale, int minutesDurationScale) {
        this.id = id;
        this.passengers = new ArrayList<>();
        this.plane = plane;
        this.departureLocation = departureLocation;
        this.scaleLocation = scaleLocation; // Assigned
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.hoursDurationArrival = hoursDurationArrival; // This would be duration of the second leg (scale to arrival)
        this.minutesDurationArrival = minutesDurationArrival;
        this.hoursDurationScale = hoursDurationScale;     // Duration of stop at scale
        this.minutesDurationScale = minutesDurationScale;
        // Note: The interpretation of hoursDurationArrival/minutesDurationArrival needs to be consistent.
        // If it's total flight time EXCLUDING stopover, then the constructor logic is more complex.
        // If it's per-leg, then the current model is okay, but calculateArrivalDate needs to be very clear.
        // Based on the provided JSON, it seems "hoursDurationArrival" might be total travel time excluding layover.
        // For now, I'll assume hoursDurationArrival/minutesDurationArrival is the duration of the *final leg* if there's a scale,
        // or total duration if no scale. The problem description implies "tiempo del vuelo" as a whole.
        // Let's assume the current fields are:
        // hoursDurationArrival/minutesDurationArrival = total travel time in air (sum of legs)
        // hoursDurationScale/minutesDurationScale = layover time at scale location
        
        if (this.plane != null) {
            this.plane.addFlight(this);
        }
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

    public int getHoursDurationArrival() { // Represents travel time (potentially sum of legs)
        return hoursDurationArrival;
    }

    public int getMinutesDurationArrival() { // Represents travel time (potentially sum of legs)
        return minutesDurationArrival;
    }

    public int getHoursDurationScale() { // Represents layover time
        return hoursDurationScale;
    }

    public int getMinutesDurationScale() { // Represents layover time
        return minutesDurationScale;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }
    
    /**
     * Calculates the final arrival date.
     * If there's a scale, it adds the travel time and the scale layover time.
     * If no scale, it just adds the travel time.
     */
    public LocalDateTime calculateArrivalDate() {
        LocalDateTime arrival = departureDate.plusHours(hoursDurationArrival).plusMinutes(minutesDurationArrival);
        if (scaleLocation != null) { // If there was a scale, add the layover time as well
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
