package com.hairdresser.gui;

public class VisitModel {
    private final int visitId;
    private final int visitorId;
    private final String visitorName;
    private final int serviceTypeId;
    private final String serviceName;
    private final int userId;
    private final String masterName;
    private final int shiftId;
    private final String shiftHours;
    private final String visitDate;

    public VisitModel(int visitId, int visitorId, String visitorName, int serviceTypeId, String serviceName,
                      int userId, String masterName, int shiftId, String shiftHours, String visitDate) {
        this.visitId = visitId;
        this.visitorId = visitorId;
        this.visitorName = visitorName;
        this.serviceTypeId = serviceTypeId;
        this.serviceName = serviceName;
        this.userId = userId;
        this.masterName = masterName;
        this.shiftId = shiftId;
        this.shiftHours = shiftHours;
        this.visitDate = visitDate;
    }

    public int getVisitId() {
        return visitId;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getUserId() {
        return userId;
    }

    public String getMasterName() {
        return masterName;
    }

    public int getShiftId() {
        return shiftId;
    }

    public String getShiftHours() {
        return shiftHours;
    }

    public String getVisitDate() {
        return visitDate;
    }
}