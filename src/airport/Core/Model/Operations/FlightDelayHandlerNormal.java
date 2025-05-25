/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model.Operations;

import java.time.LocalDateTime;

/**
 *
 * @author User
 */
public class FlightDelayHandlerNormal implements FlightDelayHandler {

    @Override
    public LocalDateTime delay(LocalDateTime departureDate , int hours, int minutes){
        departureDate = departureDate.plusHours(hours).plusMinutes(minutes);
        return departureDate;
    }
    
    
}
