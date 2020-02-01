package com.example.ticket;

public class name {

    private String Count,Name, Number, Seat,Place,Time,Date,Sex,Address,ConName;


    public name(String count, String name, String number, String seat, String place, String time, String date, String sex, String address, String conName) {
        Count = count;
        Name = name;
        Number = number;
        Seat = seat;
        Place = place;
        Time = time;
        Date = date;
        Sex = sex;
        Address = address;
        ConName = conName;
    }

    public String getName() {
        return Name;
    }

    public String getNumber() {
        return Number;
    }

    public String getSeat() {
        return Seat;
    }

    public String getPlace() {
        return Place;
    }

    public String getTime() {
        return Time;
    }

    public String getDate() {
        return Date;
    }

    public String getSex() {
        return Sex;
    }

    public String getAddress() {
        return Address;
    }

    public String getCount() {
        return Count;
    }

    public String getConName() {
        return ConName;
    }
}
