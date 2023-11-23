package com.example.pdvonhands.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pdvonhands.R;
import com.example.pdvonhands.controller.EmailSender;

public class RecuperaSenhaActivity extends AppCompatActivity {

    private EditText edRecuperaEmail;
    private Button btEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_senha);

        edRecuperaEmail = findViewById(R.id.edRecuperaEmail);
        btEnviar = findViewById(R.id.btEnviar);

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarEmailDeTest();
            }
        });

    }

    private void enviarEmailDeTest() {

        try {
            if (edRecuperaEmail.getText().toString().isEmpty()){
                edRecuperaEmail.setError("Verifique o campo e-mail. Incorreto!");
            }
            else {
                String destinatario = edRecuperaEmail.getText().toString();
                String assunto = "Recuperação de senha";
                String corpo = "Olá, você solicitou a recuperação de email";

                EmailSender.enviarEmailDeTeste(this, destinatario, assunto, corpo);

                exibirToast("E-mail enviado com sucesso");

                exibirToast("E-mail de recuperação enviado com sucesso!");
            }
        }catch (Exception ex){
                Log.e("Erro", "Ocorreu um erro enviarEmailDeTeste(): " + ex.getMessage());

        }


    }

    private void exibirToast(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}