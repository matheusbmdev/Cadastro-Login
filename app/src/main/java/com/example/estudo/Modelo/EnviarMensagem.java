package com.example.estudo.Modelo;

import com.google.firebase.auth.FirebaseUser;

public class EnviarMensagem {
     private  String cad_email;
     private String cad_Mensagem;
     private String cad_hora_formatada;

    public EnviarMensagem() {
    }

    public EnviarMensagem(String email, String mensagem, String hora_Formatada) {
        cad_email = email;
        cad_Mensagem = mensagem;
        cad_hora_formatada = hora_Formatada;
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

    @Override
    public String toString() {
        return  "["+cad_hora_formatada+ "]" +cad_email + " diz : " + cad_Mensagem + "";
    }


}
