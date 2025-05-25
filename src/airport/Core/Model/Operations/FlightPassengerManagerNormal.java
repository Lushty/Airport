/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model.Operations;

import airport.Core.Model.Passenger;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class FlightPassengerManagerNormal implements FlightPassengerManager{

    @Override
    public void addPassenger(ArrayList<Passenger> passengers, Passenger passenger) {
        if (!passengers.contains(passenger)) {
            passengers.add(passenger);
        }
    }
    
}
