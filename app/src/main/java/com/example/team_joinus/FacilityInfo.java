package com.example.team_joinus;

import com.google.gson.annotations.SerializedName;

public class FacilityInfo {
    @SerializedName("fac_p")
    private String fac_p;
    @SerializedName("fac_ceo")
    private String fac_ceo;
    @SerializedName("fac_title")
    private String fac_title;
    @SerializedName("fac_info")
    private String fac_info;
    @SerializedName("fac_max")
    private String fac_max;
    @SerializedName("fac_clicked")
    private String fac_clicked;

    public FacilityInfo(String fac_p, String fac_ceo, String fac_title, String fac_info, String fac_max, String fac_clicked) {
        this.fac_p = fac_p;
        this.fac_ceo = fac_ceo;
        this.fac_title = fac_title;
        this.fac_info = fac_info;
        this.fac_max = fac_max;
        this.fac_clicked = fac_clicked;
    }

    public String getFac_p() {
        return fac_p;
    }

    public void setFac_p(String fac_p) {
        this.fac_p = fac_p;
    }

    public String getFac_ceo() {
        return fac_ceo;
    }

    public void setFac_ceo(String fac_ceo) {
        this.fac_ceo = fac_ceo;
    }

    public String getFac_title() {
        return fac_title;
    }

    public void setFac_title(String fac_title) {
        this.fac_title = fac_title;
    }

    public String getFac_info() {
        return fac_info;
    }

    public void setFac_info(String fac_info) {
        this.fac_info = fac_info;
    }

    public String getFac_max() {
        return fac_max;
    }

    public void setFac_max(String fac_max) {
        this.fac_max = fac_max;
    }

    public String getFac_clicked() {
        return fac_clicked;
    }

    public void setFac_clicked(String fac_clicked) {
        this.fac_clicked = fac_clicked;
    }
}

