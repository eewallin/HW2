package com.example.erin.calendarapp;

/**
 * Created by Erin on 3/11/2016.
 */
public class Event {

    private int day;
    private int month;
    private int year;

    private String name;
    private String startTime;
    private String endTime;

    public Event (int year, int month, int day, String name, String startTime, String endTime) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getDay(){
        return day;
    }
    public int getMonth(){
        return month;
    }
    public int getYear(){
        return year;
    }
    public String getName(){
        return name;
    }
    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }
}
