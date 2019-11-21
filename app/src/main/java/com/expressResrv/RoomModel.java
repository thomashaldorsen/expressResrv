package com.expressResrv;

//Klasse til DB - med getters & setters. Her må vi finne ut hvilken informasjon vi skal vise fram.

public class RoomModel {

    private String name, roomnumber, description;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getRoomnumber() {

        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {

        this.roomnumber = roomnumber;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }
}
