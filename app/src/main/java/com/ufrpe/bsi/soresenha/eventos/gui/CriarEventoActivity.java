package com.ufrpe.bsi.soresenha.eventos.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;
import com.ufrpe.bsi.soresenha.infra.gui.MenuActivity;

public class CriarEventoActivity extends AppCompatActivity {

    private EditText editnome, editdesc;
    private Button criarbtn;
    private EventoServices eventoServices = new EventoServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento);

        editnome = findViewById(R.id.nomeFestaedit);
        editdesc = findViewById(R.id.descFestaedit);
        criarbtn = findViewById(R.id.criarFestabutton);
        criarbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nome = editnome.getText().toString();
                String descricao = editdesc.getText().toString();
                Evento evento = new Evento(nome, descricao);

                eventoServices.criarEvento(evento);
                Intent backMenu = new Intent(CriarEventoActivity.this, MenuActivity.class);
                startActivity(backMenu);
            }
        });


    }
}
