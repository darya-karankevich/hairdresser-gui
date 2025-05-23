package com.hairdresser.gui;

public class MasterReportModel {
    private final int userId;
    private final String masterName;
    private final int visitCount;

    public MasterReportModel(int userId, String masterName, int visitCount) {
        this.userId = userId;
        this.masterName = masterName;
        this.visitCount = visitCount;
    }

    public int getUserId() {
        return userId;
    }

    public String getMasterName() {
        return masterName;
    }

    public int getVisitCount() {
        return visitCount;
    }
}