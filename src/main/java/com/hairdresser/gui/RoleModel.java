package com.hairdresser.gui;

import org.json.JSONObject;

public class RoleModel extends EntityModel {
    private static final long serialVersionUID = 1L;
    private int roleId;
    private String roleName;

    public RoleModel(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("role_id", roleId);
        json.put("role_name", roleName);
        return json.toString();
    }

    @Override
    public String toString() {
        return "RoleModel{roleId=" + roleId + ", roleName='" + roleName + "'}";
    }
}