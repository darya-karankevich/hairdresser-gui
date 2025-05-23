package com.hairdresser.gui;

public class ClientReportModel {
    private final int shiftId;
    private final String shiftHours;
    private final int clientCount;

    public ClientReportModel(int shiftId, String shiftHours, int clientCount) {
        this.shiftId = shiftId;
        this.shiftHours = shiftHours;
        this.clientCount = clientCount;
    }

    public int getShiftId() {
        return shiftId;
    }

    public String getShiftHours() {
        return shiftHours;
    }

    public int getClientCount() {
        return clientCount;
    }
}