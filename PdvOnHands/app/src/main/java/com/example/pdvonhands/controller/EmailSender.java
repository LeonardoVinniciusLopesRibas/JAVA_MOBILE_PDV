package com.example.pdvonhands.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class EmailSender {
    public static void enviarEmailDeTeste(Context context, String destinatario, String assunto, String corpo) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{destinatario});
        intent.putExtra(Intent.EXTRA_SUBJECT, assunto);
        intent.putExtra(Intent.EXTRA_TEXT, corpo);

        try {
            context.startActivity(Intent.createChooser(intent, "Escolha um cliente de e-mail"));
        } catch (android.content.ActivityNotFoundException ex) {
            // Tratar caso não haja nenhum cliente de e-mail instalado no dispositivo
            ex.printStackTrace();
        }
    }
}
