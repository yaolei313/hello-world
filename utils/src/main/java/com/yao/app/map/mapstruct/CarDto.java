package com.yao.app.map.mapstruct;

/**
 * Created by yaolei02 on 2018/8/31.
 */
public class CarDto {
    private String make;

    private int seatCount;

    private String type;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
