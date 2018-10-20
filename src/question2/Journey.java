/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question2;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a journey of boat trips between multiple ports.
 * 
 * @author Thong Teav
 */
public class Journey {
    //variables-----------------------------------------------------------------
    private LinkedList<BoatTrip> boatTripList;
    //--------------------------------------------------------------------------
    
    //constructors--------------------------------------------------------------
    /**
     * Default constructor that creates a new journey that is initially empty.
     */
    public Journey(){
        boatTripList = new LinkedList<>();
    }
    
    /**
     * Constructor for creating a new journey which initially holds the boat trips in the collection.
     * @param trips 
     */
    public Journey(List<BoatTrip> trips){
        boatTripList = new LinkedList<>(trips);
    }
    //--------------------------------------------------------------------------

    //helper methods------------------------------------------------------------
    /**
     * Adds a boat trip to the collection only if the journey so far does not already contain its arrival port.
     * 
     * @param trip the specified boat trip to be added to the collection
     * @return true if added successfully and false if otherwise
     */
    public boolean addTrip(BoatTrip trip) {
        if(this.getEndPort() == null) { //add the trip if it's empty
            boatTripList.add(trip);
            return true;
        }
        else {
            if (!this.containsPort(trip.getArrivalPort()) && this.getEndPort().equals(trip.getDepartPort())){
                boatTripList.add(trip);
                return true;
            }
            return false;
        }
    }
    
    /**
     * Removes the last trip of the journey if there is any.
     * 
     * @return true if removal is successful and false if otherwise
     */
    public boolean removeLastTrip(){
        if(!boatTripList.isEmpty()){
            boatTripList.removeLast();
            return true;
        }
        return false;
    }
    
    /**
     * Checks whether the journey so far has the specified port.
     * 
     * @param port the specified port to search
     * @return true if the given port is in the journey so far and false if otherwise
     */
    public boolean containsPort(String port){
        for(BoatTrip trip : boatTripList){
            if((trip.getArrivalPort().equals(port)) || (trip.getDepartPort().equals(port))){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the starting port in the journey if it is not empty.
     * 
     * @return a string representing the starting port or null if journey is empty
     */
    public String getStartPort() {
        if (!boatTripList.isEmpty()) {
            return boatTripList.getFirst().getDepartPort();
        }
        return null;
    }
    
    /**
     * Returns the ending port in the journey if it is not empty.
     * 
     * @return a string representing the ending port or null if journey is empty
     */
    public String getEndPort(){ 
        if(!boatTripList.isEmpty()){
            return boatTripList.getLast().getArrivalPort();
        }
        return null;        
    }
    
    /**
     * Returns the arrival date of the last trip in the journey.
     * 
     * @return a string representing the arrival date or null if journey is empty
     */
    public String getEndDate(){
        if(!boatTripList.isEmpty()){
            return boatTripList.getLast().getArrivalDate();
        }
        return null;
    }
    
    /**
     * Returns a newly created journey object using the list of boat trips in the current journey.
     * 
     * @return a clone of the current journey
     */
    public Journey createClone(){
        return new Journey(this.boatTripList);
    }
    
    /**
     * Returns the cost of the whole journey by adding the cost of each trip in the journey.
     * 
     * @return an integer representing the total cost of the journey
     */
    public int getTotalJourneyCost(){
        int cost = 0;
        
        for(BoatTrip trip : boatTripList){
            cost += trip.getCost();
        }
        
        return cost;
    }
    
    /**
     * Returns the number of boat trips in the list
     * @return 
     */
    public int size(){
        return this.boatTripList.size();
    }
    
    /**
     * Returns a string representation of a journey of boat trips     * 
     * @return 
     */
    @Override
    public String toString(){
        String output = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Journey<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n";
        int cost = 0;
        for(BoatTrip trip : boatTripList) {
            cost += trip.getCost();
            output += trip + "\n";
        }
        output += "Total cost: $" + cost;
        output += "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n";
        return output;
    }
    //-------------------------------------------------------------------------- 
    
    public static void main(String[] args) {
        BoatTrip trip1 = new BoatTrip("101", "A", "2017-01-01", "B", "2017-01-03", 500);
        BoatTrip trip2 = new BoatTrip("102", "B", "2017-01-04", "C", "2017-01-05", 300);
        BoatTrip trip3 = new BoatTrip("103", "B", "2017-01-05", "D", "2017-01-06", 400);
        BoatTrip trip4 = new BoatTrip("104", "C", "2017-01-06", "E", "2017-01-08", 1500);
        
        Journey myJourney = new Journey();
        System.out.println("Creating a new journey: \n" + myJourney);
        System.out.println("Adding trip1 to my journey: " + myJourney.addTrip(trip1));
        System.out.println("Adding trip2 to my journey: " + myJourney.addTrip(trip2));
        System.out.println("Adding trip3 to my journey: " + myJourney.addTrip(trip3));
        System.out.println("Adding trip4 to my journey: " + myJourney.addTrip(trip4));
        System.out.println("");
        System.out.println(myJourney);
    }
}
