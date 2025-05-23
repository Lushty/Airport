/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model;

/**
 *
 * @author edangulo
 */
public class Location implements Cloneable { // Implementar Cloneable
    
    private final String airportId;
    private String airportName;
    private String airportCity;
    private String airportCountry;
    private double airportLatitude;
    private double airportLongitude;

    public Location(String airportId, String airportName, String airportCity, String airportCountry, double airportLatitude, double airportLongitude) {
        this.airportId = airportId;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
        this.airportLatitude = airportLatitude;
        this.airportLongitude = airportLongitude;
    }

    // Constructor de copia
    public Location(Location original) {
        this.airportId = original.airportId;
        this.airportName = original.airportName;
        this.airportCity = original.airportCity;
        this.airportCountry = original.airportCountry;
        this.airportLatitude = original.airportLatitude;
        this.airportLongitude = original.airportLongitude;
    }

    @Override
    public Location clone() {
        try {
            // Para Location, super.clone() es suficiente ya que todos los campos son primitivos o inmutables (String)
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed for Location", e);
        }
    }

    public String getAirportId() {
        return airportId;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public String getAirportCountry() {
        return airportCountry;
    }

    public double getAirportLatitude() {
        return airportLatitude;
    }

    public double getAirportLongitude() {
        return airportLongitude;
    }
}
