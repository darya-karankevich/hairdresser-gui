package com.hairdresser.gui;

public class ShiftModel {
    private final int shiftId;
    private final String shiftHours;

    public ShiftModel(int shiftId, String shiftHours) {
        this.shiftId = shiftId;
        this.shiftHours = shiftHours;
    }

    public int getShiftId() {
        return shiftId;
    }

    public String getShiftHours() {
        return shiftHours;
    }
}