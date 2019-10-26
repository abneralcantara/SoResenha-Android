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

public class CriarEventoActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editDesc;
    private EditText editPreco;
    private EventoServices eventoServices = new EventoServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento);
        editNome = findViewById(R.id.nomeFestaedit);
        editPreco = findViewById(R.id.precoFestaedit);
        editDesc = findViewById(R.id.descFestaEdit);
        Button criarBtn = findViewById(R.id.criarFestabutton);
        criarListeners(criarBtn);
    }

    private void criarListeners(Button criarBtn) {
        criarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editNome.getText().toString();
                String descricao = editDesc.getText().toString();
                String preco = editPreco.getText().toString();
                Evento evento = new Evento(nome, descricao, preco);
                eventoServices.criarEvento(evento);
                Intent backMenu = new Intent(CriarEventoActivity.this, ListaEventoActivity.class);
                backMenu.setFlags(backMenu.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(backMenu);
            }
        });
    }
}
