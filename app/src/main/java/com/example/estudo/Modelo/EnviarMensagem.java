package com.example.estudo.Modelo;

import com.google.firebase.auth.FirebaseUser;

public class EnviarMensagem {
     private  String cad_email;
     private String cad_Mensagem;
     private String cad_hora_formatada;
     private String cad_nome;
     private String cad_id;
     private String cad_id_msg;

    public EnviarMensagem() {
    }

    public EnviarMensagem(String cad_email, String cad_Mensagem, String cad_hora_formatada, String cad_nome, String cad_id, String cad_id_msg) {
        this.cad_email = cad_email;
        this.cad_Mensagem = cad_Mensagem;
        this.cad_hora_formatada = cad_hora_formatada;
        this.cad_nome = cad_nome;
        this.cad_id = cad_id;
        this.cad_id_msg = cad_id_msg;
    }

    public String getEmail() {
        return cad_email;
    }

    public void setEmail(String email) {
        cad_email = email;
    }

    public String getMensagem() {
        return cad_Mensagem;
    }

    public void setMensagem(String mensagem) {
        cad_Mensagem = mensagem;
    }

    public String getHora_Formatada() {
        return cad_hora_formatada;
    }

    public void setHora_Formatada(String hora_Formatada) {
        cad_hora_formatada = hora_Formatada;
    }

    public String getCad_nome() {
        return cad_nome;
    }

    public void setCad_nome(String cad_nome) {
        this.cad_nome = cad_nome;
    }

    public String getCad_id() {
        return cad_id;
    }

    public void setCad_id(String cad_id) {
        this.cad_id = cad_id;
    }

    public String getCad_id_msg() {
        return cad_id_msg;
    }

    public void setCad_id_msg(String cad_id_msg) {
        this.cad_id_msg = cad_id_msg;
    }

    @Override
    public String toString() {
        return  "["+cad_hora_formatada+ "] " +cad_nome + " diz : " + cad_Mensagem + "";
    }


}
