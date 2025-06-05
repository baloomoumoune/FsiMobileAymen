package com.example.fsi;

public class Utilisateur {
    private int idUti;
    private String login;
    private String motdepasse;

    private String nomUti, preUti, mailUti, telUti, adrUti, vilUti, cpUti;
    private String nomEnt, nomMaitapp, nomTut;

    public Utilisateur(int idUti, String login, String motdepasse,
                       String nomUti, String preUti, String mailUti, String telUti,
                       String adrUti, String vilUti, String cpUti,
                       String nomEnt, String nomMaitapp, String nomTut) {
        this.idUti = idUti;
        this.login = login;
        this.motdepasse = motdepasse;
        this.nomUti = nomUti;
        this.preUti = preUti;
        this.mailUti = mailUti;
        this.telUti = telUti;
        this.adrUti = adrUti;
        this.vilUti = vilUti;
        this.cpUti = cpUti;
        this.nomEnt = nomEnt;
        this.nomMaitapp = nomMaitapp;
        this.nomTut = nomTut;
    }

    public int getIdUti() { return idUti; }
    public String getLogin() { return login; }
    public String getMotdepasse() { return motdepasse; }
    public String getNomUti() { return nomUti; }
    public String getPreUti() { return preUti; }
    public String getMailUti() { return mailUti; }
    public String getTelUti() { return telUti; }
    public String getAdrUti() { return adrUti; }
    public String getVilUti() { return vilUti; }
    public String getCpUti() { return cpUti; }
    public String getNomEnt() { return nomEnt; }
    public String getNomMaitapp() { return nomMaitapp; }
    public String getNomTut() { return nomTut; }
}
