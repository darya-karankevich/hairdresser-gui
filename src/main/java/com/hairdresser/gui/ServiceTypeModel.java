package com.hairdresser.gui;

public class ServiceTypeModel {
    private final int serviceTypeId;
    private final String serviceName;
    private double price;

    public ServiceTypeModel(int serviceTypeId, String serviceName) {
        this.serviceTypeId = serviceTypeId;
        this.serviceName = serviceName;

    }
    public ServiceTypeModel(int serviceTypeId, String serviceName, double price) {
        this.serviceTypeId = serviceTypeId;
        this.serviceName = serviceName;
        this.price = price;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}