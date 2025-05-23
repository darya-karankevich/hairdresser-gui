package com.hairdresser.gui;

public class SalaryModel {
    private final int userId;
    private final String masterName;
    private final double salary;

    public SalaryModel(int userId, String masterName, double salary) {
        this.userId = userId;
        this.masterName = masterName;
        this.salary = salary;
    }

    public int getUserId() {
        return userId;
    }

    public String getMasterName() {
        return masterName;
    }

    public double getSalary() {
        return salary;
    }
}