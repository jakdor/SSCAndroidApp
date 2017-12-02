
package com.jakdor.sscapp.Model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Host extends SugarRecord implements Serializable
{
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;
    private final static long serialVersionUID = 6362040225203802694L;

    public Host() {
    }

    public Host(String name, String info, String mail, String imgUrl) {
        super();
        this.name = name;
        this.info = info;
        this.mail = mail;
        this.imgUrl = imgUrl;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
