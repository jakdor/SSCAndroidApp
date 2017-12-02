
package com.jakdor.sscapp.Model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Sponsor extends SugarRecord implements Serializable
{
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("webUrl")
    @Expose
    private String webUrl;
    @SerializedName("mode")
    @Expose
    private Integer mode;
    private final static long serialVersionUID = 9125616004441868704L;

    public Sponsor() {
    }

    public Sponsor(String name, String imgUrl, String webUrl, Integer mode) {
        super();
        this.name = name;
        this.imgUrl = imgUrl;
        this.webUrl = webUrl;
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

}
