package com.hairdresser.gui;

public class FreeSlotModel {
    private final int shiftId;
    private final String shiftHours;
    private final int availableSlots;

    public FreeSlotModel(int shiftId, String shiftHours, int availableSlots) {
        this.shiftId = shiftId;
        this.shiftHours = shiftHours;
        this.availableSlots = availableSlots;
    }

    public int getShiftId() {
        return shiftId;
    }

    public String getShiftHours() {
        return shiftHours;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }
}