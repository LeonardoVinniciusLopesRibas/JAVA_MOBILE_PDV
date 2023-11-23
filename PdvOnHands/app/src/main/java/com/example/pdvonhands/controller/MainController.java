package com.example.pdvonhands.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.pdvonhands.model.MainModel;
import com.example.pdvonhands.view.RetaguardaActivity;

public class MainController {

    private MainModel mainModel;

    public MainController(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void validarEmail(Context context, String emailInformado, String senhaInformada){
        if (emailInformado.equals(mainModel.getEmail()) && senhaInformada.equals(mainModel.getSenha())){
            showToast(context, "Login realizado!");
            Intent intent = new Intent(context, RetaguardaActivity.class);
            context.startActivity(intent);
        }else {
            showToast(context, "E-mail ou senha incorretos");
        }
    }

    public void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
