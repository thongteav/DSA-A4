/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question2;

public class BoatTrip {
    public String boatID;
    public String departPort;
    public String arrivalPort;
    public String departDate;
    public String arrivalDate;
    public int cost;

    public BoatTrip(String boatID, String departPort, String departDate, String arrivalPort, String arrivalDate, int cost) {
        this.boatID = boatID;
        this.departDate = departDate;
        this.departPort = departPort;
        this.arrivalDate = arrivalDate;
        this.arrivalPort = arrivalPort;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "BoatID: " + boatID + " from " + departPort + " at " + departDate + " TO " + arrivalPort + " at " + arrivalDate + " COST = " + cost;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BoatTrip) {
            BoatTrip trip = (BoatTrip) o;
            if (trip.arrivalPort.equals(arrivalPort) && trip.arrivalDate.equals(arrivalDate)) {
                return trip.boatID.equals(boatID);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getBoatID() {
        return boatID;
    }

    public String getDepartPort() {
        return departPort;
    }

    public String getArrivalPort() {
        return arrivalPort;
    }

    public String getDepartDate() {
        return departDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public int getCost() {
        return cost;
    } 
}
