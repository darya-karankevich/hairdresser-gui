package com.hairdresser.gui;

public class VisitorModel {
    private final int visitorId;
    private final String fullName;
    private final String phoneNumber;

    public VisitorModel(int visitorId, String fullName, String phoneNumber) {
        this.visitorId = visitorId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}