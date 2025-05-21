/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model.Storage;

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
    
    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
    
    public boolean addPassenger(Passenger x){
        for (Passenger passenger : this.passengers) {
            if (x.getId() == passenger.getId()) {
                return false;
            }
        }
        this.passengers.add(x);
        return true;
    }
    
    
    
    
    
    
    
    
}
