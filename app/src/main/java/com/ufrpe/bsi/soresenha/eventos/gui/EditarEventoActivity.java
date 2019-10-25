package com.ufrpe.bsi.soresenha.eventos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;

public class EditarEventoActivity extends AppCompatActivity {

    private EditText editNome, editDesc, editPreco;
    private Button criarBtn;
    private EventoServices eventoServices = new EventoServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);
        configurarTela(intent);
    }

    private void configurarTela(Intent intent) {
        Bundle extras = intent.getExtras();
        final Evento eventoOld = eventoServices.getEvento(extras.getLong("EventId"));
        editNome = findViewById(R.id.nomeFestaedit);
        editDesc = findViewById(R.id.descFestaEdit);
        editPreco = findViewById(R.id.precoFestaedit);
        criarBtn = findViewById(R.id.criarFestabutton);
        editNome.setText(eventoOld.getNome());
        editDesc.setText(eventoOld.getDescricao());
        editPreco.setText(eventoOld.getPreco());
        criarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editNome.getText().toString();
                String descricao = editDesc.getText().toString();
                String preco = editPreco.getText().toString();
                Evento eventoNew = new Evento(eventoOld.getId(), nome, descricao, preco);
                eventoServices.save(eventoNew);
                Intent backMenu = new Intent(EditarEventoActivity.this, ListaEventoActivity.class);
                startActivity(backMenu);
            }
        });
    }
}
