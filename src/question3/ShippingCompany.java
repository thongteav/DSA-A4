/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import question2.BoatTrip;
import question2.Journey;

/**
 * Contains a list of journeys from startport to the endport.
 * @author Thong Teav
 */
public class ShippingCompany {
    //variables-----------------------------------------------------------------
    private Connection conn;
    private Statement stmt;
    private List<Journey> journeyList;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://raptor2.aut.ac.nz:3306/testRestricted";
    //--------------------------------------------------------------------------
    
    //constructor---------------------------------------------------------------
    public ShippingCompany(String dbUser, String dbPassword){
        try {
            Class.forName(DRIVER);
            System.out.println("Connecting to the database...");
            conn = DriverManager.getConnection(DB_URL, dbUser, dbPassword);                
        } catch (ClassNotFoundException ex) {
            System.out.println("Class is not found: " + ex);
        } catch (SQLException ex) {
            System.out.println("SQL failed: " + ex);
        }
    }
    //--------------------------------------------------------------------------
    
    /**
     * Returns a list of ports available by query the table Ports in the database
     * @return 
     */
    public List<String> readAllPorts(){        
        List<String> ports = new ArrayList<>();
        if(conn == null){
            System.out.println("Connection is closed");
        } else {            
            try {
                stmt = conn.createStatement();
                String query = "SELECT portName FROM Ports";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    ports.add(rs.getString(1));//add the port names to the arraylist
                }
                stmt.close();
            } catch (SQLException ex) {
                System.out.println("SQL failed: " + ex);
            }
        }
        return ports;
    }
    
    /**
     * Returns a list of possible journeys from a start port to the end port after the certain date.
     * 
     * @param startPort
     * @param startDate
     * @param endPort
     * @return 
     */
    public List<Journey> getAllJourneys(String startPort, String startDate, String endPort){
        journeyList = new ArrayList<>();
        Journey journey = new Journey();
        findPaths(journey, startDate, startPort, endPort);
        return journeyList;
    }
    
    /**
     * A recursive method which performs a depth-first search of all the possible paths that a Journey can take.
     * 
     * @param currentJourney
     * @param startDate
     * @param startPort
     * @param endPort 
     */
    private void findPaths(Journey currentJourney, String startDate, String startPort, String endPort) {
        //base case
        if (startPort.equals(endPort)) { //at the end of the journey
            if(currentJourney.size() != 0) {
                journeyList.add(currentJourney.createClone()); //create a clone of the current journey and add it to the possible journey list
            }
            return;
        }
        
        try {
            List<BoatTrip> tempTrips = new ArrayList<BoatTrip>();
            stmt = conn.createStatement();
            String query = "SELECT * FROM Shipping WHERE departPort = '" + startPort + "' AND departDate >= '" + startDate + "'";//query database all boat trips leaving current port after start date
            ResultSet rs = stmt.executeQuery(query);                        
            while (rs.next()) {//create a boat trip for each record and add them to the temp arraylist of trips
                BoatTrip trip = new BoatTrip(rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(3), rs.getString(5), rs.getInt(6));
                tempTrips.add(trip);
            }
            stmt.close();//closes the statement after reading the result set
            for(BoatTrip trip : tempTrips){//loop for each trip and add them to current journey
                if(currentJourney.addTrip(trip)) {//only find path and remove last trip if trip is added successfully
                    findPaths(currentJourney, currentJourney.getEndDate(), currentJourney.getEndPort(), endPort);
                    currentJourney.removeLastTrip();
                }                
            }
        } catch (SQLException ex) {
            System.out.println("SQL failed: " + ex);
        }
    }
    
    /**
     * Closes any opened statement and connection
     */
    public void close(){
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException ex) {
                System.out.println("SQL failed: " + ex);
            }
        }
        if(conn != null){
            try {
                System.out.println("Closing the connection...");
                conn.close();
            } catch (SQLException ex) {
                System.out.println("SQL failed: " + ex);
            }
        }
    }
    
    /**
     * Returns a string of all possible journeys from the list
     * @return 
     */
    @Override
    public String toString(){
        String output = "";
        if(!journeyList.isEmpty()){ //if no possible journey
            output += journeyList.size() + (journeyList.size() == 1 ? " possible journey includes:\n " : " possible journeys include:\n"); //check if there are 1 or more than 1
            for(Journey journey : journeyList){
                output += journey;
            }
        } else {
            output += "Sorry, no journey is available";
        }
        return output;
    }
    
    public static void main(String[] args){
        System.out.println("WELCOME TO SHIPPING COMPANY");
        Scanner keyboard = new Scanner(System.in);
        
        System.out.print("Please enter your username: ");        
        String username = keyboard.nextLine();
        System.out.print("Please enter the password: ");
        String password = keyboard.nextLine();
        ShippingCompany sc = new ShippingCompany(username, password);

        String startPort = "Auckland";
        String endPort = "Samoa";
        String startDate = "2017-01-01";
        System.out.println("====================================================");
        System.out.println("Start port: " + startPort);
        System.out.println("End port: " + endPort);
        System.out.println("Leaving on: " + startDate);
        System.out.println("====================================================\n");
        sc.getAllJourneys(startPort, startDate, endPort);
        System.out.println(sc);
        sc.close();
    }
}
