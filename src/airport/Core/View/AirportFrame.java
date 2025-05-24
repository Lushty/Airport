/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package airport.Core.View;

import airport.Core.Controller.FlightController;
import airport.Core.Controller.LocationController;
import airport.Core.Controller.PassengerController;
import airport.Core.Controller.PlaneController;
import airport.Core.Controller.Utils.Response;
import airport.Core.Model.Flight;
import airport.Core.Model.Location;
import airport.Core.Model.Passenger;
import airport.Core.Model.Plane;
import airport.Core.Model.Storage.Storage;
import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author edangulo
 */
public class AirportFrame extends javax.swing.JFrame {

    /**
     * Creates new form AirportFrame
     */
    private int x, y;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AirportFrame() {
        initComponents();
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);

        this.generateMonths();
        this.generateDays();
        this.generateHours();
        this.generateMinutes();
        // populateComboBoxes() se llama antes de configurar el estado de las pestañas
        populateComboBoxes();

        // Quitar o reevaluar blockPanels() si su lógica entra en conflicto.
        // blockPanels(); 
        // Establecer estado inicial de UI (ejemplo: rol User por defecto, sin pasajero seleccionado)
        this.user.setSelected(false); // Asegura que el radio button user esté seleccionado
        this.administrator.setSelected(false);
        userActionPerformed(null); // Establece el estado de las pestañas para el rol de usuario sin un ID de pasajero específico
        // userSelectActionPerformed(null) se llamará desde userActionPerformed

    }

    private void populateComboBoxes() {
        // User Select (Pasajeros)
        Object firstUser = userSelect.getItemCount() > 0 ? userSelect.getItemAt(0) : "Select User";
        userSelect.removeAllItems();
        userSelect.addItem(firstUser.toString());
        ArrayList<String> passengerIds = PassengerController.getAllPassengerIds(); // Cambiado a ArrayList
        if (passengerIds != null) {
            for (String passengerId : passengerIds) {
                userSelect.addItem(passengerId);
            }
        }

        // Flight Plane (Aviones)
        Object firstPlane = flightPlane.getItemCount() > 0 ? flightPlane.getItemAt(0) : "Plane";
        flightPlane.removeAllItems();
        flightPlane.addItem(firstPlane.toString());
        ArrayList<String> planeIds = PlaneController.getAllPlaneIds(); // Cambiado a ArrayList
        if (planeIds != null) {
            for (String planeId : planeIds) {
                flightPlane.addItem(planeId);
            }
        }

        // Departure, Arrival, Scale Locations (Ubicaciones)
        Object firstLocation = departureLocation.getItemCount() > 0 ? departureLocation.getItemAt(0) : "Location";
        departureLocation.removeAllItems();
        arrivalLocation.removeAllItems();
        scaleLocation.removeAllItems();

        departureLocation.addItem(firstLocation.toString());
        arrivalLocation.addItem(firstLocation.toString());
        scaleLocation.addItem(firstLocation.toString());

        ArrayList<String> locationAirportIds = LocationController.getAllLocationAirportIds(); // Cambiado a ArrayList
        if (locationAirportIds != null) {
            for (String airportId : locationAirportIds) {
                departureLocation.addItem(airportId);
                arrivalLocation.addItem(airportId);
                scaleLocation.addItem(airportId);
            }
        }

        // Add Flight Select (Vuelos)
        Object firstFlight = addFlightSelect.getItemCount() > 0 ? addFlightSelect.getItemAt(0) : "Flight";
        addFlightSelect.removeAllItems();
        addFlightSelect.addItem(firstFlight.toString());
        ArrayList<String> flightIds = FlightController.getAllFlightIds(); // Cambiado a ArrayList
        if (flightIds != null) {
            for (String flightId : flightIds) {
                addFlightSelect.addItem(flightId);
            }
        }

        // Delay ID (Vuelos)
        Object firstDelayId = delayID.getItemCount() > 0 ? delayID.getItemAt(0) : "ID";
        delayID.removeAllItems();
        delayID.addItem(firstDelayId.toString());
        if (flightIds != null) { // Reusar flightIds
            for (String flightId : flightIds) {
                delayID.addItem(flightId);
            }
        }
    }

    private void blockPanels() {
        //9, 11
        for (int i = 1; i < myFlightsTable.getTabCount(); i++) {
            if (i != 9 && i != 11) {
                myFlightsTable.setEnabledAt(i, false);
            }
        }
    }

    private void generateMonths() {
        for (int i = 1; i < 13; i++) {
            passengerMonth.addItem("" + i);
            departureMonth.addItem("" + i);
            updateMonth.addItem("" + i);
        }
    }

    private void generateDays() {
        for (int i = 1; i < 32; i++) {
            passengerDay.addItem("" + i);
            departureDay.addItem("" + i);
            updateDay.addItem("" + i);
        }
    }

    private void generateHours() {
        for (int i = 0; i < 24; i++) {
            MONTH2.addItem("" + i);
            arrivalHour.addItem("" + i);
            scaleHour.addItem("" + i);
            delayHour.addItem("" + i);
        }
    }

    private void generateMinutes() {
        for (int i = 0; i < 60; i++) {
            DAY2.addItem("" + i);
            arrivalMinute.addItem("" + i);
            scaleMinute.addItem("" + i);
            delayMinute.addItem("" + i);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new airport.Core.View.PanelRound();
        panelRound2 = new airport.Core.View.PanelRound();
        exit = new javax.swing.JButton();
        myFlightsTable = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        user = new javax.swing.JRadioButton();
        administrator = new javax.swing.JRadioButton();
        userSelect = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        passengerCCode = new javax.swing.JTextField();
        passengerID = new javax.swing.JTextField();
        passengerYear = new javax.swing.JTextField();
        passengerCountry = new javax.swing.JTextField();
        passengerPhone = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        passengerSurname = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        passengerMonth = new javax.swing.JComboBox<>();
        passengerName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        passengerDay = new javax.swing.JComboBox<>();
        passengerCreate = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        planeID = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        planeBrand = new javax.swing.JTextField();
        planeModel = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        planeMaxCap = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        planeAirline = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        planeCreate = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        locationID = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        loationName = new javax.swing.JTextField();
        locationCity = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        locationCountry = new javax.swing.JTextField();
        locationLat = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        locationLong = new javax.swing.JTextField();
        locationCreate = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        flightID = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        flightPlane = new javax.swing.JComboBox<>();
        departureLocation = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        arrivalLocation = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        scaleLocation = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        departureYear = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        departureMonth = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        departureDay = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        MONTH2 = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        DAY2 = new javax.swing.JComboBox<>();
        arrivalHour = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        arrivalMinute = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        scaleHour = new javax.swing.JComboBox<>();
        scaleMinute = new javax.swing.JComboBox<>();
        flightCreate = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        updateID = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        updateName = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        updateSurname = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        updateYear = new javax.swing.JTextField();
        updateMonth = new javax.swing.JComboBox<>();
        updateDay = new javax.swing.JComboBox<>();
        updatePhone = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        updateCCode = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        updateCountry = new javax.swing.JTextField();
        update = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        addFlightID = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        addFlightSelect = new javax.swing.JComboBox<>();
        addFlight = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        flightsRefresh = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        passengersTable = new javax.swing.JTable();
        passengersRefresh = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        flightsTable = new javax.swing.JTable();
        generalFlightsRefresh = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        planesRefresh = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        planesTable = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        locationsTable = new javax.swing.JTable();
        locationsRefresh = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        delayHour = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        delayID = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        delayMinute = new javax.swing.JComboBox<>();
        delayCreate = new javax.swing.JButton();
        panelRound3 = new airport.Core.View.PanelRound();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelRound1.setRadius(40);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelRound2MouseDragged(evt);
            }
        });
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelRound2MousePressed(evt);
            }
        });

        exit.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        exit.setText("X");
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap(1083, Short.MAX_VALUE)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addComponent(exit)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        panelRound1.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, -1));

        myFlightsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        user.setText("User");
        user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userActionPerformed(evt);
            }
        });
        jPanel1.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, -1, -1));

        administrator.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        administrator.setText("Administrator");
        administrator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administratorActionPerformed(evt);
            }
        });
        jPanel1.add(administrator, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 164, -1, -1));

        userSelect.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        userSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select User" }));
        userSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userSelectActionPerformed(evt);
            }
        });
        jPanel1.add(userSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 130, -1));

        myFlightsTable.addTab("Administration", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel1.setText("Country:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel2.setText("ID:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel3.setText("First Name:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel4.setText("Last Name:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel5.setText("Birthdate:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel6.setText("+");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 20, -1));

        passengerCCode.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(passengerCCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 50, -1));

        passengerID.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(passengerID, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 130, -1));

        passengerYear.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(passengerYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 90, -1));

        passengerCountry.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(passengerCountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 130, -1));

        passengerPhone.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(passengerPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, 130, -1));

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel7.setText("Phone:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel8.setText("-");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, 30, -1));

        passengerSurname.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(passengerSurname, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 130, -1));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel9.setText("-");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 30, -1));

        passengerMonth.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));
        jPanel2.add(passengerMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, -1, -1));

        passengerName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(passengerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 130, -1));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel10.setText("-");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 30, -1));

        passengerDay.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));
        jPanel2.add(passengerDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, -1, -1));

        passengerCreate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengerCreate.setText("Register");
        passengerCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passengerCreateActionPerformed(evt);
            }
        });
        jPanel2.add(passengerCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 480, -1, -1));

        myFlightsTable.addTab("Passenger registration", jPanel2);

        jPanel3.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel11.setText("ID:");
        jPanel3.add(jLabel11);
        jLabel11.setBounds(53, 96, 22, 25);

        planeID.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(planeID);
        planeID.setBounds(180, 93, 130, 31);

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel12.setText("Brand:");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(53, 157, 52, 25);

        planeBrand.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(planeBrand);
        planeBrand.setBounds(180, 154, 130, 31);

        planeModel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(planeModel);
        planeModel.setBounds(180, 213, 130, 31);

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel13.setText("Model:");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(53, 216, 57, 25);

        planeMaxCap.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(planeMaxCap);
        planeMaxCap.setBounds(180, 273, 130, 31);

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel14.setText("Max Capacity:");
        jPanel3.add(jLabel14);
        jLabel14.setBounds(53, 276, 114, 25);

        planeAirline.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(planeAirline);
        planeAirline.setBounds(180, 333, 130, 31);

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel15.setText("Airline:");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(53, 336, 70, 25);

        planeCreate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        planeCreate.setText("Create");
        planeCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                planeCreateActionPerformed(evt);
            }
        });
        jPanel3.add(planeCreate);
        planeCreate.setBounds(490, 480, 120, 40);

        myFlightsTable.addTab("Airplane registration", jPanel3);

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel16.setText("Airport ID:");

        locationID.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel17.setText("Airport name:");

        loationName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        locationCity.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel18.setText("Airport city:");

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel19.setText("Airport country:");

        locationCountry.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        locationLat.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel20.setText("Airport latitude:");

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel21.setText("Airport longitude:");

        locationLong.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        locationCreate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        locationCreate.setText("Create");
        locationCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(locationLong, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(locationID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loationName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(locationCity, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(locationCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(locationLat, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(515, 515, 515)
                        .addComponent(locationCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(515, 515, 515))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel17)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel18)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel19)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel20))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(locationID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(loationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(locationCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(locationCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(locationLat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(locationLong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(locationCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        myFlightsTable.addTab("Location registration", jPanel13);

        jLabel22.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel22.setText("ID:");

        flightID.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel23.setText("Plane:");

        flightPlane.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        flightPlane.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Plane" }));

        departureLocation.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        departureLocation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        jLabel24.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel24.setText("Departure location:");

        arrivalLocation.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        arrivalLocation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel25.setText("Arrival location:");

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel26.setText("Scale location:");

        scaleLocation.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        scaleLocation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        jLabel27.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel27.setText("Duration:");

        jLabel28.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel28.setText("Duration:");

        jLabel29.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel29.setText("Departure date:");

        departureYear.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel30.setText("-");

        departureMonth.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        departureMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel31.setText("-");

        departureDay.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        departureDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));

        jLabel32.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel32.setText("-");

        MONTH2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MONTH2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel33.setText("-");

        DAY2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DAY2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        arrivalHour.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        arrivalHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel34.setText("-");

        arrivalMinute.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        arrivalMinute.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel35.setText("-");

        scaleHour.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        scaleHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        scaleMinute.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        scaleMinute.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        flightCreate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        flightCreate.setText("Create");
        flightCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flightCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scaleLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(arrivalLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(46, 46, 46)
                        .addComponent(departureLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(flightID)
                            .addComponent(flightPlane, 0, 130, Short.MAX_VALUE))))
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(departureYear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(departureMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(departureDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(MONTH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(DAY2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(arrivalHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(arrivalMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(scaleHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(scaleMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(flightCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(530, 530, 530))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel22))
                    .addComponent(flightID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(flightPlane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MONTH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(DAY2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel24)
                                .addComponent(departureLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29))
                            .addComponent(departureYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(departureMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(departureDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel25)
                                .addComponent(arrivalLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28))
                            .addComponent(arrivalHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)
                            .addComponent(arrivalMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scaleHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(scaleMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26)
                                .addComponent(scaleLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel27)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addComponent(flightCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        myFlightsTable.addTab("Flight registration", jPanel4);

        jLabel36.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel36.setText("ID:");

        updateID.setEditable(false);
        updateID.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateID.setEnabled(false);

        jLabel37.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel37.setText("First Name:");

        updateName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel38.setText("Last Name:");

        updateSurname.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel39.setText("Birthdate:");

        updateYear.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        updateMonth.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));

        updateDay.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updateDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));

        updatePhone.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel40.setText("-");

        updateCCode.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel41.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel41.setText("+");

        jLabel42.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel42.setText("Phone:");

        jLabel43.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel43.setText("Country:");

        updateCountry.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        update.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addGap(108, 108, 108)
                                .addComponent(updateID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(41, 41, 41)
                                .addComponent(updateName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(43, 43, 43)
                                .addComponent(updateSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addGap(55, 55, 55)
                                .addComponent(updateYear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(updateMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(updateDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(updateCCode, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(updatePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addGap(63, 63, 63)
                                .addComponent(updateCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(507, 507, 507)
                        .addComponent(update)))
                .addContainerGap(555, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(updateID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(updateName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(updateSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(updateYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel41)
                    .addComponent(updateCCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(updatePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(updateCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(update)
                .addGap(113, 113, 113))
        );

        myFlightsTable.addTab("Update info", jPanel5);

        addFlightID.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        addFlightID.setEnabled(false);

        jLabel44.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel44.setText("ID:");

        jLabel45.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel45.setText("Flight:");

        addFlightSelect.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        addFlightSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Flight" }));

        addFlight.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        addFlight.setText("Add");
        addFlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFlightActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45))
                .addGap(79, 79, 79)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addFlightSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addFlightID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(829, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addFlight, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(509, 509, 509))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel44))
                    .addComponent(addFlightID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(addFlightSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                .addComponent(addFlight, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );

        myFlightsTable.addTab("Add to flight", jPanel6);

        jTable1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Departure Date", "Arrival Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        flightsRefresh.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        flightsRefresh.setText("Refresh");
        flightsRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flightsRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(flightsRefresh)
                .addGap(527, 527, 527))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(flightsRefresh)
                .addContainerGap())
        );

        myFlightsTable.addTab("Show my flights", jPanel7);

        passengersTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Birthdate", "Age", "Phone", "Country", "Num Flight"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(passengersTable);

        passengersRefresh.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passengersRefresh.setText("Refresh");
        passengersRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passengersRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(489, 489, 489)
                        .addComponent(passengersRefresh))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(passengersRefresh)
                .addContainerGap())
        );

        myFlightsTable.addTab("Show all passengers", jPanel8);

        flightsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        flightsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Departure Airport ID", "Arrival Airport ID", "Scale Airport ID", "Departure Date", "Arrival Date", "Plane ID", "Number Passengers"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(flightsTable);

        generalFlightsRefresh.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        generalFlightsRefresh.setText("Refresh");
        generalFlightsRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generalFlightsRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(521, 521, 521)
                        .addComponent(generalFlightsRefresh)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(generalFlightsRefresh)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        myFlightsTable.addTab("Show all flights", jPanel9);

        planesRefresh.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        planesRefresh.setText("Refresh");
        planesRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                planesRefreshActionPerformed(evt);
            }
        });

        planesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brand", "Model", "Max Capacity", "Airline", "Number Flights"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(planesTable);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(planesRefresh))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(planesRefresh)
                .addGap(17, 17, 17))
        );

        myFlightsTable.addTab("Show all planes", jPanel10);

        locationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Airport ID", "Airport Name", "City", "Country"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(locationsTable);

        locationsRefresh.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        locationsRefresh.setText("Refresh");
        locationsRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationsRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(locationsRefresh))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(locationsRefresh)
                .addGap(17, 17, 17))
        );

        myFlightsTable.addTab("Show all locations", jPanel11);

        delayHour.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        delayHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel46.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel46.setText("Hours:");

        jLabel47.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel47.setText("ID:");

        delayID.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        delayID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID" }));

        jLabel48.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel48.setText("Minutes:");

        delayMinute.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        delayMinute.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        delayCreate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        delayCreate.setText("Delay");
        delayCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delayCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(delayMinute, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel46))
                        .addGap(79, 79, 79)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(delayHour, 0, 105, Short.MAX_VALUE)
                            .addComponent(delayID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(820, 820, 820))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(delayCreate)
                .addGap(531, 531, 531))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(delayID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(delayHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(delayMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
                .addComponent(delayCreate)
                .addGap(33, 33, 33))
        );

        myFlightsTable.addTab("Delay flight", jPanel12);

        panelRound1.add(myFlightsTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 41, 1150, 620));

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1150, Short.MAX_VALUE)
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        panelRound1.add(panelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 660, 1150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void panelRound2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_panelRound2MousePressed

    private void panelRound2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_panelRound2MouseDragged

    private void administratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administratorActionPerformed
        if (user.isSelected()) {
            user.setSelected(false);
        }
        userSelect.setSelectedIndex(0);
        addFlightID.setText("");
        updateID.setText("");

        // Habilitar todas las pestañas de administrador
        myFlightsTable.setEnabledAt(1, true);  // Passenger registration
        myFlightsTable.setEnabledAt(2, true);  // Airplane registration
        myFlightsTable.setEnabledAt(3, true);  // Location registration
        myFlightsTable.setEnabledAt(4, true);  // Flight registration
        myFlightsTable.setEnabledAt(8, true);  // Show all passengers
        myFlightsTable.setEnabledAt(9, true);  // Show all flights
        myFlightsTable.setEnabledAt(10, true); // Show all planes
        myFlightsTable.setEnabledAt(11, true); // Show all locations
        myFlightsTable.setEnabledAt(12, true); // Delay flight

        // Deshabilitar pestañas específicas de usuario
        myFlightsTable.setEnabledAt(5, false); // "Update info" (Índice 5)
        myFlightsTable.setEnabledAt(6, false); // "Add to flight" (Índice 6)
        myFlightsTable.setEnabledAt(7, false); // "Show my flights" (Índice 7)
    }//GEN-LAST:event_administratorActionPerformed

    private void userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userActionPerformed
        if (administrator.isSelected()) {
            administrator.setSelected(false);
        }
        // Al seleccionar el ROL "User", deshabilitamos la mayoría de las pestañas inicialmente.
        for (int i = 1; i < myFlightsTable.getTabCount(); i++) {
            myFlightsTable.setEnabledAt(i, false);
        }

        // Pestañas que se habilitan para el ROL "User" incluso sin un ID de pasajero específico:
        // myFlightsTable.setEnabledAt(1, true);  // Passenger registration (se controlará en userSelectActionPerformed)
        myFlightsTable.setEnabledAt(9, true);  // Show all flights
        myFlightsTable.setEnabledAt(10, true); // Show all planes
        myFlightsTable.setEnabledAt(11, true); // Show all locations

        userSelectActionPerformed(null);

    }//GEN-LAST:event_userActionPerformed

    private void passengerCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passengerCreateActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        String id = passengerID.getText();
        String firstname = passengerName.getText();
        String lastname = passengerSurname.getText();
        String year = passengerYear.getText();
        String month = passengerMonth.getItemAt(passengerMonth.getSelectedIndex());
        String day = passengerDay.getItemAt(passengerDay.getSelectedIndex());
        String phoneCode = passengerCCode.getText();
        String phone = passengerPhone.getText();
        String country = passengerCountry.getText();

        Response response = PassengerController.registerPassenger(id, firstname, lastname, year, month, day, phoneCode, phone, country); //

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar campos
            passengerID.setText("");
            passengerName.setText("");
            passengerSurname.setText("");
            passengerYear.setText("");
            passengerMonth.setSelectedIndex(0);
            passengerDay.setSelectedIndex(0);
            passengerCCode.setText("");
            passengerPhone.setText("");
            passengerCountry.setText("");

            // Repoblar el ComboBox de usuarios
            populateComboBoxes(); // O una versión más específica si solo quieres recargar userSelect
        }

    }//GEN-LAST:event_passengerCreateActionPerformed

    private void planeCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_planeCreateActionPerformed
        String id = planeID.getText();
        String brand = planeBrand.getText();
        String model = planeModel.getText();
        String maxCapacityStr = planeMaxCap.getText(); // Obtener como String
        String airline = planeAirline.getText();

        Response response = PlaneController.createPlane(id, brand, model, maxCapacityStr, airline);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Warning " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else { // Asumimos Status.CREATED o Status.OK
            JOptionPane.showMessageDialog(this, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar campos
            planeID.setText("");
            planeBrand.setText("");
            planeModel.setText("");
            planeMaxCap.setText("");
            planeAirline.setText("");

            populateComboBoxes(); // Actualizar JComboBoxes que listan aviones
        }

    }//GEN-LAST:event_planeCreateActionPerformed

    private void locationCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationCreateActionPerformed
        String id = locationID.getText();
        String name = loationName.getText(); // Corregir posible typo: loationName a locationName si es necesario
        String city = locationCity.getText();
        String country = locationCountry.getText();
        String latitudeStr = locationLat.getText();
        String longitudeStr = locationLong.getText();

        Response response = LocationController.createLocation(id, name, city, country, latitudeStr, longitudeStr);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Warning " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar campos
            locationID.setText("");
            loationName.setText(""); // locationName
            locationCity.setText("");
            locationCountry.setText("");
            locationLat.setText("");
            locationLong.setText("");

            populateComboBoxes(); // Actualizar JComboBoxes que listan ubicaciones
        }

    }//GEN-LAST:event_locationCreateActionPerformed

    private void flightCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flightCreateActionPerformed
        // TODO add your handling code here:
        String id = flightID.getText();
        String planeId = flightPlane.getSelectedItem().toString();
        String departureLocationId = departureLocation.getSelectedItem().toString();
        String arrivalLocationId = arrivalLocation.getSelectedItem().toString();
        String scaleLocationId = scaleLocation.getSelectedItem().toString();

        String depYear = departureYear.getText();
        String depMonth = departureMonth.getSelectedItem().toString();
        String depDay = departureDay.getSelectedItem().toString();
        // Asegúrate de que MONTH2 y DAY2 son para la hora y minuto de salida respectivamente
        String departureHour = MONTH2.getSelectedItem().toString(); // Nombre de variable confuso, debería ser departureHourSelect
        String departureMinute = DAY2.getSelectedItem().toString(); // Nombre de variable confuso, debería ser departureMinuteSelect

        String hoursDurationArrival = arrivalHour.getSelectedItem().toString();
        String minutesDurationArrival = arrivalMinute.getSelectedItem().toString();
        String hoursDurationScale = scaleHour.getSelectedItem().toString();
        String minutesDurationScale = scaleMinute.getSelectedItem().toString();

        // El controlador de FlightController espera "Location" o "" si no hay escala.
        // Ajusta scaleLocationId si el valor por defecto del JComboBox es "Location" y no se selecciona nada.
        if ("Location".equals(scaleLocationId)) {
            scaleLocationId = null; // O una cadena vacía si el controlador lo maneja así
        }
        if ("Plane".equals(planeId)) {
            planeId = null;
        }
        if ("Location".equals(departureLocationId)) {
            departureLocationId = null;
        }
        if ("Location".equals(arrivalLocationId)) {
            arrivalLocationId = null;
        }
        // Similar para los JComboBox de hora/minuto si tienen un valor por defecto como "Hour" o "Minute"
        if ("Month".equals(depMonth)) {
            departureMonth = null;
        }
        if ("Day".equals(depDay)) {
            departureDay = null;
        }
        if ("Hour".equals(departureHour)) {
            departureHour = null;
        }
        if ("Minute".equals(departureMinute)) {
            departureMinute = null;
        }
        if ("Hour".equals(hoursDurationArrival)) {
            hoursDurationArrival = null;
        }
        if ("Minute".equals(minutesDurationArrival)) {
            minutesDurationArrival = null;
        }
        if ("Hour".equals(hoursDurationScale)) {
            hoursDurationScale = null;
        }
        if ("Minute".equals(minutesDurationScale)) {
            minutesDurationScale = null;
        }

        Response response = FlightController.createFlight(id, planeId, departureLocationId, arrivalLocationId, scaleLocationId,
                depYear, depMonth, depDay, departureHour, departureMinute,
                hoursDurationArrival, minutesDurationArrival,
                hoursDurationScale, minutesDurationScale);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Warning " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar todos los campos del formulario de vuelo
            flightID.setText("");
            flightPlane.setSelectedIndex(0);
            departureLocation.setSelectedIndex(0);
            arrivalLocation.setSelectedIndex(0);
            scaleLocation.setSelectedIndex(0);
            departureYear.setText("");
            departureMonth.setSelectedIndex(0);
            departureDay.setSelectedIndex(0);
            MONTH2.setSelectedIndex(0); // departureHourSelect
            DAY2.setSelectedIndex(0);   // departureMinuteSelect
            arrivalHour.setSelectedIndex(0);
            arrivalMinute.setSelectedIndex(0);
            scaleHour.setSelectedIndex(0);
            scaleMinute.setSelectedIndex(0);

            populateComboBoxes(); // Actualizar JComboBoxes que listan vuelos (addFlightSelect, delayID)
        }

    }//GEN-LAST:event_flightCreateActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        String idStr = updateID.getText(); // Este ID viene del usuario seleccionado, no es editable por el usuario aquí.
        String firstname = updateName.getText();
        String lastname = updateSurname.getText();

        String yearStr = updateYear.getText();
        String monthStr = updateMonth.getSelectedItem() != null ? updateMonth.getSelectedItem().toString() : null;
        String dayStr = updateDay.getSelectedItem() != null ? updateDay.getSelectedItem().toString() : null;

        String phoneCodeStr = updateCCode.getText();
        String phoneStr = updatePhone.getText();
        String country = updateCountry.getText();

        // Manejo de placeholders para mes y día si es necesario (si tienen "Month", "Day" como items)
        if ("Month".equals(monthStr)) {
            monthStr = null;
        }
        if ("Day".equals(dayStr)) {
            dayStr = null;
        }

        if (idStr == null || idStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No passenger selected for update. Please select a user first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Response response = PassengerController.updatePassenger(idStr, firstname, lastname, yearStr, monthStr, dayStr, phoneCodeStr, phoneStr, country);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Warning " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else { // Status.OK
            JOptionPane.showMessageDialog(this, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar campos del formulario de actualización (excepto el ID que es de solo lectura)
            updateName.setText("");
            updateSurname.setText("");
            updateYear.setText("");
            updateMonth.setSelectedIndex(0); // Asumiendo que el primer item es el placeholder
            updateDay.setSelectedIndex(0);   // Asumiendo que el primer item es el placeholder
            updateCCode.setText("");
            updatePhone.setText("");
            updateCountry.setText("");

            // Opcional: podrías querer refrescar la tabla de pasajeros si está visible y mostrando este pasajero
            passengersRefreshActionPerformed(null); // Para refrescar la tabla de todos los pasajeros
        }
    }//GEN-LAST:event_updateActionPerformed

    private void addFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFlightActionPerformed
        String passengerIdStr = addFlightID.getText(); // ID del pasajero seleccionado (no editable)
        String flightIdStr = addFlightSelect.getSelectedItem() != null ? addFlightSelect.getSelectedItem().toString() : null;

        if (passengerIdStr == null || passengerIdStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No passenger selected. Please select a user first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (flightIdStr == null || flightIdStr.equals("Flight")) { // "Flight" es el placeholder
            JOptionPane.showMessageDialog(this, "Please select a flight.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Response response = PassengerController.addPassengerToFlight(passengerIdStr, flightIdStr);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Warning " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else { // Status.OK
            JOptionPane.showMessageDialog(this, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            addFlightSelect.setSelectedIndex(0); // Restablecer selección de vuelo

            // Opcional: Refrescar la tabla "Show my flights" si está visible y es el usuario actual
            if (myFlightsTable.getSelectedIndex() == 7 && userSelect.getSelectedItem().toString().equals(passengerIdStr)) {
                flightsRefreshActionPerformed(null); // Refresca "Show my flights"
            }
            // Opcional: Refrescar la tabla de "Show all flights" para ver el número actualizado de pasajeros
            // if (myFlightsTable.getSelectedIndex() == 9) { // Índice de "Show all flights"
            //    jButton4ActionPerformed(null);
            // }
            // Opcional: Refrescar la tabla de "Show all passengers" para ver el número de vuelos actualizado
            // if (myFlightsTable.getSelectedIndex() == 8) { // Índice de "Show all passengers"
            //    jButton3ActionPerformed(null);
            // }
        }
    }//GEN-LAST:event_addFlightActionPerformed

    private void delayCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delayCreateActionPerformed
        String flightId = delayID.getSelectedItem().toString();
        String hoursStr = delayHour.getSelectedItem().toString();
        String minutesStr = delayMinute.getSelectedItem().toString();

        // Similar a flightCreateActionPerformed, manejar valores por defecto de JComboBox
        if ("ID".equals(flightId)) {
            flightId = null;
        }
        if ("Hour".equals(hoursStr)) {
            hoursStr = null;
        }
        if ("Minute".equals(minutesStr)) {
            minutesStr = null;
        }

        Response response = FlightController.delayFlight(flightId, hoursStr, minutesStr);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Warning " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else { // Status.OK
            JOptionPane.showMessageDialog(this, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            delayID.setSelectedIndex(0);
            delayHour.setSelectedIndex(0);
            delayMinute.setSelectedIndex(0);
            // No es necesario llamar a populateComboBoxes() a menos que el retraso cambie algo en los combos.
            // Podrías querer refrescar la tabla de vuelos si está visible.
        }

    }//GEN-LAST:event_delayCreateActionPerformed

    private void flightsRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flightsRefreshActionPerformed

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Limpiar tabla

        // Asegurarse de que userSelect y su item seleccionado no sean null
        if (userSelect == null || userSelect.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "User selection is not available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedPassengerIdStr = userSelect.getSelectedItem().toString();
        if (selectedPassengerIdStr.equals("Select User")) {
            JOptionPane.showMessageDialog(this, "Please select a user first.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return; // No hacer nada si el placeholder está seleccionado
        }

        // Obtener datos a través del controlador
        ArrayList<Object[]> passengerFlightsData = PassengerController.getFlightsForPassenger(selectedPassengerIdStr);

        if (passengerFlightsData != null) {
            for (Object[] rowData : passengerFlightsData) {
                model.addRow(rowData);
            }
        } else {
            // Opcional: Manejar el caso donde el controlador podría devolver null (aunque lo configuramos para devolver lista vacía)
            System.err.println("flightsRefreshActionPerformed: passengerFlightsData fue null para el pasajero " + selectedPassengerIdStr);
        }

    }//GEN-LAST:event_flightsRefreshActionPerformed

    private void passengersRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passengersRefreshActionPerformed
        DefaultTableModel model = (DefaultTableModel) passengersTable.getModel();
        model.setRowCount(0); // Limpiar tabla

        ArrayList<Object[]> passengersData = PassengerController.getAllPassengersForTable();

        if (passengersData != null) {
            for (Object[] rowData : passengersData) {
                model.addRow(rowData);
            }
        } else {
            System.err.println("passengersRefreshActionPerformed: passengersData fue null.");
        }

    }//GEN-LAST:event_passengersRefreshActionPerformed

    private void generalFlightsRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalFlightsRefreshActionPerformed
        DefaultTableModel model = (DefaultTableModel) flightsTable.getModel();
        model.setRowCount(0); // Limpiar tabla

        ArrayList<Object[]> flightsData = FlightController.getAllFlightsForTable();

        if (flightsData != null) {
            for (Object[] rowData : flightsData) {
                model.addRow(rowData);
            }
        } else {
            System.err.println("generalFlightsRefreshActionPerformed: flightsData fue null.");
        }

    }//GEN-LAST:event_generalFlightsRefreshActionPerformed

    private void planesRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_planesRefreshActionPerformed
        DefaultTableModel model = (DefaultTableModel) planesTable.getModel();
        model.setRowCount(0); // Limpiar tabla

        ArrayList<Object[]> planesData = PlaneController.getAllPlanesForTable();

        if (planesData != null) {
            for (Object[] rowData : planesData) {
                model.addRow(rowData);
            }
        } else {
            System.err.println("planesRefreshActionPerformed: planesData fue null.");
        }

    }//GEN-LAST:event_planesRefreshActionPerformed

    private void locationsRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationsRefreshActionPerformed
        DefaultTableModel model = (DefaultTableModel) locationsTable.getModel();
        model.setRowCount(0); // Limpiar tabla

        ArrayList<Object[]> locationsData = LocationController.getAllLocationsForTable();

        if (locationsData != null) {
            for (Object[] rowData : locationsData) {
                model.addRow(rowData);
            }
        } else {
            System.err.println("locationsRefreshActionPerformed: locationsData fue null.");
        }

    }//GEN-LAST:event_locationsRefreshActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void userSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userSelectActionPerformed
        try {
            String selectedValue = userSelect.getSelectedItem() != null ? userSelect.getSelectedItem().toString() : "Select User";
            boolean isActualUserSelected = !selectedValue.equals("Select User") && user.isSelected();

            // Pestaña "Passenger registration" (índice 1):
            // Habilitada si el rol es "User" Y NINGÚN usuario específico está seleccionado.
            // Deshabilitada si un usuario específico ESTÁ seleccionado (o si el rol no es "User").
            if (user.isSelected()) {
                myFlightsTable.setEnabledAt(1, !isActualUserSelected);
            } else { // Si el rol no es "User" (es decir, es Admin o ninguno), el Admin lo controla.
                // administratorActionPerformed ya habilita la pestaña 1 para el admin.
                // Si ningún rol está seleccionado, permanece deshabilitada por el estado inicial.
                myFlightsTable.setEnabledAt(1, administrator.isSelected());
            }

            // Pestañas específicas de un ID de pasajero
            myFlightsTable.setEnabledAt(5, isActualUserSelected); // "Update info" 
            myFlightsTable.setEnabledAt(6, isActualUserSelected); // "Add to flight"
            myFlightsTable.setEnabledAt(7, isActualUserSelected); // "Show my flights"

            if (isActualUserSelected) {
                updateID.setText(selectedValue);
                addFlightID.setText(selectedValue);
            } else {
                updateID.setText("");
                addFlightID.setText("");
            }

            // Las pestañas generales para el ROL "User" (9, 10, 11) ya se habilitaron en userActionPerformed.
            // No es necesario tocarlas aquí a menos que su lógica cambie.
        } catch (Exception e) {
            System.err.println("Error en userSelectActionPerformed: " + e.getMessage());
            myFlightsTable.setEnabledAt(1, false); // Estado seguro en caso de error
            myFlightsTable.setEnabledAt(5, false);
            myFlightsTable.setEnabledAt(6, false);
            myFlightsTable.setEnabledAt(7, false);
            updateID.setText("");
            addFlightID.setText("");
        }

    }//GEN-LAST:event_userSelectActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> DAY2;
    private javax.swing.JComboBox<String> MONTH2;
    private javax.swing.JButton addFlight;
    private javax.swing.JTextField addFlightID;
    private javax.swing.JComboBox<String> addFlightSelect;
    private javax.swing.JRadioButton administrator;
    private javax.swing.JComboBox<String> arrivalHour;
    private javax.swing.JComboBox<String> arrivalLocation;
    private javax.swing.JComboBox<String> arrivalMinute;
    private javax.swing.JButton delayCreate;
    private javax.swing.JComboBox<String> delayHour;
    private javax.swing.JComboBox<String> delayID;
    private javax.swing.JComboBox<String> delayMinute;
    private javax.swing.JComboBox<String> departureDay;
    private javax.swing.JComboBox<String> departureLocation;
    private javax.swing.JComboBox<String> departureMonth;
    private javax.swing.JTextField departureYear;
    private javax.swing.JButton exit;
    private javax.swing.JButton flightCreate;
    private javax.swing.JTextField flightID;
    private javax.swing.JComboBox<String> flightPlane;
    private javax.swing.JButton flightsRefresh;
    private javax.swing.JTable flightsTable;
    private javax.swing.JButton generalFlightsRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField loationName;
    private javax.swing.JTextField locationCity;
    private javax.swing.JTextField locationCountry;
    private javax.swing.JButton locationCreate;
    private javax.swing.JTextField locationID;
    private javax.swing.JTextField locationLat;
    private javax.swing.JTextField locationLong;
    private javax.swing.JButton locationsRefresh;
    private javax.swing.JTable locationsTable;
    private javax.swing.JTabbedPane myFlightsTable;
    private airport.Core.View.PanelRound panelRound1;
    private airport.Core.View.PanelRound panelRound2;
    private airport.Core.View.PanelRound panelRound3;
    private javax.swing.JTextField passengerCCode;
    private javax.swing.JTextField passengerCountry;
    private javax.swing.JButton passengerCreate;
    private javax.swing.JComboBox<String> passengerDay;
    private javax.swing.JTextField passengerID;
    private javax.swing.JComboBox<String> passengerMonth;
    private javax.swing.JTextField passengerName;
    private javax.swing.JTextField passengerPhone;
    private javax.swing.JTextField passengerSurname;
    private javax.swing.JTextField passengerYear;
    private javax.swing.JButton passengersRefresh;
    private javax.swing.JTable passengersTable;
    private javax.swing.JTextField planeAirline;
    private javax.swing.JTextField planeBrand;
    private javax.swing.JButton planeCreate;
    private javax.swing.JTextField planeID;
    private javax.swing.JTextField planeMaxCap;
    private javax.swing.JTextField planeModel;
    private javax.swing.JButton planesRefresh;
    private javax.swing.JTable planesTable;
    private javax.swing.JComboBox<String> scaleHour;
    private javax.swing.JComboBox<String> scaleLocation;
    private javax.swing.JComboBox<String> scaleMinute;
    private javax.swing.JButton update;
    private javax.swing.JTextField updateCCode;
    private javax.swing.JTextField updateCountry;
    private javax.swing.JComboBox<String> updateDay;
    private javax.swing.JTextField updateID;
    private javax.swing.JComboBox<String> updateMonth;
    private javax.swing.JTextField updateName;
    private javax.swing.JTextField updatePhone;
    private javax.swing.JTextField updateSurname;
    private javax.swing.JTextField updateYear;
    private javax.swing.JRadioButton user;
    private javax.swing.JComboBox<String> userSelect;
    // End of variables declaration//GEN-END:variables
}
