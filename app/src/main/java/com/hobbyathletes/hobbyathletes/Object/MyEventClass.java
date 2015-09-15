package com.hobbyathletes.hobbyathletes.Object;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyEventClass implements Serializable {

    /*
    {"error":"0","errorMessage":"","count_events":"1","events":[{"name":"Dextro Energy Triathlon 2009","location":"Germany, Hamburg","date":"2009-07-26","type":"triathlon","theme":"http://hobbyathletes.com/en/imgs/triathlon_34.png","link":"http://hobbyathletes.com/tri/Dextro-Energy-Triathlon-2009/main-169.html"}]}
     */

    private Integer id;
    private String event_name;
    private String location;
    private Date date;
    private String type;
    private String theme;
    private String link;
    private Integer myevents_ref;
    private Date last_updated;

    private MyEventRefClass mERC;

    public MyEventClass(){

    }

    //{"name":"Dextro Energy Triathlon 2009","location":"Germany, Hamburg","date":"2009-07-26","type":"triathlon","theme":"http://hobbyathletes.com/en/imgs/triathlon_34.png","link":"http://hobbyathletes.com/tri/Dextro-Energy-Triathlon-2009/main-169.html"}]}
    public MyEventClass(Integer id, String name, String location, String date, String type, String theme, String link, Integer myevents_ref, String last_updated){
        this.id = id;
        this.event_name = name;
        this.location = location;
        setDate(date);
        this.type = type;
        this.theme = theme;
        this.link = link;
        this.myevents_ref = myevents_ref;
        setLast_updated(last_updated);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        Date fulldate = null;

        try {
            fulldate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " " + "00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = fulldate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getMyevents_ref() {
        return myevents_ref;
    }

    public void setMyevents_ref(Integer myevents_ref) {
        this.myevents_ref = myevents_ref;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        Date fulldate = null;

        try {
            fulldate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(last_updated);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.last_updated = fulldate;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    public MyEventRefClass getMERC() {
        return mERC;
    }

    public void setMERC(MyEventRefClass mERC) {
        this.mERC = mERC;
    }

}
