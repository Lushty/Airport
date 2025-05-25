/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model.Operations;

import airport.Core.Model.Flight;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class FlightManagerNormal implements FlightManager {

    @Override
    public void addFlight(List<Flight> flights, Flight flight) {
        if (!flights.contains(flight)) {
            flights.add(flight);
        }
    }
}    
