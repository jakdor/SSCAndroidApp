
package com.jakdor.sscapp.Model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Timetable extends SugarRecord<Timetable> implements Serializable
{
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("mode")
    @Expose
    private Integer mode;
    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("hStart")
    @Expose
    private Integer hStart;
    @SerializedName("hEnd")
    @Expose
    private Integer hEnd;
    @SerializedName("mStart")
    @Expose
    private Integer mStart;
    @SerializedName("mEnd")
    @Expose
    private Integer mEnd;
    private final static long serialVersionUID = 5942897135578861955L;

    public Timetable() {
    }

    public Timetable(String name, String info, Integer mode, Integer day, Integer hStart, Integer hEnd, Integer mStart, Integer mEnd) {
        super();
        this.name = name;
        this.info = info;
        this.mode = mode;
        this.day = day;
        this.hStart = hStart;
        this.hEnd = hEnd;
        this.mStart = mStart;
        this.mEnd = mEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHStart() {
        return hStart;
    }

    public void setHStart(Integer hStart) {
        this.hStart = hStart;
    }

    public Integer getHEnd() {
        return hEnd;
    }

    public void setHEnd(Integer hEnd) {
        this.hEnd = hEnd;
    }

    public Integer getMStart() {
        return mStart;
    }

    public void setMStart(Integer mStart) {
        this.mStart = mStart;
    }

    public Integer getMEnd() {
        return mEnd;
    }

    public void setMEnd(Integer mEnd) {
        this.mEnd = mEnd;
    }

}
