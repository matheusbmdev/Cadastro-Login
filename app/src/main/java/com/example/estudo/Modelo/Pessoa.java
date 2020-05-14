package com.example.estudo.Modelo;

public class Pessoa {

    private String nome;
    private String sobrenome;
    private String email;
    private String id;
    private boolean adm;



    public Pessoa() {

    }

    public Pessoa(String nome, String sobrenome, String email, String id, boolean adm) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.adm = adm;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nome;
    }
}
