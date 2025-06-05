package com.example.fsi;
import com.google.gson.annotations.SerializedName;

public class Bilan {

    private int idUti;
    private int type;

    @SerializedName("notdoss")
    private Double notdoss;

    @SerializedName("notent")
    private Double notent;

    @SerializedName("notor")
    private Double notor;

    @SerializedName("moy")
    private Double moy;

    @SerializedName("remarque")
    private String remarque;

    @SerializedName("sujmemo")
    private String sujmemo;

    @SerializedName("datevisite")
    private String datevisite;
    public Bilan(int idUti, int type, double notdoss, Double notent, double notor,
                 double moy, String remarque, String sujmemo, String datevisite) {
        this.idUti = idUti;
        this.type = type;
        this.notdoss = notdoss;
        this.notent = notent;
        this.notor = notor;
        this.moy = moy;
        this.remarque = remarque;
        this.sujmemo = sujmemo;
        this.datevisite = datevisite;
    }

    public int getIdUti() {
        return idUti;
    }

    public void setIdUti(int idUti) {
        this.idUti = idUti;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getNotdoss() {
        return notdoss != null ? notdoss : 0.0;
    }

    public void setNotdoss(double notdoss) {
        this.notdoss = notdoss;
    }

    public double getNotent() {
        return notent != null ? notent : 0.0;
    }


    public void setNotent(double notent) {
        this.notent = notent;
    }

    public double getNotor() {
        return notor != null ? notor : 0.0;
    }
    public void setNotor(double notor) {
        this.notor = notor;
    }

    public double getMoy() {
        return moy != null ? moy : 0.0;
    }

    public void setMoy(double moy) {
        this.moy = moy;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getSujmemo() {
        return sujmemo;
    }

    public void setSujmemo(String sujmemo) {
        this.sujmemo = sujmemo;
    }

    public String getDatevisite() {
        return datevisite;
    }

    public void setDatevisite(String datevisite) {
        this.datevisite = datevisite;
    }
}
