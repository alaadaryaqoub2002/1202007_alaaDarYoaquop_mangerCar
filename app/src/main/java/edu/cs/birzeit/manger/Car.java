package edu.cs.birzeit.manger;

public class Car {
    private long id;
    private String make;
    private String model;
    private int year;
    private double price;

    public Car(long id, String make, String model, int year, double price) {
        this.id=id;
        this.model=model;
        this.price=price;
        this.make=make;
        this.year=year;
    }


    public Long getId() {
        return id;
    }
    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }
    public int getYear() {
        return year;
    }
    public double getPrice() {
        return price;
    }

}
