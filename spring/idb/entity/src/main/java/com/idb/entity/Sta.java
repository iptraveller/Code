package com.idb.entity;

import java.util.Date;

public class Sta {
    private String mac;
    private String username;
    private String os;
    private String osVersion;
    private String model;
    private String type;
    Integer is5g;
    Integer is11a;
    Integer is11b;
    Integer is11g;
    Integer is11an;
    Integer is11bn;
    Integer is11ac;
    Integer is11acWave2;
    private Date firstOnlineTime;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIs5g() {
        return is5g;
    }

    public void setIs5g(Integer is5g) {
        this.is5g = is5g;
    }

    public Integer getIs11a() {
        return is11a;
    }

    public void setIs11a(Integer is11a) {
        this.is11a = is11a;
    }

    public Integer getIs11b() {
        return is11b;
    }

    public void setIs11b(Integer is11b) {
        this.is11b = is11b;
    }

    public Integer getIs11g() {
        return is11g;
    }

    public void setIs11g(Integer is11g) {
        this.is11g = is11g;
    }

    public Integer getIs11an() {
        return is11an;
    }

    public void setIs11an(Integer is11an) {
        this.is11an = is11an;
    }

    public Integer getIs11bn() {
        return is11bn;
    }

    public void setIs11bn(Integer is11bn) {
        this.is11bn = is11bn;
    }

    public Integer getIs11ac() {
        return is11ac;
    }

    public void setIs11ac(Integer is11ac) {
        this.is11ac = is11ac;
    }

    public Integer getIs11acWave2() {
        return is11acWave2;
    }

    public void setIs11acWave2(Integer is11acWave2) {
        this.is11acWave2 = is11acWave2;
    }

    public Date getFirstOnlineTime() {
        return firstOnlineTime;
    }

    public void setFirstOnlineTime(Date firstOnlineTime) {
        this.firstOnlineTime = firstOnlineTime;
    }

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getManufacturerEn() {
        return manufacturerEn;
    }

    public void setManufacturerEn(String manufacturerEn) {
        this.manufacturerEn = manufacturerEn;
    }

    private Date lastOnlineTime;
    private String projectId;
    private Integer source;
    private String manufacturerEn;
}
