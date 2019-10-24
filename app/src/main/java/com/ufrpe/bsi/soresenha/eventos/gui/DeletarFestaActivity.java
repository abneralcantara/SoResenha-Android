package com.ufrpe.bsi.soresenha.eventos.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;
import com.ufrpe.bsi.soresenha.infra.gui.MenuActivity;

public class DeletarFestaActivity extends AppCompatActivity {
    private EditText editEvento;
    private Button deletarEventobutton;
    private EventoServices eventoServices = new EventoServices(this);
    //A activity ta com erro ao ser chamada pelo menu devido algum retorno

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletar_festa);

        getSupportActionBar().hide();
        editEvento = findViewById(R.id.eventoDeletar);
        deletarEventobutton = findViewById(R.id.deletaruser_button);

        deletarEventobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String evento = editEvento.getText().toString();
                if (!eventoServices.checarEvento(evento)) {
                    eventoServices.deletarEvento(eventoServices.getEvento(evento));
                    Toast.makeText(DeletarFestaActivity.this,"Evento deletado com sucesso", Toast.LENGTH_LONG).show();
                    Intent menuIntent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(menuIntent);
                    return;
                }
                Toast.makeText(DeletarFestaActivity.this,"Evento não encontrado", Toast.LENGTH_LONG).show();

            }
        });

    }
}
