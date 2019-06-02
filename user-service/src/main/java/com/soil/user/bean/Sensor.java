package com.soil.user.bean;

public class Sensor {

    private int id;

    private String name;

    private float temp;

    private float hum;

    private float th;

    private float tl;

    private float hh;

    private float hl;

    private int userId;

    private int infoId;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getHum() {
        return hum;
    }

    public void setHum(float hum) {
        this.hum = hum;
    }

    public float getHh() {
        return hh;
    }

    public void setHh(float hh) {
        this.hh = hh;
    }

    public float getHl() {
        return hl;
    }

    public void setHl(float hl) {
        this.hl = hl;
    }

    public float getTh() {
        return th;
    }

    public void setTh(float th) {
        this.th = th;
    }

    public float getTl() {
        return tl;
    }

    public void setTl(float tl) {
        this.tl = tl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userid) {
        this.userId = userid;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }
}
