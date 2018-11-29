package com.surfarrayinc.surf.model;

import android.graphics.drawable.Drawable;

public class vendor{
    private int vid;
    private String vimage;
    private String vname;
    private String vcategory;
    private String vlocation;
    private String vContact;
    private String rating;

    //added later
    private String vdist;
    private String vdesc;

    public vendor() {
    }

    public vendor(int vid, String vimage, String vname, String vcategory, String vlocation, String vContact, String rating, String vdist, String vdesc) {
        this.vid = vid;
        this.vimage = vimage;
        this.vname = vname;
        this.vcategory = vcategory;
        this.vlocation = vlocation;
        this.vContact = vContact;
        this.rating = rating;

        this.vdist = vdist;
        this.vdesc = vdesc;

    }


    public String getVdist() {
        return vdist;
    }

    public void setVdist(String vdist) {
        this.vdist = vdist;
    }

    public String getVdesc() {
        return vdesc;
    }

    public void setVdesc(String vdesc) {
        this.vdesc = vdesc;
    }


    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getVimage() {
        return vimage;
    }

    public void setVimage(String vimage) {
        this.vimage = vimage;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getVcategory() {
        return vcategory;
    }

    public void setVcategory(String vcategory) {
        this.vcategory = vcategory;
    }

    public String getVlocation() {
        return vlocation;
    }

    public void setVlocation(String vlocation) {
        this.vlocation = vlocation;
    }

    public String getvContact() {
        return vContact;
    }

    public void setvContact(String vContact) {
        this.vContact = vContact;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
