/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.Core.Model;

import airport.Core.Model.Flight;
import airport.Core.Model.Operations.FlightManager;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author edangulo
 */
public class Passenger implements Cloneable {

    private final long id;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private int countryPhoneCode;
    private long phone;
    private String country;
    private final ArrayList<Flight> flights;

    public Passenger(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.countryPhoneCode = countryPhoneCode;
        this.phone = phone;
        this.country = country;
        this.flights = new ArrayList<>();
    }

    public Passenger(Passenger original) {
        this.id = original.id;
        this.firstname = original.firstname;
        this.lastname = original.lastname;
        this.birthDate = original.birthDate;
        this.countryPhoneCode = original.countryPhoneCode;
        this.phone = original.phone;
        this.country = original.country;
        this.flights = new ArrayList<>(original.flights);
    }

    @Override
    public Passenger clone() {
        try {
            Passenger cloned = (Passenger) super.clone();

            return (Passenger) super.clone(); // Mantiene el comportamiento original del prompt
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("La clonación falló para Passenger", e);
        }
    }

    /**
     * Permite gestionar la lista de vuelos del pasajero utilizando un
     * FlightManager.
     *
     * @param flight El vuelo a añadir (o potencialmente remover en el futuro).
     * @param manager El gestor de operaciones de vuelo.
     * @param add True para añadir, False para remover (la lógica de remover no
     * está implementada aquí).
     */
    public void manageFlights(Flight flight, FlightManager manager, boolean add) {
        if (add) {
            if (flight != null) {
                manager.addFlight(this.flights, flight); // FlightManagerNormal se encarga de no duplicar
            }
        } else {
            // Lógica para remover, si FlightManager tuviera un método removeFlight
            // manager.removeFlight(this.flights, flight);
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

    public List<Flight> getFlights() {
        return Collections.unmodifiableList(new ArrayList<>(this.flights));
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
