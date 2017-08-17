
package com.jakdor.sscapp.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppData implements Serializable
{

    @SerializedName("timetables")
    @Expose
    private List<Timetable> timetables = null;
    @SerializedName("hosts")
    @Expose
    private List<Host> hosts = null;
    @SerializedName("sponsors")
    @Expose
    private List<Sponsor> sponsors = null;
    private final static long serialVersionUID = -793513161365219544L;

    public AppData() {
    }

    public AppData(List<Timetable> timetables, List<Host> hosts, List<Sponsor> sponsors) {
        super();
        this.timetables = timetables;
        this.hosts = hosts;
        this.sponsors = sponsors;
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public List<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

}
