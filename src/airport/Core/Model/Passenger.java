/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model;

import airport.Core.Model.Flight;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class Passenger implements Cloneable { // Implementar Cloneable

    private final long id;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private int countryPhoneCode;
    private long phone;
    private String country;
    private ArrayList<Flight> flights;

    public Passenger(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.countryPhoneCode = countryPhoneCode;
        this.phone = phone;
        this.country = country;
        this.flights = new ArrayList<>(); //
    }

    // Constructor de copia (útil para el clonado)
    public Passenger(Passenger original) {
        this.id = original.id;
        this.firstname = original.firstname;
        this.lastname = original.lastname;
        this.birthDate = original.birthDate; // LocalDate es inmutable
        this.countryPhoneCode = original.countryPhoneCode;
        this.phone = original.phone;
        this.country = original.country;
        // Copia profunda de la lista de vuelos (conteniendo las mismas referencias a Flight)
        this.flights = new ArrayList<>(original.flights);
    }

    @Override
    public Passenger clone() {
        try {
            Passenger cloned = (Passenger) super.clone();
            // Los campos primitivos y los inmutables (String, LocalDate) se copian bien con super.clone().
            // Para la lista de vuelos, necesitamos una nueva instancia de ArrayList,
            // pero contendrá las mismas referencias a los objetos Flight.
            // Si los Flight también necesitaran ser clonados profundamente, se haría aquí.
            cloned.flights = new ArrayList<>(this.flights);
            return cloned;
        } catch (CloneNotSupportedException e) {
            // Esto no debería suceder ya que implementamos Cloneable
            throw new AssertionError("Cloning failed for Passenger", e);
        }
    }

    public void addFlight(Flight flight) {
        if (flight != null && !this.flights.contains(flight)) { // 'this.flights' es la lista interna
            this.flights.add(flight);
        }
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public long getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public ArrayList<Flight> getFlights() {
        // Devolver una copia de la lista para proteger la encapsulación
        return new ArrayList<>(flights);
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setCountryPhoneCode(int countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }

    public String generateFullPhone() {
        return "+" + countryPhoneCode + " " + phone;
    }

    public int calculateAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public int getNumFlights() {
        return flights.size();
    }
}
