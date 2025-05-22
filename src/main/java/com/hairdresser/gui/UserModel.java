package com.hairdresser.gui;

import org.json.JSONObject;

public class UserModel extends EntityModel {
    private static final long serialVersionUID = 1L;
    private int userId;
    private String username;
    private String password;
    private int roleId;
    private String roleName;

    public UserModel(int userId, String username, String password, int roleId, String roleName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    // Геттеры и сеттеры
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("user_id", userId);
        json.put("username", username);
        json.put("password", password);
        json.put("role_id", roleId);
        return json.toString();
    }

    @Override
    public String toString() {
        return "UserModel{userId=" + userId + ", username='" + username + "', roleName='" + roleName + "'}";
    }
}