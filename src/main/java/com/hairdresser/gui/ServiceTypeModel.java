package com.hairdresser.gui;

import org.json.JSONObject;

public class ServiceTypeModel extends EntityModel {
    private static final long serialVersionUID = 1L;
    private int serviceTypeId;
    private String serviceName;

    public ServiceTypeModel(int serviceTypeId, String serviceName) {
        this.serviceTypeId = serviceTypeId;
        this.serviceName = serviceName;
    }

    public int getServiceTypeId() { return serviceTypeId; }
    public void setServiceTypeId(int serviceTypeId) { this.serviceTypeId = serviceTypeId; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("service_type_id", serviceTypeId);
        json.put("service_name", serviceName);
        return json.toString();
    }

    @Override
    public String toString() {
        return "ServiceTypeModel{serviceTypeId=" + serviceTypeId + ", serviceName='" + serviceName + "'}";
    }
}