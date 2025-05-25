/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model.Operations;

import airport.Core.Model.Flight;
import airport.Core.Model.Passenger;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public interface FlightPassengerManager {
    
   public void addPassenger(List<Passenger> passengers, Passenger passenger);
   
}
